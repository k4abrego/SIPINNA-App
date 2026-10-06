package com.example.sippina_aplicacion.model.Autenticacion
import android.util.Base64
import java.security.MessageDigest

// Se crea el repositorio de autenticacion en donde se definen los metodos de autenticacion
class AuthRepository(
    private val api: AutenticacionAPI = ServicioRemoto.autenticacionAPI
) {
    private fun String.toMacSha256(): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(this.toByteArray(Charsets.UTF_8))
    }

    private fun resolverReto(challengeStr: String, passwordSha256: ByteArray): String {
        val challengeBytes = challengeStr.toByteArray(Charsets.UTF_8)
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(challengeBytes)
        val respuestaBytes = digest.digest(passwordSha256)
        return Base64.encodeToString(respuestaBytes, Base64.NO_WRAP)
    }

    suspend fun iniciarSesion(correo: String, pass: String): Result<String> {
        return try {
            val respuestaReto = api.solicitarReto(correo)
            if (!respuestaReto.isSuccessful || respuestaReto.body() == null) {
                return Result.failure(Exception("Usuario no encontrado o correo inválido"))
            }
            val retoData = respuestaReto.body()!!
            val passHash = pass.toMacSha256()

            val respuestaBase64 = resolverReto(retoData.challenge, passHash)

            val verifyInput = VerifyChallengeInput(
                userId = retoData.userId,
                response = respuestaBase64
            )

            val respuestaLogin = api.verificarReto(verifyInput)
            if (respuestaLogin.isSuccessful && respuestaLogin.body() != null) {
                // Guarda el token generado por el backend Go
                val token = respuestaLogin.body()!!.token
                SessionManager.guardarSesion(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Credenciales inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Registro de usuario en Go
    suspend fun registrarUsuario(
        correo: String,
        contrasena:String,
        nombre: String,
        apellidos: String,
        telefono: String?,
    ): Result<RegistroResponse> {
        return try {
            val request = RegistroInput(
                nombre = nombre,
                apellido = apellidos,
                correo = correo,
                contrasena = contrasena,
                telefono = telefono
            )
            val response = api.registrar(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al registrar: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun obtenerPerfil(token : String):Result<UsuarioPerfilModel>{
        return try {
            Result.failure(Exception("Error al obtener el perfil"))
        }catch(e:Exception){
            Result.failure(e)

        }
    }

    suspend fun actualizarPerfil(token:String , nombre:String, apellidos:String, telefono:String?):Result<UsuarioPerfilModel>{
        return try{
            Result.failure(Exception("Error no se implemento el API"))

        }catch (e:Exception)
        {Result.failure(e)
        }
    }


    suspend fun recuperarContrasena(correo:String):Result<RespuestaBase>{
        return try {
            Result.failure(Exception("Método no implementado en API"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun eliminarCuenta(token:String):Result<Unit>{
        return try{
            Result.success(Unit)
        }catch(e:Exception) {
            Result.failure(e)

        }

    }

    suspend fun cerrarSesion(token:String):Result<Unit>{
        SessionManager.cerrarSesion()
        return Result.success(Unit)
        }
}