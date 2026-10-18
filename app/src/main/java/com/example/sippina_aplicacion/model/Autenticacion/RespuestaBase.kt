package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName

/**
 * Modelo genérico que representa la estructura base de respuesta devuelta por la API REST del backend.
 *
 * Utilizado para deserializar respuestas estándar de la API en operaciones diversas de autenticación y
 * gestión de cuenta, encapsulando códigos de estado, mensajes del servidor e identificadores opcionales.
 *
 * @property status Código numérico de respuesta de la API o estado HTTP retornado por la petición.
 * @property mensaje Mensaje descriptivo enviado por el servidor con detalles del resultado de la operación.
 * @property token Token de autenticación o sesión (JWT) opcional devuelto tras procesar la petición.
 * @property idUsuario Identificador del usuario retornado opcionalmente como cadena de texto.
 */

data class RespuestaBase(
    @SerializedName("message") val mensaje: String? = null,
    @SerializedName("error") val error: String? = null
)
