package com.example.sippina_aplicacion.model.Autenticacion

/**
 * Objeto Singleton responsable de administrar el estado de la sesión activa del usuario en memoria.
 *
 * Mantiene en memoria el token de acceso JWT obtenido durante el inicio de sesión o registro,
 * permitiendo su inyección y consulta desde los repositorios ([AuthRepository]) y componentes de red.
 */
object SessionManager {
    /**
     * Token de sesión (JWT) activo en memoria; es `null` si el usuario no ha iniciado sesión.
     */
    var token: String? = null

    /**
     * Almacena el token de acceso recibido tras un inicio de sesión o registro exitoso.
     *
     * @param nuevoToken Cadena que contiene el nuevo token de sesión que permanecerá activo.
     */
    fun guardarSesion(nuevoToken: String) {
        token = nuevoToken
    }

    /**
     * Recupera el token de sesión almacenado actualmente en memoria.
     *
     * @return El token JWT si existe una sesión activa, o `null` si no se ha autenticado el usuario.
     */
    fun obtenerToken(): String? {
        return token
    }

    /**
     * Purga el token de sesión almacenado en memoria, cerrando la sesión activa localmente.
     */
    fun cerrarSesion() {
        token = null
    }
}
