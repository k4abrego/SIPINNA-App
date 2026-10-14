package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName

/**
 * Respuesta devuelta por el servidor durante el primer paso del inicio de sesión (GET /Login/{correo}).
 */
data class ChallengeResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("challenge")
    val challenge: String,

    @SerializedName("mensaje")
    val mensaje: String? = null
)

/**
 * Objeto de transferencia de datos (DTO) enviado al servidor para verificar el reto (POST /Login/).
 */
data class VerifyChallengeInput(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("response")
    val response: String
)

/**
 * Respuesta que contiene el token de acceso obtenido tras la autenticación.
 */
data class TokenResponse(
    @SerializedName("token")
    val token: String
)

/**
 * Modelo de petición enviado a la API para registrar una nueva cuenta (POST /Registro/).
 * Nota: Los nombres deben coincidir con la estructura `RegistroInput` de auth-API en Go.
 */
data class RegistroInput(
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellido: String,

    @SerializedName("correo")
    val correo: String,

    @SerializedName("contraseña")
    val contrasena: String,

    @SerializedName("numero_de_telefono")
    val telefono: String? = null
)

/**
 * Respuesta obtenida del servidor tras procesar un registro exitoso.
 */
data class RegistroResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("mensaje")
    val mensaje: String? = null
)

/**
 * Modelo de petición para cambiar la contraseña (PATCH /Contrasenna/{correo}).
 */
data class CambiarContrasenaInput(
    @SerializedName("new_password")
    val nuevaContrasena: String
)

/**
 * Petición enviada al servidor para modificar los datos del perfil (PATCH /Usuario/{correo}).
 */
data class ActualizarPerfilPeticion(
    @SerializedName("nombre")
    val nombre: String? = null,

    @SerializedName("apellido")
    val apellidos: String? = null,

    @SerializedName("numero_de_telefono")
    val telefono: String? = null
)

/**
 * Modelo que representa el perfil devuelto por la API (GET /Usuario/{correo}).
 */
data class UsuarioPerfilModel(
    @SerializedName("user_id")
    val idUsuario: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellidos: String,

    @SerializedName("numero_de_telefono")
    val telefono: String? = null,

    @SerializedName("correo")
    val correo: String = ""
)

/**
 * Petición enviada a la API para solicitar la recuperación de contraseña.
 */
data class RecuperarContrasenaPeticion(
    @SerializedName("email")
    val correo: String
)

/**
 * Respuesta combinada de autenticación utilizada localmente si se requiere envolver token y usuario.
 */
data class AuthResponse(
    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val usuario: UsuarioPerfilModel
)


/**
 * Modelo local utilizado por Compose/ViewModel para capturar el formulario de Login.
 */
data class InicioSesionModel(
    val correo: String,
    val contrasena: String
)

/**
 * Modelo local utilizado por Compose/ViewModel para capturar el formulario de Registro.
 */
data class RegistroModel(
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val apellidos: String,
    val telefono: String? = null
)