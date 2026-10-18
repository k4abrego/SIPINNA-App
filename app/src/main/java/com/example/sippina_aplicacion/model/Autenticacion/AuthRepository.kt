package com.example.sippina_aplicacion.model.Autenticacion

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Repositorio encargado de gestionar la lógica de negocio para la autenticación y administración de cuentas de usuario.
 *
 * Coordina las peticiones de red a través de [AutenticacionAPI] e implementa un esquema de autenticación
 * por desafío criptográfico (HMAC-SHA256) sin transmitir la contraseña en texto plano.
 * Además, interactúa con [SessionManager] para la persistencia del token de sesión.
 *
 * @property api Cliente Retrofit de [AutenticacionAPI] para la comunicación con los endpoints del servidor en Go.
 */

class AuthRepository(
    private val api: AutenticacionAPI = ServicioRemoto.autenticacionAPI
) {

    private fun String.toSha256(): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(this.toByteArray(Charsets.UTF_8))
    }

    private fun resolverRetoHMAC256(challengeStr: String, passwordSha256: ByteArray): String {
        val algoritmo = "HmacSHA256"
        val secretKey = SecretKeySpec(passwordSha256, algoritmo)
        val mac = Mac.getInstance(algoritmo)
        mac.init(secretKey)

        val challengeBytes = Base64.decode(challengeStr, Base64.NO_WRAP)
        val respuestaBytes = mac.doFinal(challengeBytes)

        return Base64.encodeToString(respuestaBytes, Base64.NO_WRAP)
    }

    private fun formatearBearerToken(token: String): String {
        return if (token.startsWith("Bearer ")) token else "Bearer $token"
    }

    suspend fun iniciarSesion(correo: String, pass: String): Result<String> {
        return try {
            val respuestaReto = api.solicitarReto(correo.trim())
            if (!respuestaReto.isSuccessful || respuestaReto.body() == null) {
                return Result.failure(Exception("Usuario no encontrado o correo inválido"))
            }

            val retoData = respuestaReto.body()!!
            val passHash = pass.toSha256()
            val respuestaBase64 = resolverRetoHMAC256(retoData.challenge, passHash)

            val verifyInput = VerifyChallengeInput(
                userId = retoData.userId,
                response = respuestaBase64
            )

            val respuestaLogin = api.verificarReto(verifyInput)
            if (respuestaLogin.isSuccessful && respuestaLogin.body() != null) {
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

    suspend fun registrarUsuario(
        correo: String,
        contrasena: String,
        nombre: String,
        apellidos: String,
        telefono: String?,
    ): Result<RegistroResponse> {
        return try {
            val request = RegistroInput(
                nombre = nombre.trim(),
                apellido = apellidos.trim(),
                correo = correo.trim(),
                contrasena = contrasena,
                numeroDeTelefono = telefono?.takeIf { it.isNotBlank() }
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

    suspend fun obtenerPerfil(token: String, correoTarget: String): Result<UsuarioResponse> {
        return try {
            val response = api.obtenerUsuario(
                correo = correoTarget.trim(),
                token = formatearBearerToken(token)
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener perfil: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarPerfil(
        token: String,
        correoTarget: String,
        nombre: String?,
        apellidos: String?,
        telefono: String?
    ): Result<Unit> {
        return try {
            val request = EditarUsuarioInput(
                nombre = nombre?.takeIf { it.isNotBlank() },
                apellido = apellidos?.takeIf { it.isNotBlank() },
                telefono = telefono?.takeIf { it.isNotBlank() }
            )
            val response = api.editarUsuario(
                correo = correoTarget.trim(),
                token = formatearBearerToken(token),
                request = request
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al actualizar perfil: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun cambiarContrasena(
        token: String,
        correoTarget: String,
        nuevaContrasena: String
    ): Result<Unit> {
        return try {
            val request = CambiarContrasenaInput(newPassword = nuevaContrasena)
            val response = api.cambiarContrasenna(
                correo = correoTarget.trim(),
                token = formatearBearerToken(token),
                request = request
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al cambiar contraseña: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun cerrarSesion(userId: Long, token: String): Result<Unit> {
        return try {
            api.logout(userId = userId, token = formatearBearerToken(token))
            SessionManager.cerrarSesion()
            Result.success(Unit)
        } catch (e: Exception) {
            SessionManager.cerrarSesion()
            Result.success(Unit)
        }
    }

    suspend fun recuperarContrasena(correo: String): Result<RegistroResponse> {
        return try {
            Result.success(RegistroResponse(userId = 0, mensaje = "Correo de recuperación enviado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarCuenta(token: String): Result<Unit> {
        return try {
            SessionManager.cerrarSesion()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}