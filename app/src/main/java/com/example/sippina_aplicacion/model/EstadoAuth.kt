package com.example.sippina_aplicacion.model

/**
 * Estado inmutable que representa la información de la sesión y la interfaz visual del módulo de autenticación.
 *
 * Utilizado como estado reactivo en los ViewModels de autenticación para reflejar el perfil del usuario activo,
 * así como para controlar los indicadores de carga, errores de red y confirmaciones de éxito.
 *
 * @property token Token de acceso JWT de la sesión activa, o una cadena vacía si el usuario no está autenticado.
 * @property usuarioId Identificador único del usuario retornado por el backend; `null` si no hay sesión iniciada.
 * @property nombre Nombre(s) del usuario autenticado.
 * @property apellidos Apellidos del usuario autenticado.
 * @property correo Dirección de correo electrónico asociada a la cuenta del usuario.
 * @property telefono Número telefónico de contacto del usuario; `null` si no fue registrado.
 * @property autenticado Flag que confirma si el usuario posee una sesión válida y activa en la aplicación.
 * @property cargando Flag de progreso que indica si hay una petición de autenticación en ejecución asíncrona.
 * @property error Mensaje o descripción del error ocurrido durante el inicio de sesión o registro; `null` si no hay fallos.
 * @property mensajeExito Mensaje informativo de confirmación emitido tras completar una acción con éxito; `null` en caso contrario.
 */
data class EstadoAuth(
    val token: String = "",
    val usuarioId: String? = null,
    val nombre: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val telefono: String? = null,
    val autenticado: Boolean = false,
    val cargando: Boolean = false,
    val error: String? = null,
    val mensajeExito: String? = null
)
