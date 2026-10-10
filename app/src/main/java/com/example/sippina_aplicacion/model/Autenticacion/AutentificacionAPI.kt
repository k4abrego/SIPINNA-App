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

/**
 * Interfaz de Retrofit que define los endpoints de la API REST para el módulo de autenticación y gestión de usuarios.
 *
 * Implementa un esquema de inicio de sesión basado en la resolución de retos en dos pasos (Challenge-Response)
 * para autenticar usuarios de manera segura, así como endpoints para el registro de nuevos usuarios y
 * la actualización de credenciales.
 */
interface AutenticacionAPI {

    /**
     * Paso 1 del flujo de autenticación: solicita un reto criptográfico ([ChallengeResponse]) asociado al correo del usuario.
     *
     * Inicia el proceso de verificación de identidad enviando el correo del usuario como parámetro de ruta en el endpoint `GET auth/{correo}`.
     * El servidor responde con el ID de usuario y una cadena que representa el reto a resolver.
     *
     * @param correo Dirección de correo electrónico del usuario registrado que intenta iniciar sesión.
     * @return [Response] que envuelve la respuesta [ChallengeResponse] con el `userId` y la cadena del desafío enviado por el servidor.
     */
    @GET("auth/{correo}")
    suspend fun solicitarReto(
        @Path("correo") correo: String
    ): Response<ChallengeResponse>

    /**
     * Paso 2 del flujo de autenticación: envía la solución del reto previamente recibido para validar el inicio de sesión.
     *
     * Transmite el ID de usuario y el resultado de la verificación ([VerifyChallengeInput]) mediante una petición `POST auth/`.
     * Si la solución es válida, el servidor responde confirmando la autenticación y devolviendo el token de sesión o respuesta HTTP.
     *
     * @param request Objeto [VerifyChallengeInput] que contiene el identificador del usuario y la solución del reto.
     * @return [Response] con un cuerpo de tipo [ResponseBody] que contiene la respuesta plana del servidor o el token generado.
     */
    @POST("auth/")
    suspend fun verificarReto(
        @Body request: VerifyChallengeInput
    ): Response<ResponseBody>

    /**
     * Registra un nuevo usuario en la plataforma SIPINNA.
     *
     * Realiza una petición `POST register` enviando los datos personales y credenciales iniciales del usuario
     * encapsulados en el objeto [RegistroInput].
     *
     * @param request Objeto [RegistroInput] con la información del nuevo usuario (nombre, apellido, correo, contraseña y teléfono opcional).
     * @return [Response] que contiene un [RegistroResponse] con el identificador único del usuario recién creado y mensajes del servidor.
     */
    @POST("register")
    suspend fun registrar(
        @Body request: RegistroInput
    ): Response<RegistroResponse>

    /**
     * Actualiza la contraseña de acceso de un usuario existente.
     *
     * Requiere autenticación mediante un token de portador (`Bearer token`) enviado en la cabecera `Authorization`,
     * así como especificar el correo del usuario en el parámetro de ruta de la petición `PUT password/{correo}`.
     *
     * @param correo Dirección de correo electrónico del usuario al que se le cambiará la contraseña.
     * @param token Token de autorización (ej. `"Bearer <token>"`) que valida los permisos de la sesión actual.
     * @param request Objeto [CambiarContrasenaInput] con la nueva contraseña que se establecerá.
     * @return [Response] que incluye un [RespuestaBase] confirmando el éxito o fallo de la actualización.
     */
    @PUT("password/{correo}")
    suspend fun cambiarContrasena(
        @Path("correo") correo: String,
        @Header("Authorization") token: String,
        @Body request: CambiarContrasenaInput
    ): Response<RespuestaBase>
}
