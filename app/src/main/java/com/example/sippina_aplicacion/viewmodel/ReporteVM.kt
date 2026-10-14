package com.example.sippina_aplicacion.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sippina_aplicacion.model.BorradorGuardado
import com.example.sippina_aplicacion.model.DatosMenor
import com.example.sippina_aplicacion.model.EstadoReporte
import com.example.sippina_aplicacion.model.EvidenciaReporte
import com.example.sippina_aplicacion.model.Reporte.ReporteRepository
import com.example.sippina_aplicacion.model.Reporte.ReporteRequest
import com.example.sippina_aplicacion.model.ReporteModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * ViewModel principal encargado de administrar la lógica de negocio, el flujo de asistente en 5 pasos,
 * la persistencia local de borradores y la comunicación con el servidor para la gestión de reportes de SIPINNA.
 *
 * Expone flujos de estado reactivos ([StateFlow]) para el estado del formulario ([EstadoReporte]), listados de reportes,
 * detalles, indicadores de carga y resultados de búsqueda.
 *
 * @property repository Repositorio [ReporteRepository] responsable de las peticiones REST de creación y consulta de reportes.
 */
class ReporteVM(
    private val repository: ReporteRepository = ReporteRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoReporte())
    val uiState: StateFlow<EstadoReporte> = _uiState.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _misReportes = MutableStateFlow<List<ReporteModel>>(emptyList())
    val misReportes: StateFlow<List<ReporteModel>> = _misReportes.asStateFlow()

    private val _detalleReporte = MutableStateFlow<ReporteModel?>(null)
    val detalleReporte: StateFlow<ReporteModel?> = _detalleReporte.asStateFlow()

    private val _buscandoReporte = MutableStateFlow(false)
    val buscandoReporte: StateFlow<Boolean> = _buscandoReporte.asStateFlow()

    private val _reporteEncontrado = MutableStateFlow<ReporteModel?>(null)
    val reporteEncontrado: StateFlow<ReporteModel?> = _reporteEncontrado.asStateFlow()

    private val _errorBusqueda = MutableStateFlow<String?>(null)
    val errorBusqueda: StateFlow<String?> = _errorBusqueda.asStateFlow()

    private var borradorStorage: BorradorGuardado? = null

    /**
     * Inicializa el almacenamiento local de borradores y carga cualquier progreso guardado previamente.
     *
     * @param context Contexto [Context] de Android para inicializar [BorradorGuardado].
     */
    fun iniciarlizarBorradorStorage(context: Context) {
        if (borradorStorage == null) {
            borradorStorage = BorradorGuardado(context)
            cargarBorradorExistente()
        }
    }

    /**
     * Carga un borrador de reporte existente desde el almacenamiento local del dispositivo.
     */
    fun cargarBorradorExistente() {
        val borrador = borradorStorage?.obtenerBorradorLocal()
        if (borrador != null) {
            _uiState.value = borrador
        }
    }

    /**
     * Guarda automáticamente el estado actual del formulario de reporte en el almacenamiento local.
     */
    private fun autoGuardarLocal() {
        borradorStorage?.guardarBorrador(_uiState.value)
    }

    /**
     * Solicita el guardado manual del borrador actual.
     */
    fun guardarBorrador() {
        autoGuardarLocal()
    }

    /**
     * Descarta y elimina el borrador guardado en el almacenamiento local.
     */
    fun descartarBorrador() {
        borradorStorage?.eliminarBorradorLocal()
        limpiarBusqueda()
    }

    /**
     * Restablece el estado del reporte a sus valores iniciales y purga el borrador local.
     */
    fun reiniciarEstadoReporte() {
        borradorStorage?.eliminarBorradorLocal()
        _uiState.value = EstadoReporte()
    }

    /**
     * Obtiene el listado de reportes enviados por el usuario desde el servidor backend.
     */
    fun obtenerMisReportesDelBackEnd() {
        viewModelScope.launch {
            _cargando.value = true
            val resultado = repository.obtenerListaReportes()

            resultado.onSuccess { lista ->
                _misReportes.value = lista.map { res ->
                    ReporteModel(
                        folio = res.folio,
                        estatus = res.estatus,
                        fechaEnvio = res.fechaEnvio ?: "",
                        descripcion = res.mensaje ?: "",
                        direccion = ""
                    )
                }
            }.onFailure {
                _misReportes.value = emptyList()
            }
            _cargando.value = false
        }
    }

    /**
     * Avanza al siguiente paso del asistente de reporte (máximo el paso 4).
     */
    fun siguientePaso() {
        _uiState.update { it.copy(pasoActual = (it.pasoActual + 1).coerceAtMost(4)) }
    }

    /**
     * Retrocede al paso anterior del asistente de reporte (mínimo el paso 0).
     */
    fun anteriorPaso() {
        _uiState.update { it.copy(pasoActual = (it.pasoActual - 1).coerceAtLeast(0)) }
    }

    /**
     * Actualiza la opción seleccionada para establecer la ubicación del incidente.
     *
     * @param opcion Cadena que representa la modalidad elegida.
     */
    fun seleccionarOpcionUbicacion(opcion: String) {
        _uiState.update { it.copy(opcionUbicacion = opcion, ubicacionConfirmada = false) }
    }

    /**
     * Establece las coordenadas geográficas y la dirección del incidente en el estado del reporte.
     *
     * @param lat Coordenada de latitud.
     * @param lng Coordenada de longitud.
     * @param direccion Texto descriptivo de la dirección.
     */
    fun setUbicacion(lat: Double, lng: Double, direccion: String = "") {
        _uiState.update {
            it.copy(latitud = lat, longitud = lng, direccion = direccion, ubicacionConfirmada = true)
        }
    }

    /**
     * Establece el horario aproximado en que ocurrieron los hechos.
     *
     * @param horario Cadena con el formato de hora (ej. `"12:30"`).
     */
    fun setHorarioAprox(horario: String) {
        _uiState.update { it.copy(horarioAprox = horario) }
    }

    /**
     * Actualiza la cantidad de menores involucrados en el incidente y ajusta el listado correspondiente.
     *
     * @param delta Valor entero positivo o negativo para incrementar o decrementar la cantidad.
     */
    fun cambiarCantidadMenores(delta: Int) {
        _uiState.update { estado ->
            val nuevaCantidad = (estado.cantidadMenores + delta).coerceAtLeast(1)
            val menoresActualizados = estado.menores.toMutableList()

            while (menoresActualizados.size < nuevaCantidad) {
                menoresActualizados.add(DatosMenor())
            }
            val nuevoIndice = estado.indiceMenorActual.coerceIn(0, nuevaCantidad - 1)
            val menorActual = menoresActualizados[nuevoIndice]
            estado.copy(
                cantidadMenores = nuevaCantidad,
                menores = menoresActualizados,
                indiceMenorActual = nuevoIndice,
                rangoEdad = menorActual.rangoEdad,
                genero = menorActual.genero,
                descripcionFisica = menorActual.descripcionFisica
            )
        }
    }

    /**
     * Indica si hubo adultos involucrados en la situación reportada.
     *
     * @param opcion Respuesta seleccionada (ej. `"Sí"`, `"No"`, `"No sé"`).
     */
    fun setAdultoInvolucrado(opcion: String) {
        _uiState.update { it.copy(adultoInvolucrados = opcion) }
    }

    /**
     * Establece la descripción narrativa de los hechos del incidente.
     *
     * @param texto Relato de la situación observada.
     */
    fun setDescripcionHechos(texto: String) {
        _uiState.update { it.copy(descripcionHechos = texto) }
    }

    /**
     * Establece el rango de edad estimado para el menor en edición actual.
     *
     * @param rango Rango de edad seleccionado.
     */
    fun setRangoEdad(rango: String) {
        _uiState.update { estado ->
            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual
            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }
            menoresActualizados[indice] = menoresActualizados[indice].copy(rangoEdad = rango)
            estado.copy(menores = menoresActualizados, rangoEdad = rango)
        }
    }

    /**
     * Establece el género del menor en edición actual.
     *
     * @param genero Género seleccionado.
     */
    fun setGenero(genero: String) {
        _uiState.update { estado ->
            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual
            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }
            menoresActualizados[indice] = menoresActualizados[indice].copy(genero = genero)
            estado.copy(menores = menoresActualizados, genero = genero)
        }
    }

    /**
     * Establece la descripción física del menor en edición actual.
     *
     * @param texto Rasgos particulares o vestimenta del menor.
     */
    fun setDescripcionFisica(texto: String) {
        _uiState.update { estado ->
            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual
            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }
            menoresActualizados[indice] = menoresActualizados[indice].copy(descripcionFisica = texto)
            estado.copy(menores = menoresActualizados, descripcionFisica = texto)
        }
    }

    /**
     * Selecciona el índice del menor activo dentro del flujo iterativo de captura.
     *
     * @param indice Índice de base cero del menor.
     */
    private fun seleccionarMenor(indice: Int) {
        _uiState.update { estado ->
            val indiceSeguro = indice.coerceIn(0, (estado.cantidadMenores - 1).coerceAtLeast(0))
            val menor = estado.menores.getOrNull(indiceSeguro) ?: DatosMenor()
            estado.copy(
                indiceMenorActual = indiceSeguro,
                rangoEdad = menor.rangoEdad,
                genero = menor.genero,
                descripcionFisica = menor.descripcionFisica
            )
        }
    }

    /**
     * Selecciona el primer menor de la lista para su edición.
     */
    fun irAlPrimerMenor() = seleccionarMenor(0)

    /**
     * Avanza al siguiente menor en la lista de menores involucrados.
     *
     * @return `true` si avanzó exitosamente a otro menor; `false` si ya se encontraba en el último menor.
     */
    fun avanzarAlSiguienteMenor(): Boolean {
        val estado = _uiState.value
        val siguienteIndice = estado.indiceMenorActual + 1
        if (siguienteIndice >= estado.cantidadMenores) return false
        seleccionarMenor(siguienteIndice)
        return true
    }

    /**
     * Retrocede al menor anterior en la lista de menores involucrados.
     *
     * @return `true` si retrocedió exitosamente; `false` si ya estaba en el primer menor.
     */
    fun regresarAlMenorAnterior(): Boolean {
        val indiceAnterior = _uiState.value.indiceMenorActual - 1
        if (indiceAnterior < 0) return false
        seleccionarMenor(indiceAnterior)
        return true
    }

    /**
     * Selecciona el tipo de evidencia multimedia que se va a adjuntar.
     *
     * @param tipo Categoría del archivo (ej. `"imagen"`, `"video"`).
     */
    fun seleccionarTipoEvidencia(tipo: String) {
        _uiState.update { it.copy(tipoEvidenciaSeleccionada = tipo) }
    }

    /**
     * Agrega un archivo de evidencia a la lista de archivos adjuntos del reporte.
     *
     * @param uri Ubicación [Uri] del archivo multimedia.
     * @param tipo Categoría o tipo de archivo.
     * @param nombre Nombre original o descriptivo del archivo.
     */
    fun agregarEvidencia(uri: Uri, tipo: String, nombre: String = "") {
        _uiState.update { estado ->
            if (estado.evidencias.any { it.uri == uri }) {
                estado
            } else {
                estado.copy(
                    evidencias = estado.evidencias + EvidenciaReporte(
                        uri = uri,
                        tipo = tipo,
                        nombre = nombre
                    )
                )
            }
        }
    }

    /**
     * Elimina un archivo de evidencia adjunto de la lista según su [Uri].
     *
     * @param uri [Uri] del archivo a remover.
     */
    fun eliminarEvidencia(uri: Uri) {
        _uiState.update { estado ->
            val actualizadas = estado.evidencias.filterNot { it.uri == uri }
            estado.copy(evidencias = actualizadas, evidenciaUri = actualizadas.firstOrNull()?.uri)
        }
    }

    /**
     * Actualiza la [Uri] temporal de la evidencia en selección.
     *
     * @param uri [Uri] del archivo multimedia.
     */
    fun actualizarEvidenciaUri(uri: Uri?) {
        _uiState.update { it.copy(evidenciaUri = uri) }
    }

    /**
     * Actualiza la modalidad de privacidad seleccionada para el reporte (`"anonimo"` o `"actualizaciones"`).
     *
     * @param nuevaOpcion Opción de privacidad elegida.
     */
    fun actualizarOpcionPrivacidad(nuevaOpcion: String) {
        val opcionNormalizada = if (nuevaOpcion == "anónimo") "anonimo" else nuevaOpcion
        if (opcionNormalizada != "anonimo" && opcionNormalizada != "actualizaciones") return
        _uiState.update { it.copy(opcionPrivacidad = opcionNormalizada) }
    }

    /**
     * Establece si el reporte debe procesarse bajo la modalidad anónima.
     *
     * @param valor `true` para anónimo, `false` para seguimiento con actualizaciones.
     */
    fun setEsAnonimo(valor: Boolean) {
        actualizarOpcionPrivacidad(if (valor) "anonimo" else "actualizaciones")
    }

    /**
     * Establece el correo electrónico para recibir actualizaciones de seguimiento del reporte.
     *
     * @param correo Dirección de correo electrónico.
     */
    fun setCorreoSeguimiento(correo: String) {
        _uiState.update { it.copy(correoSeguimiento = correo) }
    }

    /**
     * Valida que la descripción narrativa de los hechos no esté vacía.
     *
     * @return `true` si es válida, `false` en caso contrario.
     */
    fun descripcionHechosValida(): Boolean = _uiState.value.descripcionHechos.trim().isNotEmpty()

    /**
     * Valida que la descripción física del menor actual no esté vacía.
     *
     * @return `true` si es válida, `false` en caso contrario.
     */
    fun descripcionFisicaValida(): Boolean = _uiState.value.descripcionFisica.trim().isNotEmpty()

    /**
     * Valida que la ubicación geográfica haya sido confirmada por el usuario.
     *
     * @return `true` si está confirmada, `false` en caso contrario.
     */
    fun ubicacionValida(): Boolean = _uiState.value.ubicacionConfirmada

    /**
     * Valida el correo de seguimiento según la modalidad de privacidad elegida.
     *
     * @return `true` si es válido o si el reporte es anónimo; `false` si falta un correo válido en modo actualizaciones.
     */
    fun correoSeguimientoValido(): Boolean {
        val estado = _uiState.value
        if (estado.opcionPrivacidad == "anonimo") return true
        return Patterns.EMAIL_ADDRESS.matcher(estado.correoSeguimiento.trim()).matches()
    }

    /**
     * Limpia cualquier error de envío registrado en el estado.
     */
    fun limpiarErrorEnvio() {
        _uiState.update { it.copy(errorEnvio = null) }
    }

    /**
     * Restablece el estado del reporte a una instancia vacía.
     */
    fun limpiarReporte() {
        _uiState.value = EstadoReporte()
    }

    /**
     * Obtiene el detalle completo de un reporte específico a partir de su folio.
     *
     * @param folio Folio único del reporte a consultar.
     */
    fun obtenerDetalleReportePorFolio(folio: String) {
        viewModelScope.launch {
            _cargando.value = true
            val resultado = repository.obtenerReportePorFolio(folio)

            resultado.onSuccess { res ->
                _detalleReporte.value = ReporteModel(
                    folio = res.folio,
                    estatus = res.estatus,
                    fechaEnvio = res.fechaEnvio ?: "",
                    descripcion = res.mensaje ?: "",
                    direccion = ""
                )
            }.onFailure {
                _detalleReporte.value = null
            }
            _cargando.value = false
        }
    }

    /**
     * Busca un reporte existente en el servidor utilizando su folio y código privado de acceso.
     *
     * @param folio Folio único del reporte.
     * @param codigo Código privado de seguridad asignado.
     */
    fun buscarReportePorFolio(folio: String, codigo: String) {
        if (folio.isBlank() || codigo.isBlank()) {
            _reporteEncontrado.value = null
            _errorBusqueda.value = null
            return
        }

        viewModelScope.launch {
            _buscandoReporte.value = true
            _reporteEncontrado.value = null
            _errorBusqueda.value = null

            val resultado = repository.obtenerReportePorFolio(folio.trim())

            resultado.onSuccess { res ->
                _reporteEncontrado.value = ReporteModel(
                    folio = res.folio,
                    estatus = res.estatus,
                    fechaEnvio = res.fechaEnvio ?: "",
                    descripcion = res.mensaje ?: "",
                    direccion = ""
                )
            }.onFailure { error ->
                _errorBusqueda.value = error.localizedMessage ?: "Sin reportes encontrados."
            }

            _buscandoReporte.value = false
        }
    }

    /**
     * Limpia los resultados y errores de la búsqueda de reportes existentes.
     */
    fun limpiarBusqueda() {
        _reporteEncontrado.value = null
        _errorBusqueda.value = null
        _buscandoReporte.value = false
    }

    /**
     * Envía el reporte completo al servidor backend, incluyendo la serialización de los datos del formulario
     * y la posterior subida de archivos multimedia adjuntos si existen.
     *
     * @param context Contexto [Context] de Android requerido para procesar las evidencias multimedia.
     */
    fun enviarReporteAlBackEnd(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(enviando = true, errorEnvio = null) }

            val estadoActual = _uiState.value

            val detallesFormulario = buildString {
                append("Hechos: ${estadoActual.descripcionHechos}\n")
                append("Horario aprox: ${estadoActual.horarioAprox}\n")
                append("Adultos involucrados: ${estadoActual.adultoInvolucrados}\n")
                append("Direccion: ${estadoActual.direccion}\n")
                append("Menores involucrados (${estadoActual.cantidadMenores}):\n")
                estadoActual.menores.forEachIndexed { i, m ->
                    append(" - Menor ${i + 1}: Genero=${m.genero}, Edad=${m.rangoEdad}, Desc=${m.descripcionFisica}\n")
                }
            }

            // Se usa ReporteRequest con nombres unificados y latitud/longitud
            val peticion = ReporteRequest(
                tipoReporte = if (estadoActual.tipoEvidenciaSeleccionada.isNotBlank()) estadoActual.tipoEvidenciaSeleccionada else "General",
                descripcion = detallesFormulario,
                opcionPrivacidad = estadoActual.opcionPrivacidad,
                telefono = if (!estadoActual.esAnonimo && estadoActual.correoSeguimiento.isNotBlank()) estadoActual.correoSeguimiento else null,
                latitud = if (estadoActual.ubicacionConfirmada) estadoActual.latitud else null,
                longitud = if (estadoActual.ubicacionConfirmada) estadoActual.longitud else null
            )

            val resultado = repository.crearReporte(peticion)

            resultado.onSuccess { respuesta ->
                if (estadoActual.evidencias.isNotEmpty()) {
                    val partes = prepararEvidenciasParaBackend(context)
                    repository.subirEvidencias(respuesta.folio, partes)
                }

                _uiState.update {
                    it.copy(
                        enviando = false,
                        reporteExitoso = true,
                        folio = respuesta.folio,
                        codigoPrivado = respuesta.codigoPrivado ?: ""
                    )
                }
                borradorStorage?.eliminarBorradorLocal()
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        enviando = false,
                        errorEnvio = error.localizedMessage ?: "No se pudo conectar con el servidor."
                    )
                }
            }
        }
    }

    /**
     * Prepara los archivos de evidencia adjuntos convirtiéndolos en partes [MultipartBody.Part] para su envío HTTP.
     *
     * @param context Contexto [Context] para resolver los flujos de datos de las URIs.
     * @return Lista de objetos [MultipartBody.Part] listos para la petición multipart.
     */
    fun prepararEvidenciasParaBackend(context: Context): List<MultipartBody.Part> {
        val listaParts = mutableListOf<MultipartBody.Part>()

        _uiState.value.evidencias.forEach { evidencia ->
            val file = obtenerArchivoDesdeUri(context, evidencia.uri)
            file?.let {
                val mimeType = context.contentResolver.getType(evidencia.uri) ?: "multipart/form-data"
                val requestFile = it.asRequestBody(mimeType.toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("evidencias", it.name, requestFile)
                listaParts.add(part)
            }
        }
        return listaParts
    }

    /**
     * Copia el contenido de una [Uri] multimedia a un archivo temporal local en caché para su transmisión.
     *
     * @param context Contexto [Context] de Android.
     * @param uri [Uri] del archivo multimedia origen.
     * @return Archivo [File] temporal creado, o `null` en caso de error.
     */
    private fun obtenerArchivoDesdeUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("upload_", ".tmp", context.cacheDir)
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun limpiarFormulario() {
        borradorStorage?.eliminarBorradorLocal()
        _uiState.value = EstadoReporte()
    }




}
