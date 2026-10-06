package com.example.sippina_aplicacion.model.Autenticacion

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Se definen las ApiRest
interface AutenticacionAPI {

    // Paso 1: Solicitar el reto pasando el correo en la URL
    @GET("Login/{correo}")
    suspend fun solicitarReto(
        @Path("correo") correo: String
    ): Response<ChallengeResponse>

    // Paso 2: Enviar el reto resuelto
    @POST("Login/")
    suspend fun verificarReto(
        @Body request: VerifyChallengeInput
    ): Response<TokenResponse>

    // Registro de usuario
    @POST("Registro/")
    suspend fun registrar(
        @Body request: RegistroInput
    ): Response<RegistroResponse>

    // Cambio de contraseña
    @PUT("Contrasenna/{correo}")
    suspend fun cambiarContrasena(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: CambiarContrasenaInput
    ): Response<RespuestaBase>
}