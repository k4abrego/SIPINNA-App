package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName

/**
 * Respuesta devuelta por el servidor durante el primer paso del inicio de sesión (solicitud de desafío).
 *
 * Contiene el identificador del usuario y una cadena aleatoria (reto) codificada en Base64
 * que debe ser procesada localmente mediante HMAC-SHA256 para verificar la identidad.
 *
 * @property userId Identificador único asignado al usuario en el sistema backend.
 * @property challenge Cadena del desafío en Base64 generada por el servidor para su posterior firma.
 * @property mensaje Mensaje informativo opcional proveniente de la API.
 */
data class ChallengeResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("challenge")
    val challenge: String,

    @SerializedName("mensaje")
    val mensaje: String? = null
)

/**
 * Objeto de transferencia de datos (DTO) enviado al servidor durante el segundo paso del inicio de sesión.
 *
 * Transmite la solución del desafío criptográfico previamente calculado utilizando el hash de la contraseña.
 *
 * @property userId Identificador único del usuario obtenido en [ChallengeResponse].
 * @property response Cadena en Base64 que contiene la firma HMAC-SHA256 resultante.
 */
data class VerifyChallengeInput(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("response")
    val response: String
)

/**
 * Respuesta que contiene el token de acceso obtenido tras la autenticación.
 *
 * @property token Cadena que representa el token de sesión (JWT) utilizado en la cabecera `Authorization`.
 */
data class TokenResponse(
    @SerializedName("token")
    val token: String
)

/**
 * Modelo de petición enviado a la API para registrar una nueva cuenta de usuario.
 *
 * Encapsula la información personal y las credenciales iniciales requeridas por la plataforma.
 *
 * @property nombre Nombre(s) del nuevo usuario.
 * @property apellido Apellidos paterno y materno del nuevo usuario.
 * @property correo Dirección de correo electrónico con la que se creará la cuenta.
 * @property contrasena Contraseña seleccionada para el acceso a la cuenta.
 * @property telefono Número de teléfono de contacto opcional.
 */
data class RegistroInput(
    @SerializedName("name")
    val nombre: String,

    @SerializedName("surname")
    val apellido: String,

    @SerializedName("email")
    val correo: String,

    @SerializedName("password")
    val contrasena: String,

    @SerializedName("phone_number")
    val telefono: String? = null
)

/**
 * Respuesta obtenida del servidor tras procesar satisfactoriamente una solicitud de registro.
 *
 * @property userId Identificador único asignado al nuevo usuario recién creado.
 * @property mensaje Mensaje informativo o de estado devuelto por el servidor.
 */
data class RegistroResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("mensaje")
    val mensaje: String? = null
)

/**
 * Modelo de petición para solicitar la actualización de la contraseña del usuario.
 *
 * @property nuevaContrasena Cadena de texto con la nueva clave de acceso que sustituirá a la anterior.
 */
data class CambiarContrasenaInput(
    @SerializedName("nueva_contraseña")
    val nuevaContrasena: String
)

/**
 * Modelo de datos local para la capa de presentación que representa el formulario de inicio de sesión.
 *
 * Utilizado por el ViewModel y las vistas Compose para capturar los campos de texto antes del envío.
 *
 * @property correo Correo electrónico introducido por el usuario en la interfaz.
 * @property contrasena Contraseña introducida por el usuario en la interfaz.
 */
data class InicioSesionModel(
    val correo: String,
    val contrasena: String
)

/**
 * Modelo de datos local utilizado en la capa UI para recopilar la información del formulario de registro.
 *
 * @property correo Dirección de correo electrónico especificada por el usuario.
 * @property contrasena Contraseña seleccionada por el usuario.
 * @property nombre Nombre(s) especificados por el usuario.
 * @property apellidos Apellidos especificados por el usuario.
 * @property telefono Número de teléfono de contacto opcional.
 */
data class RegistroModel(
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val apellidos: String,
    val telefono: String? = null
)

/**
 * Petición enviada al servidor para modificar los datos del perfil del usuario autenticado.
 *
 * @property nombre Nombre(s) actualizados que se guardarán en el perfil.
 * @property apellidos Apellidos actualizados que se guardarán en el perfil.
 * @property telefono Número de teléfono de contacto actualizado opcional.
 */
data class ActualizarPerfilPeticion(
    @SerializedName("name")
    val nombre: String,

    @SerializedName("surname")
    val apellidos: String,

    @SerializedName("phone_number")
    val telefono: String? = null
)

/**
 * Petición enviada a la API para solicitar la recuperación de contraseña olvidada.
 *
 * @property correo Dirección de correo electrónico de la cuenta que se desea recuperar.
 */
data class RecuperarContrasenaPeticion(
    @SerializedName("email")
    val correo: String
)

/**
 * Respuesta completa de autenticación enviada por el backend en peticiones unificadas.
 *
 * @property token Token de sesión JWT devuelto por el servidor.
 * @property usuario Objeto [UsuarioPerfilModel] con la información detallada del perfil de usuario.
 */
data class AuthResponse(
    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val usuario: UsuarioPerfilModel
)

/**
 * Modelo que representa el perfil completo del usuario devuelto por la API REST.
 *
 * @property idUsuario Identificador único del usuario en el sistema.
 * @property correo Dirección de correo electrónico asociada a la cuenta.
 * @property nombre Nombre(s) del usuario.
 * @property apellidos Apellidos del usuario.
 * @property telefono Número telefónico de contacto registrado, o `null` si no fue proporcionado.
 */
data class UsuarioPerfilModel(
    @SerializedName("user_id")
    val idUsuario: Int,

    @SerializedName("email")
    val correo: String,

    @SerializedName("name")
    val nombre: String,

    @SerializedName("surname")
    val apellidos: String,

    @SerializedName("phone_number")
    val telefono: String? = null
)
