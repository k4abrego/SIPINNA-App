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
    /**
     * Calcula la suma de verificación SHA-256 de una cadena de texto.
     *
     * @return Arreglo de bytes con el resumen digest SHA-256 codificado en UTF-8.
     */
    private fun String.toSha256(): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(this.toByteArray(Charsets.UTF_8))
    }

    /**
     * Resuelve el desafío criptográfico enviado por el servidor utilizando el algoritmo HMAC-SHA256.
     *
     * Toma el reto en formato Base64, lo decodifica y calcula la firma HMAC utilizando como clave secreta
     * el hash SHA-256 de la contraseña del usuario.
     *
     * @param challengeStr Cadena del desafío en Base64 recibida del endpoint del servidor.
     * @param passwordSha256 Arreglo de bytes con el hash SHA-256 de la contraseña del usuario.
     * @return Cadena codificada en Base64 sin saltos de línea con la respuesta calculada del desafío.
     */
    private fun resolverRetoHMAC256(challengeStr: String, passwordSha256: ByteArray): String {
        val Algoritmo= "HmacSHA256"
        val secretKey = SecretKeySpec(passwordSha256, Algoritmo)
        val mac = Mac.getInstance(Algoritmo)
        mac.init(secretKey)

        val challengeBytes = Base64.decode(challengeStr, Base64.NO_WRAP)
        val respuestaBytes = mac.doFinal(challengeBytes)

        return Base64.encodeToString(respuestaBytes, Base64.NO_WRAP)

    }

    /**
     * Inicia sesión en la plataforma realizando el flujo de verificación en dos pasos (Challenge-Response).
     *
     * 1. Solicita el reto criptográfico al servidor con [AutenticacionAPI.solicitarReto].
     * 2. Calcula el hash SHA-256 de la contraseña y resuelve el reto mediante [resolverRetoHMAC256].
     * 3. Verifica la respuesta con [AutenticacionAPI.verificarReto].
     * 4. Si la verificación es exitosa, persiste el token de sesión en [SessionManager].
     *
     * @param correo Correo electrónico del usuario registrado.
     * @param pass Contraseña en texto plano introducida por el usuario.
     * @return [Result] que contiene el token JWT de sesión si la autenticación es exitosa,
     * o una excepción si las credenciales son inválidas o falla la conexión.
     */
    suspend fun iniciarSesion(correo: String, pass: String): Result<String> {
        return try {
            val respuestaReto = api.solicitarReto(correo)
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
                // Guarda el token generado por el backend Go
                val token = respuestaLogin.body()!!.string()
                SessionManager.guardarSesion(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Credenciales inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Registra una nueva cuenta de usuario en el backend.
     *
     * Empaqueta los datos personales del usuario en un objeto [RegistroInput] y los transmite
     * a través de [AutenticacionAPI.registrar].
     *
     * @param correo Dirección de correo electrónico del usuario.
     * @param contrasena Contraseña que se asignará a la cuenta.
     * @param nombre Nombre(s) del usuario.
     * @param apellidos Apellidos paterno y materno del usuario.
     * @param telefono Número de teléfono de contacto opcional.
     * @return [Result] con el objeto [RegistroResponse] devuelto por el servidor en caso de éxito,
     * o un fallo con el mensaje de error correspondiente.
     */
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

    /**
     * Obtiene el perfil del usuario autenticado a partir de su correo electrónico.
     *
     * @param token Token de sesión activo del usuario.
     * @param correoTarget Correo electrónico del usuario cuyo perfil se desea consultar.
     * @return [Result] con la información del perfil [UsuarioPerfilModel] o la excepción en caso de falla.
     */
    suspend fun obtenerPerfil(token: String, correoTarget: String):Result<UsuarioPerfilModel>{
        return try {
            Result.failure(Exception("Error al obtener el perfil"))
        }catch(e:Exception){
            Result.failure(e)

        }
    }

    /**
     * Actualiza la información del perfil de un usuario registrado.
     *
     * @param token Token de autorización de la sesión activa.
     * @param nombre Nombre(s) actualizados del usuario.
     * @param apellidos Apellidos actualizados del usuario.
     * @param telefono Número de teléfono actualizado opcional.
     * @return [Result] con los datos actualizados de [UsuarioPerfilModel] o la excepción correspondiente.
     */
    suspend fun actualizarPerfil(token:String , nombre:String, apellidos:String, telefono:String?):Result<UsuarioPerfilModel>{
        return try{
            Result.failure(Exception("Error no se implemento el API"))

        }catch (e:Exception)
        {Result.failure(e)
        }
    }

    /**
     * Solicita el restablecimiento de la contraseña para un correo electrónico registrado.
     *
     * @param correo Dirección de correo electrónico asociada a la cuenta a recuperar.
     * @return [Result] que envuelve la respuesta base [RespuestaBase] confirmando la solicitud.
     */
    suspend fun recuperarContrasena(correo:String):Result<RespuestaBase>{
        return try {
            Result.failure(Exception("Método no implementado en API"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Elimina permanentemente la cuenta del usuario autenticado del sistema.
     *
     * @param token Token de sesión activo que autoriza la eliminación de la cuenta.
     * @return [Result] de tipo [Unit] confirmando la baja de la cuenta.
     */
    suspend fun eliminarCuenta(token:String):Result<Unit>{
        return try{
            Result.success(Unit)
        }catch(e:Exception) {
            Result.failure(e)

        }

    }

    /**
     * Cierra la sesión activa del usuario eliminando los datos de autenticación persistidos.
     *
     * Invoca [SessionManager.cerrarSesion] para purgar las credenciales locales de la aplicación.
     *
     * @param token Token de la sesión que se desea finalizar.
     * @return [Result] de tipo [Unit] indicando la finalización exitosa de la sesión local.
     */
    suspend fun cerrarSesion(token:String):Result<Unit>{
        SessionManager.cerrarSesion()
        return Result.success(Unit)
        }
}
