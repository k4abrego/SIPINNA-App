package com.example.sippina_aplicacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.IDN

class NotificacionesVM : ViewModel() {

    private val _state = MutableStateFlow(EstadoNotificaciones())
    val state: StateFlow<EstadoNotificaciones> = _state.asStateFlow()

    fun cargarNotificaciones(usuarioId: String) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }
            try {
                // 1. Corregido: Se agregó _state.update { ... }
                _state.update {
                    it.copy(
                        lista = listOf(
                            EstadoNotificacion(1, "Reporte actualizado", "Tu reporte cambió a En revisión.", "Hace 10 min", "REP-1234", false),
                            EstadoNotificacion(2, "Caso recibido", "Se recibió correctamente tu reporte", "Ayer", "REP-1234", true)
                        ),
                        cargando = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(cargando = false, error = e.localizedMessage ?: "Error al cargar notificaciones")
                }
            }
        }
    }

    fun marcarComoLeida(idNotificacion: Int) {
        _state.update { estado ->
            estado.copy(
                lista = estado.lista.map {
                    if (it.id == idNotificacion) it.copy(leida = true) else it
                }
            )
        }

        viewModelScope.launch {
            try {
                // Petición al servidor (Retrofit / API)
            } catch (_: Exception) {
                // En caso de error se  revierte el cambio
                marcarComoNoLeida(idNotificacion)
            }
        }
    }
    // estado por si llega a fallar la petición
    private fun marcarComoNoLeida(idNotificacion: Int) {
        _state.update { estado ->
            estado.copy(
                lista = estado.lista.map {
                    if (it.id == idNotificacion) it.copy(leida = false) else it
                }
            )
        }
    }
}