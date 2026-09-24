package com.example.sippina_aplicacion.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.internal.format


class ReporteVM : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoReporte())
    val uiState: StateFlow<EstadoReporte> = _uiState.asStateFlow()

    fun siguientePaso() {
        _uiState.update { estado ->
            estado.copy(
                pasoActual = (estado.pasoActual + 1).coerceAtMost(4)
            )
        }
    }

    fun anteriorPaso() {
        _uiState.update { estado ->
            estado.copy(
                pasoActual = (estado.pasoActual - 1).coerceAtLeast(0)
            )
        }
    }

    fun seleccionarOpcionUbicacion(opcion: String) {
        _uiState.update { estado ->
            estado.copy(
                opcionUbicacion = opcion,
                ubicacionConfirmada = false
            )
        }
    }

    fun setUbicacion(
        lat: Double,
        lng: Double,
        direccion: String = ""
    ) {
        _uiState.update { estado ->
            estado.copy(
                latitud = lat,
                longitud = lng,
                direccion = direccion,
                ubicacionConfirmada = true
            )
        }
    }

    fun setHorarioAprox(horario: String) {
        _uiState.update { estado ->
            estado.copy(
                horarioAprox = horario
            )
        }
    }

    fun cambiarCantidadMenores(delta: Int) {
        _uiState.update { estado ->
            val nuevaCantidad = (estado.cantidadMenores + delta).coerceAtLeast(1)

            // agregamos un registro por cada menor nuevo. si disminuye la cantidad, conservamos los datos anteriores por si vuelve a aumentarla.
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
        _uiState.update { estado ->
            estado.copy(
                adultoInvolucrados = opcion
            )
        }
    }

    fun setDescripcionHechos(texto: String) {
        _uiState.update { estado ->
            estado.copy(
                descripcionHechos = texto
            )
        }
    }

    private fun menorActual(estado: EstadoReporte): DatosMenor {
        return estado.menores.getOrNull(
            estado.indiceMenorActual
        ) ?: DatosMenor()
    }

    fun setRangoEdad(rango: String) {
        _uiState.update { estado ->

            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual

            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }

            menoresActualizados[indice] =
                menoresActualizados[indice].copy(
                    rangoEdad = rango
                )

            estado.copy(
                menores = menoresActualizados,
                rangoEdad = rango
            )
        }
    }

    fun setGenero(genero: String) {
        _uiState.update { estado ->

            val menoresActualizados = estado.menores.toMutableList()
            val indice = estado.indiceMenorActual

            while (menoresActualizados.size <= indice) {
                menoresActualizados.add(DatosMenor())
            }

            menoresActualizados[indice] =
                menoresActualizados[indice].copy(
                    genero = genero
                )

            estado.copy(
                menores = menoresActualizados,
                genero = genero
            )
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
                menoresActualizados[indice].copy(
                    descripcionFisica = texto
                )

            estado.copy(
                menores = menoresActualizados,
                descripcionFisica = texto
            )
        }
    }
    private fun seleccionarMenor(indice: Int) {
        _uiState.update { estado ->

            val indiceSeguro = indice.coerceIn(
                0,
                (estado.cantidadMenores - 1).coerceAtLeast(0)
            )

            val menor = estado.menores.getOrNull(
                indiceSeguro
            ) ?: DatosMenor()

            estado.copy(
                indiceMenorActual = indiceSeguro,
                rangoEdad = menor.rangoEdad,
                genero = menor.genero,
                descripcionFisica = menor.descripcionFisica
            )
        }
    }

    fun irAlPrimerMenor() {
        seleccionarMenor(0)
    }

    fun avanzarAlSiguienteMenor(): Boolean {
        val estado = _uiState.value
        val siguienteIndice = estado.indiceMenorActual + 1
        if (siguienteIndice >= estado.cantidadMenores) {
            return false
        }
        seleccionarMenor(siguienteIndice)
        return true
    }

    fun regresarAlMenorAnterior(): Boolean {
        val indiceAnterior = _uiState.value.indiceMenorActual - 1
        if (indiceAnterior < 0) {
            return false
        }
        seleccionarMenor(indiceAnterior)
        return true
    }

    fun seleccionarTipoEvidencia(tipo: String) {
        _uiState.update { estado ->
            estado.copy(
                tipoEvidenciaSeleccionada = tipo
            )
        }
    }

    fun agregarEvidencia(
        uri: Uri,
        tipo: String,
        nombre: String = ""
    ) {
        _uiState.update { estado ->
            //evita agregar accidentalmente dos veces el mismo archivo
            val yaExiste = estado.evidencias.any {
                it.uri == uri
            }
            if (yaExiste) {
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
            val evidenciasActualizadas =
                estado.evidencias.filterNot {
                    it.uri == uri
                }
            estado.copy(
                evidencias = evidenciasActualizadas,
                evidenciaUri = evidenciasActualizadas
                    .firstOrNull()
                    ?.uri
            )
        }
    }

    fun actualizarEvidenciaUri(uri: Uri?) {
        _uiState.update { estado ->
            estado.copy(
                evidenciaUri = uri
            )
        }
    }

    fun actualizarOpcionPrivacidad(nuevaOpcion: String) {
        if (
            nuevaOpcion != "anonimo" &&
            nuevaOpcion != "actualizaciones"
        ) {
            return
        }
        _uiState.update { estado ->
            estado.copy(
                opcionPrivacidad = nuevaOpcion,
                esAnonimo = nuevaOpcion == "anonimo",
                recibirActualizaciones = nuevaOpcion == "actualizaciones"
            )
        }
    }

    fun setEsAnonimo(valor: Boolean) {
        actualizarOpcionPrivacidad(
            if (valor) "anonimo"
            else "actualizaciones"
        )
    }

    fun setCorreoSeguimiento(correo: String) {
        _uiState.update { estado ->
            estado.copy(
                correoSeguimiento = correo
            )
        }
    }

    fun descripcionHechosValida(): Boolean {
        return _uiState.value.descripcionHechos.trim().isNotEmpty()
    }

    fun descripcionFisicaValida(): Boolean {
        return _uiState.value.descripcionFisica.trim().isNotEmpty()
    }

    fun ubicacionValida(): Boolean {
        return _uiState.value.ubicacionConfirmada
    }

    fun correoSeguimientoValido(): Boolean {
        val estado = _uiState.value
        if (estado.opcionPrivacidad == "anonimo") {
            return true
        }
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(estado.correoSeguimiento.trim())
            .matches()
    }

    fun enviarReporteFinal() {
        _uiState.update { estado ->
            estado.copy(
                enviando = false,
                reporteExitoso = false,
                errorEnvio = "El envío del reporte todavía no está disponible."
            )
        }
    }

    fun limpiarErrorEnvio() {
        _uiState.update { estado ->
            estado.copy(
                errorEnvio = null
            )
        }
    }

    fun limpiarReporte() {
        _uiState.value = EstadoReporte()
    }
}

