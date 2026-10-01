package com.example.sippina_aplicacion.model.Autenticacion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// Se definen las ApiRest
interface AutentificacionAPI{
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ):Response<AuthResponse>

    @POST("auth/registro")
    suspend fun registrarUsuario(@Body request: RegistroRequest): Response<AuthResponse>
}