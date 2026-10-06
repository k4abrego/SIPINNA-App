package com.example.sippina_aplicacion.viewmodel
// Esto es para las notificaciones
data class EstadoNotificacion(
    val id: Int = 0,
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val folio: String = "",
    val leida: Boolean = false
)

// Estado para la pantalla
data class EstadoNotificaciones(
    val lista: List<EstadoNotificacion> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)

