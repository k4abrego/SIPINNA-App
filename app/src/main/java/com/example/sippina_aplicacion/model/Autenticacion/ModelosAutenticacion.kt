package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName


data class ChallengeResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("challenge")
    val challenge: String,

    @SerializedName("mensaje")
    val mensaje: String? = null
)

data class VerifyChallengeInput(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("response")
    val response: String
)

data class TokenResponse(
    @SerializedName("token")
    val token: String
)

data class RegistroInput(
    @SerializedName("name")
    val nombre: String,

    @SerializedName("surname")
    val apellido: String,

    @SerializedName("email")
    val correo: String,

    @SerializedName("password")
    val contrasena: String,

    @SerializedName("phone_number")
    val telefono: String? = null
)

data class RegistroResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("mensaje")
    val mensaje: String? = null
)

data class CambiarContrasenaInput(
    @SerializedName("nueva_contraseña")
    val nuevaContrasena: String
)

data class InicioSesionModel(
    val correo: String,
    val contrasena: String
)

data class RegistroModel(
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val apellidos: String,
    val telefono: String? = null
)

data class ActualizarPerfilPeticion(
    @SerializedName("name")
    val nombre: String,

    @SerializedName("surname")
    val apellidos: String,

    @SerializedName("phone_number")
    val telefono: String? = null
)

data class RecuperarContrasenaPeticion(
    @SerializedName("email")
    val correo: String
)

data class AuthResponse(
    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val usuario: UsuarioPerfilModel
)

data class UsuarioPerfilModel(
    @SerializedName("user_id")
    val idUsuario: Int,

    @SerializedName("email")
    val correo: String,

    @SerializedName("name")
    val nombre: String,

    @SerializedName("surname")
    val apellidos: String,

    @SerializedName("phone_number")
    val telefono: String? = null
)