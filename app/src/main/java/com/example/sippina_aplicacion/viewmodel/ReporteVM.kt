package com.example.sippina_aplicacion.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sippina_aplicacion.model.BorradorGuardado
import com.example.sippina_aplicacion.model.Reporte.PeticionCrearReporte
import com.example.sippina_aplicacion.model.Reporte.ReporteRepository
import com.example.sippina_aplicacion.model.Reporte.ReporteRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.format
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


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

    fun siguientePaso() {
        _uiState.update { it.copy(pasoActual = (it.pasoActual + 1).coerceAtMost(4)) }
    }

    fun anteriorPaso() {
        _uiState.update { it.copy(pasoActual = (it.pasoActual - 1).coerceAtLeast(0)) }
    }

    fun seleccionarOpcionUbicacion(opcion: String) {
        _uiState.update { it.copy(opcionUbicacion = opcion, ubicacionConfirmada = false) }
    }

    fun setUbicacion(lat: Double, lng: Double, direccion: String = "") {
        _uiState.update {
            it.copy(latitud = lat, longitud = lng, direccion = direccion, ubicacionConfirmada = true)
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
                descripcionFisica = menorActual.descripcionFisica
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
                    evidencias = estado.evidencias + EvidenciaReporte(uri = uri, tipo = tipo, nombre = nombre)
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
        return android.util.Patterns.EMAIL_ADDRESS.matcher(estado.correoSeguimiento.trim()).matches()
    }

    fun limpiarErrorEnvio() {
        _uiState.update { it.copy(errorEnvio = null) }
    }

    fun limpiarReporte() {
        _uiState.value = EstadoReporte()
    }

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

    fun limpiarBusqueda() {
        _reporteEncontrado.value = null
        _errorBusqueda.value = null
        _buscandoReporte.value = false
    }

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
}

