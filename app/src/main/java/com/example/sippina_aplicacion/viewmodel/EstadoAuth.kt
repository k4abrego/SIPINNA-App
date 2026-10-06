package com.example.sippina_aplicacion.viewmodel

data class EstadoAuth(
    val token: String = "",
    val usuarioId: String? = null,
    val nombre: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val telefono: String? = null,
    val autenticado: Boolean = false,
    val cargando: Boolean = false,
    val error: String? = null,
    val mensajeExito: String? = null
)


