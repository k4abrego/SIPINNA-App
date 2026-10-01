package com.example.sippina_aplicacion.model.Autenticacion

import java.security.MessageDigest

// Se crea el repositorio de autenticacion en donde se definen los metodos de autenticacion
class AuthRepository(
    private val api: AutentificacionAPI = ServicioRemoto.autentificacionAPI
) {
    private fun String.toSha256(): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
    // Este metodo se debe de usar para iniciar sesion en el viewModel
    suspend fun iniciarSesion(correo: String, pass: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(
                correo = correo,
                contrasena = pass.toSha256()
            )
            val response = api.login(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de autenticación: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Tambien este metodo se debe de usar para registrar un usuario en el viewModel
    suspend fun registrarUsuario(
        correo: String,
        contrasena: String,
        nombre: String,
        apellidos: String,
        telefono: String?
    ): Result<AuthResponse> {
        return try {
            val request = RegistroRequest(correo = correo, contrasena = contrasena.toSha256(), nombre = nombre, apellidos = apellidos, telefono = telefono)
            val response = api.registrarUsuario(request)
            if(response.isSuccessful && response.body() != null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error de autenticación: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
            }
        }

    }