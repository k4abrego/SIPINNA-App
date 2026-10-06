package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName


// Son modelos de peticion para el componente de autenticación
data class ChallengeResponse(
    val userId:Long,
    val challenge:String,
    val mensaje:String? = null

)
data class VerifyChallengeInput(
    val userId: Long,
    val response:String,
)

data class TokenResponse(
    val token:String,
)

// Esta es la respuesta del registro
data class RegistroInput(
    val nombre: String,
    val apellido: String,
    val correo: String,
    @SerializedName("contraseña") // El backend de Go espera contraseña con ñ
    val contrasena: String,
    val telefono: String? = null
)

data class RegistroResponse(
    val userId: Long,
    val mensaje: String
)

data class CambiarContrasenaInput(
    @SerializedName("nueva_contraseña")
    val nuevaContrasena: String,

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
    val nombre: String,
    val apellidos: String,
    val telefono: String? = null
)

data class RecuperarContrasenaPeticion(
    val correo: String
)

data class AuthResponse(
    val token: String,
    val usuario: UsuarioPerfilModel
)

data class UsuarioPerfilModel(
    val idUsuario: Int,
    val correo: String,
    val nombre: String,
    val apellidos: String,
    val telefono: String? = null
)