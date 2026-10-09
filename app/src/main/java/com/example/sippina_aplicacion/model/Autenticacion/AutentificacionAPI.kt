package com.example.sippina_aplicacion.model.Autenticacion

import okhttp3.ResponseBody
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

    // Paso 1: Solicitar el reto pasando el correo en la URL (/auth/:email)
    @GET("auth/{correo}")
    suspend fun solicitarReto(
        @Path("correo") correo: String
    ): Response<ChallengeResponse>

    // Paso 2: Enviar el reto resuelto (/auth/)
    @POST("auth/")
    suspend fun verificarReto(
        @Body request: VerifyChallengeInput
    ): Response<ResponseBody>

    // Registro de usuario (/register)
    @POST("register")
    suspend fun registrar(
        @Body request: RegistroInput
    ): Response<RegistroResponse>

    // Cambio de contraseña (/password/:email)
    @PUT("password/{correo}")
    suspend fun cambiarContrasena(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: CambiarContrasenaInput
    ): Response<RespuestaBase>
}