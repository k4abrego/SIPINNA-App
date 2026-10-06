package com.example.sippina_aplicacion.model

data class Usuario(
    val idUsuario: Int,
    val correo: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String? = null
) {
    val id: String
        get()= idUsuario.toString()

    val nombreCompletoFormateado: String
        get() = "$nombre\n$apellidos".trim()
}