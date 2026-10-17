package com.example.sippina_aplicacion.model.Autenticacion

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AutenticacionAPI {

    // Paso 1 de inicio de sesión
    @GET("Login/{correo}")
    suspend fun solicitarReto(
        @Path("correo") correo: String
    ): Response<ChallengeResponse>

    // Paso 2 de inicio de sesión (Devuelve TokenResponse en lugar de ResponseBody)
    @POST("Login/")
    suspend fun verificarReto(
        @Body request: VerifyChallengeInput
    ): Response<TokenResponse>

    // Registro de usuario
    @POST("Registro/")
    suspend fun registrar(
        @Body request: RegistroInput
    ): Response<RegistroResponse>

    // Cambiar contraseña
    @PATCH("Contrasenna/{correo}")
    suspend fun cambiarContrasena(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: CambiarContrasenaInput
    ): Response<RespuestaBase>

    // Cerrar sesión / Invalidar token por ID de usuario
    @DELETE("Login/{id}")
    suspend fun logout(
        @Path("id") userId: Long,
        @Header("Authorization") token: String
    ): Response<RespuestaBase>

    // Consultar datos del perfil
    @GET("Usuario/{correo}")
    suspend fun obtenerUsuario(
        @Path("correo") correo: String,
        @Header("Authorization") token: String
    ): Response<UsuarioResponse>

    // Editar perfil
    @PATCH("Usuario/{correo}")
    suspend fun editarUsuario(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: EditarUsuarioInput
    ): Response<RespuestaBase>

    // Cambiar correo electrónico
    @PATCH("Correo/")
    suspend fun cambiarCorreo(
        @Header("Authorization") token: String,
        @Body request: CambiarCorreoInput
    ): Response<RespuestaBase>
}