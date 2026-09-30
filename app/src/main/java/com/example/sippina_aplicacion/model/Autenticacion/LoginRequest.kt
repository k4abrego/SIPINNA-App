package com.example.sippina_aplicacion.model.Autenticacion

data class LoginRequest(
    val correo: String,
    val contrasena: String
)