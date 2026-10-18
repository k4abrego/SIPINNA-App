package com.example.sippina_aplicacion.model.Autenticacion

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AutenticacionAPI {

    /**
     * Solicita el desafío (challenge) para autenticación CHAP.
     * Ruta Go: GET /Login/:correo
     */
    @GET("Login/{correo}")
    suspend fun solicitarReto(
        @Path("correo") correo: String
    ): Response<ChallengeResponse>

    /**
     * Envía la respuesta HMAC-SHA256 para verificar el reto y recibir el JWT.
     * Ruta Go: POST /Login/
     */
    @POST("Login/")
    suspend fun verificarReto(
        @Body request: VerifyChallengeInput
    ): Response<TokenResponse>

    /**
     * Registra un nuevo usuario en la plataforma.
     * Ruta Go: POST /Registro/
     */
    @Headers("Content-Type: application/json")
    @POST("Registro/")
    suspend fun registrar(
        @Body request: RegistroInput
    ): Response<RegistroResponse>

    /**
     * Obtiene los datos del perfil del usuario autenticado.
     * Ruta Go: GET /Usuario/:correo
     */
    @GET("Usuario/{correo}")
    suspend fun obtenerUsuario(
        @Path("correo") correo: String,
        @Header("Authorization") token: String
    ): Response<UsuarioResponse>

    /**
     * Edita los datos personales del usuario.
     * Ruta Go: PATCH /Usuario/:correo
     */
    @PATCH("Usuario/{correo}")
    suspend fun editarUsuario(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: EditarUsuarioInput
    ): Response<Unit>

    /**
     * Cambia la contraseña del usuario.
     * Ruta Go: PATCH /Contrasenna/:correo
     */
    @PATCH("Contrasenna/{correo}")
    suspend fun cambiarContrasenna(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: CambiarContrasenaInput
    ): Response<Unit>

    /**
     * Invalida la sesión actual del usuario.
     * Ruta Go: DELETE /Login/:id
     */
    @DELETE("Login/{id}")
    suspend fun logout(
        @Path("id") userId: Long,
        @Header("Authorization") token: String
    ): Response<Unit>
}