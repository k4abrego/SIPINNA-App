package com.example.sippina_aplicacion.model

/**
 * Modelo del dominio que representa la entidad de un usuario registrado en la aplicación SIPINNA.
 *
 * Contiene los datos personales del usuario utilizados para la personalización de la interfaz
 * y la vinculación de perfiles en las vistas de la aplicación.
 *
 * @property idUsuario Identificador numérico único del usuario en el sistema.
 * @property correo Dirección de correo electrónico asociada a la cuenta del usuario.
 * @property nombre Nombre(s) del usuario.
 * @property apellidos Apellidos paterno y materno del usuario.
 * @property telefono Número de teléfono de contacto opcional; `null` si no fue proporcionado.
 */
data class Usuario(
    val idUsuario: Int,
    val correo: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String? = null
) {
    /**
     * Representación en cadena de texto del identificador numérico del usuario.
     *
     * Facilita la interoperabilidad con componentes visuales o APIs que requieren un ID de tipo [String].
     *
     * @return El [idUsuario] convertido a [String].
     */
    val id: String
        get()= idUsuario.toString()

    /**
     * Nombre completo del usuario estructurado en dos líneas para su presentación en componentes visuales.
     *
     * @return Cadena con el nombre en la primera línea y los apellidos en la segunda,
     * eliminando espacios sobrantes al inicio o final.
     */
    val nombreCompletoFormateado: String
        get() = "$nombre\n$apellidos".trim()
}
