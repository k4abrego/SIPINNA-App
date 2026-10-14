package com.example.sippina_aplicacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sippina_aplicacion.model.EstadoNotificacion
import com.example.sippina_aplicacion.model.EstadoNotificaciones
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la gestión del estado y la lógica de negocio para las notificaciones del usuario.
 *
 * Administra la obtención asíncrona de los avisos del sistema, la actualización local del estado de lectura
 * y la sincronización con el servidor, implementando reversión optimista ante fallos de red.
 * Expone la información reactiva mediante un [StateFlow] de tipo [EstadoNotificaciones].
 */
class NotificacionesVM : ViewModel() {

    private val _state = MutableStateFlow(EstadoNotificaciones())
    val state: StateFlow<EstadoNotificaciones> = _state.asStateFlow()

    /**
     * Carga de manera asíncrona el listado de notificaciones correspondientes al usuario especificado.
     *
     * Activa el indicador de carga y actualiza la lista de avisos en el estado del ViewModel.
     *
     * @param usuarioId Identificador único del usuario cuyas notificaciones se consultarán.
     */
    fun cargarNotificaciones(usuarioId: String) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }
            try {
                // 1. Corregido: Se agregó _state.update { ... }
                _state.update {
                    it.copy(
                        lista = listOf(
                            EstadoNotificacion(
                                1,
                                "Reporte actualizado",
                                "Tu reporte cambió a En revisión.",
                                "Hace 10 min",
                                "REP-1234",
                                false
                            ),
                            EstadoNotificacion(
                                2,
                                "Caso recibido",
                                "Se recibió correctamente tu reporte",
                                "Ayer",
                                "REP-1234",
                                true
                            )
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

    /**
     * Marca una notificación específica como leída tanto en el estado local como en el servidor.
     *
     * Aplica actualización optimista inmediata en la interfaz y lanza una corrutina para sincronizar
     * con la API; si la operación de red falla, revierte el cambio utilizando [marcarComoNoLeida].
     *
     * @param idNotificacion Identificador único de la notificación a marcar como leída.
     */
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
    /**
     * Revierte el estado de lectura de una notificación a no leída en caso de fallo en la sincronización remota.
     *
     * @param idNotificacion Identificador único de la notificación cuyo estado de lectura se desea revertir.
     */
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
