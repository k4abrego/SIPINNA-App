package com.example.sippina_aplicacion.model.Autenticacion

data class RegistroRequest(
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val apellidos: String,
    val telefono: String?
)