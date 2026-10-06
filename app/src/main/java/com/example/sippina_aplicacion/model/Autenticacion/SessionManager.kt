package com.example.sippina_aplicacion.model.Autenticacion
// Este es para guardar la sesion del usuario

object SessionManager {
    var token: String? = null

    fun guardarSesion(nuevoToken: String) {
        token = nuevoToken
    }
    fun obtenerToken(): String? {
        return token
    }
    fun cerrarSesion() {
        token = null
    }
}