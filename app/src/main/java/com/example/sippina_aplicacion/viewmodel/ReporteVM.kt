package com.example.sippina_aplicacion.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReporteVM : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoReporte())
    val uiState: StateFlow<EstadoReporte> = _uiState.asStateFlow()

    fun siguientePaso() {
        if (_uiState.value.pasoActual < 4) {
            _uiState.update { it.copy(pasoActual = it.pasoActual + 1) }
        }
    }

    fun anteriorPaso() {
        if (_uiState.value.pasoActual > 0) {
            _uiState.update { it.copy(pasoActual = it.pasoActual - 1) }
        }
    }

    fun seleccionarOpcionUbicacion(opcion: String) {
        _uiState.update { it.copy(opcionUbicacion = opcion) }
    }

    fun cambiarCantidadMenores(delta: Int) {
        val nuevaCantidad = (_uiState.value.cantidadMenores + delta).coerceAtLeast(1)
        _uiState.update { it.copy(cantidadMenores = nuevaCantidad) }
    }

    fun setAdultoInvolucrado(opcion: String) {
        _uiState.update { it.copy(adultoInvolucrados = opcion) }
    }

    fun setDescripcionHechos(texto: String) {
        _uiState.update { it.copy(descripcionHechos = texto) }
    }

    fun setRangoEdad(rango: String) {
        _uiState.update { it.copy(rangoEdad = rango) }
    }

    fun setGenero(genero: String) {
        _uiState.update { it.copy(genero = genero) }
    }

    fun setDescripcionFisica(texto: String) {
        _uiState.update { it.copy(descripcionFisica = texto) }
    }

    fun setEsAnonimo(valor: Boolean) {
        _uiState.update { it.copy(esAnonimo = valor) }
    }

    fun enviarReporteFinal() {

    }
}