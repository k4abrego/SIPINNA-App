package com.example.sippina_aplicacion.viewmodel

import com.example.sippina_aplicacion.model.Autenticacion.EtiquetaInput
import com.example.sippina_aplicacion.model.Autenticacion.MenorInput
import com.example.sippina_aplicacion.model.Autenticacion.ReporteRequest
import com.example.sippina_aplicacion.model.Autenticacion.UbicacionInput
import com.example.sippina_aplicacion.model.Reporte.ReporteRepository
import android.content.Context
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sippina_aplicacion.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Instant




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

    val ultimoReporte: StateFlow<ReporteModel?> = _misReportes
        .map { lista -> lista.firstOrNull() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _detalleReporte = MutableStateFlow<ReporteModel?>(null)
    val detalleReporte: StateFlow<ReporteModel?> = _detalleReporte.asStateFlow()

    private val _buscandoReporte = MutableStateFlow(false)
    val buscandoReporte: StateFlow<Boolean> = _buscandoReporte.asStateFlow()

    private val _reporteEncontrado = MutableStateFlow<ReporteModel?>(null)
    val reporteEncontrado: StateFlow<ReporteModel?> = _reporteEncontrado.asStateFlow()

    private val _errorBusqueda = MutableStateFlow<String?>(null)
    val errorBusqueda: StateFlow<String?> = _errorBusqueda.asStateFlow()

    private var borradorStorage: BorradorGuardado? = null

    fun iniciarlizarBorradorStorage(context: Context) {
        if (borradorStorage == null) {
            borradorStorage = BorradorGuardado(context)
            cargarBorradorExistente()
        }
    }

    fun cargarBorradorExistente() {
        val borrador = borradorStorage?.obtenerBorradorLocal()
        if (borrador != null) {
            _uiState.value = borrador
        }
    }

    private fun autoGuardarLocal() {
        borradorStorage?.guardarBorrador(_uiState.value)
    }

    fun guardarBorrador() {
        autoGuardarLocal()
    }

    fun descartarBorrador() {
        borradorStorage?.eliminarBorradorLocal()
        limpiarBusqueda()
    }

    fun reiniciarEstadoReporte() {
        borradorStorage?.eliminarBorradorLocal()
        _uiState.value = EstadoReporte()
    }

    fun obtenerMisReportesDelBackEnd(token: String? = null) {
        viewModelScope.launch {
            _cargando.value = true
            val resultado = repository.obtenerListaReportes(token = token)

            resultado.onSuccess { lista ->
                _misReportes.value = lista.map { res ->
                    ReporteModel(
                        folio = res.folio ?: "",
                        estatus = res.estatus ?: "Pendiente",
                        fechaEnvio =formatearFechaLocal(res.fechaEnvio),
                        descripcion = res.descripcion ?: res.mensaje ?: "",
                        direccion = res.direccion ?: "",
                        latitud = res.latitud ?: 0.0,
                        longitud = res.longitud ?: 0.0
                    )
                }
            }.onFailure {
                _misReportes.value = emptyList()
            }
            _cargando.value = false
        }
    }

    fun siguientePaso() {
        _uiState.update { it.copy(pasoActual = (it.pasoActual + 1).coerceAtMost(4)) }
    }

    fun anteriorPaso() {
        _uiState.update { it.copy(pasoActual = (it.pasoActual - 1).coerceAtLeast(0)) }
    }

    fun seleccionarOpcionUbicacion(opcion: String) {
        _uiState.update { it.copy(opcionUbicacion = opcion, ubicacionConfirmada = false) }
    }

    fun setUbicacion(
        lat: Double,
        lng: Double,
        direccion: String = "",
        idTerritorio: Int = 1
    ) {
        _uiState.update {
            it.copy(
                latitud = lat,
                longitud = lng,
                direccion = direccion,
                idTerritorio = idTerritorio,
                ubicacionConfirmada = true
            )
        }
    }

    fun setHorarioAprox(horario: String) {
        _uiState.update { it.copy(horarioAprox = horario) }
    }

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
                descripcionFisica = menorActual.descripcionFisica,
                peligroInmediato = menorActual.peligroInmediato
            )
        }
    }

    fun setAdultoInvolucrado(opcion: String) {
        _uiState.update { it.copy(adultoInvolucrados = opcion) }
    }

    fun setDescripcionHechos(texto: String) {
        _uiState.update { it.copy(descripcionHechos = texto) }
    }

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

    fun setPeligroInmediato(opcion: String) {
        _uiState.update { estado ->
            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual
            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }
            menoresActualizados[indice] = menoresActualizados[indice].copy(peligroInmediato = opcion)
            estado.copy(menores = menoresActualizados, peligroInmediato = opcion)
        }
    }

    fun setDescripcionFisica(texto: String) {
        _uiState.update { estado ->
            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual
            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }
            menoresActualizados[indice] =
                menoresActualizados[indice].copy(descripcionFisica = texto)
            estado.copy(menores = menoresActualizados, descripcionFisica = texto)
        }
    }

    private fun seleccionarMenor(indice: Int) {
        _uiState.update { estado ->
            val indiceSeguro = indice.coerceIn(0, (estado.cantidadMenores - 1).coerceAtLeast(0))
            val menor = estado.menores.getOrNull(indiceSeguro) ?: DatosMenor()
            estado.copy(
                indiceMenorActual = indiceSeguro,
                rangoEdad = menor.rangoEdad,
                genero = menor.genero,
                descripcionFisica = menor.descripcionFisica,
                peligroInmediato = menor.peligroInmediato
            )
        }
    }

    fun irAlPrimerMenor() = seleccionarMenor(0)

    fun avanzarAlSiguienteMenor(): Boolean {
        val estado = _uiState.value
        val siguienteIndice = estado.indiceMenorActual + 1
        if (siguienteIndice >= estado.cantidadMenores) return false
        seleccionarMenor(siguienteIndice)
        return true
    }

    fun regresarAlMenorAnterior(): Boolean {
        val indiceAnterior = _uiState.value.indiceMenorActual - 1
        if (indiceAnterior < 0) return false
        seleccionarMenor(indiceAnterior)
        return true
    }

    fun seleccionarTipoEvidencia(tipo: String) {
        _uiState.update { it.copy(tipoEvidenciaSeleccionada = tipo) }
    }

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

    fun eliminarEvidencia(uri: Uri) {
        _uiState.update { estado ->
            val actualizadas = estado.evidencias.filterNot { it.uri == uri }
            estado.copy(evidencias = actualizadas, evidenciaUri = actualizadas.firstOrNull()?.uri)
        }
    }

    fun actualizarEvidenciaUri(uri: Uri?) {
        _uiState.update { it.copy(evidenciaUri = uri) }
    }

    fun actualizarOpcionPrivacidad(nuevaOpcion: String) {
        val opcionNormalizada = if (nuevaOpcion == "anónimo") "anonimo" else nuevaOpcion
        if (opcionNormalizada != "anonimo" && opcionNormalizada != "actualizaciones") return
        _uiState.update { it.copy(opcionPrivacidad = opcionNormalizada) }
    }

    fun setEsAnonimo(valor: Boolean) {
        actualizarOpcionPrivacidad(if (valor) "anonimo" else "actualizaciones")
    }

    fun setCorreoSeguimiento(correo: String) {
        _uiState.update { it.copy(correoSeguimiento = correo) }
    }

    fun descripcionHechosValida(): Boolean = _uiState.value.descripcionHechos.trim().isNotEmpty()

    fun descripcionFisicaValida(): Boolean = _uiState.value.descripcionFisica.trim().isNotEmpty()

    fun ubicacionValida(): Boolean = _uiState.value.ubicacionConfirmada

    fun correoSeguimientoValido(): Boolean {
        val estado = _uiState.value
        if (estado.opcionPrivacidad == "anonimo") return true
        val correo = estado.correoSeguimiento.trim()
        return try {
            Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        } catch (_: Throwable) {
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(correo)
        }
    }

    fun limpiarErrorEnvio() {
        _uiState.update { it.copy(errorEnvio = null) }
    }

    fun limpiarReporte() {
        _uiState.value = EstadoReporte()
    }

    fun obtenerDetalleReportePorFolio(folio: String, token: String? = null) {
        viewModelScope.launch {
            _cargando.value = true
            val resultado = repository.obtenerReportePorFolio(folio, token)
            resultado.onSuccess { res ->
                _detalleReporte.value = ReporteModel(
                    folio = res.folio ?: "",
                    estatus = res.estatus ?: "Pendiente",
                    fechaEnvio = formatearFechaLocal(res.fechaEnvio),
                    descripcion = res.descripcion ?: res.mensaje ?: "",
                    direccion = res.direccion ?: "",
                    latitud = res.latitud ?: 0.0,
                    longitud = res.longitud ?: 0.0
                )
            }.onFailure {
                _detalleReporte.value = null
            }
            _cargando.value = false
        }
    }



    fun limpiarBusqueda() {
        _reporteEncontrado.value = null
        _errorBusqueda.value = null
        _buscandoReporte.value = false
    }

    fun enviarReporteAlBackEnd(context: Context, token: String? = null, userId:Long? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(enviando = true, errorEnvio = null) }

            val estadoActual = _uiState.value

            // 1. Mapeo de menores cumpliendo la regla de Go:
            // "Masculino", "Femenino" o "No identificado" (convierte "Desconocido" a "No identificado")
            val listaMenores = if (estadoActual.menores.isNotEmpty()) {
                estadoActual.menores.mapIndexed { index, m ->
                    val generoValido = when (m.genero.trim()) {
                        "Masculino" -> "Masculino"
                        "Femenino" -> "Femenino"
                        else -> "No identificado" // "Desconocido" o vacío se convierte a "No identificado"
                    }
                    MenorInput(
                        contador = index + 1,
                        genero = generoValido,
                        descripcionFisica = m.descripcionFisica.take(255) // Máximo 255 caracteres
                    )
                }
            } else {
                listOf(
                    MenorInput(
                        contador = 1,
                        genero = "No identificado",
                        descripcionFisica = estadoActual.descripcionFisica.take(255)
                    )
                )
            }

            // 2. Formato estricto para Go: "HH:MM" (time.Parse("15:04"))
            val horarioFormatoGo = estadoActual.horarioAprox.trim().takeIf { it.isNotBlank() }?.let {
                if (it.length > 5) it.substring(0, 5) else it
            }

            // 3. Peligro inmediato requerido por Go: "Sí", "No" o "No sé" (con acento)
            val peligroValido = when (estadoActual.peligroInmediato.trim().lowercase()) {
                "sí", "si" -> "Sí"
                "no" -> "No"
                else -> "No sé"
            }

            // 4. Territorio: ID positivo (> 0). Se usa 13 por defecto si no viene establecido
            val territorioValido = if (estadoActual.idTerritorio <= 1) 126 else estadoActual.idTerritorio

            // 5. Construcción de la petición
            val peticion = ReporteRequest(
                idUsuario = userId,
                descripcion = estadoActual.descripcionHechos.ifBlank { "Reporte ciudadano" },
                horarioAproximado = horarioFormatoGo, // 👈 Formato HH:MM
                esAnonimo = estadoActual.opcionPrivacidad == "anonimo",
                deseaSeguimiento = estadoActual.opcionPrivacidad != "anonimo",
                hayAdultoInvolucrado = estadoActual.adultoInvolucrados.equals("si", ignoreCase = true) ||
                        estadoActual.adultoInvolucrados.equals("sí", ignoreCase = true),
                peligroInmediato = peligroValido, // 👈 "Sí", "No" o "No sé"
                correoContacto = estadoActual.correoSeguimiento.trim().takeIf { it.isNotBlank() },
                ubicacion = UbicacionInput(
                    latitud = estadoActual.latitud,
                    longitud = estadoActual.longitud,
                    direccionTexto = estadoActual.direccion.ifBlank { "Sin dirección exacta" },
                    idTerritorio = territorioValido
                ),
                menores = listaMenores,
                etiquetas = listOf(
                    EtiquetaInput(
                        nombreEtiqueta = "Trabajo infantil",
                        tipo = "Categoria"
                    )
                )
            )

            // 6. Enviar al backend
            val resultado = repository.crearReporte(peticion, token)

            resultado.onSuccess { respuesta ->
                val folioGenerado = respuesta.folio ?: ""
                val codigoGenerado = respuesta.codigoPrivado ?: ""

                if (estadoActual.evidencias.isNotEmpty() && folioGenerado.isNotBlank()) {
                    val partes = prepararEvidenciasParaBackend(context)
                    repository.subirEvidencias(folioGenerado, partes)
                }

                _uiState.update {
                    it.copy(
                        enviando = false,
                        reporteExitoso = true,
                        folio = folioGenerado,
                        codigoPrivado = codigoGenerado
                    )
                }
                borradorStorage?.eliminarBorradorLocal()

                obtenerMisReportesDelBackEnd(token)
            }.onFailure { error ->
                val mensajeDetallado = when (error) {
                    is HttpException -> {
                        try {
                            val jsonError = error.response()?.errorBody()?.string()
                            val json = JSONObject(jsonError ?: "")
                            json.optString(
                                "mensaje",
                                json.optString(
                                    "error",
                                    json.optString(
                                        "message",
                                        "Error en el servidor (${error.code()})"
                                    )
                                )
                            )
                        } catch (e: Exception) {
                            "Error del servidor (${error.code()})"
                        }
                    }
                    else -> error.localizedMessage ?: "No se pudo conectar con el servidor."
                }

                _uiState.update {
                    it.copy(
                        enviando = false,
                        errorEnvio = mensajeDetallado
                    )
                }
            }
        }
    }

    fun prepararEvidenciasParaBackend(context: Context): List<MultipartBody.Part> {
        val listaParts = mutableListOf<MultipartBody.Part>()

        _uiState.value.evidencias.forEach { evidencia ->
            val file = obtenerArchivoDesdeUri(context, evidencia.uri)
            file?.let {
                val mimeType =
                    context.contentResolver.getType(evidencia.uri) ?: "multipart/form-data"
                val requestFile = it.asRequestBody(mimeType.toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("archivos", it.name, requestFile)
                listaParts.add(part)
            }
        }
        return listaParts
    }

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

    // Función auxiliar para formatear la fecha UTC a hora local legible:

    fun formatearFechaLocal(fechaIso: String?): String {
        if (fechaIso.isNullOrBlank()) return ""
        return try {
            val instant = java.time.Instant.parse(fechaIso)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            fechaIso
        }
    }
}

