package com.example.sippina_aplicacion.model.Autenticacion

import com.example.sippina_aplicacion.model.Usuario

data class AuthResponse (
    val token:String,
    val usuario: Usuario
    )