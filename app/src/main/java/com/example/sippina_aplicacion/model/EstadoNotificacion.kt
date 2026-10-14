package com.example.sippina_aplicacion.model

/**
 * Modelo de datos que representa una notificación individual del sistema.
 *
 * Encapsula la información de avisos o actualizaciones sobre el estado de seguimiento
 * de los reportes generados por el ciudadano.
 *
 * @property id Identificador único de la notificación en el sistema.
 * @property titulo Título o encabezado descriptivo del mensaje de notificación.
 * @property descripcion Detalle o cuerpo del mensaje informativo emitido.
 * @property fecha Fecha y hora en que se emitió o registró la notificación.
 * @property folio Folio del reporte al cual está vinculada esta notificación.
 * @property leida Flag que indica si el usuario ya ha visualizado o marcado como leída la notificación.
 */
data class EstadoNotificacion(
    val id: Int = 0,
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val folio: String = "",
    val leida: Boolean = false
)

/**
 * Estado inmutable que modela la interfaz de usuario para la pantalla de notificaciones.
 *
 * Actúa como estado reactivo en los ViewModels para exponer la lista de notificaciones obtenidas,
 * los indicadores de carga asíncrona y la gestión de errores de red o lectura.
 *
 * @property lista Lista con los elementos de tipo [EstadoNotificacion] disponibles para el usuario.
 * @property cargando Flag de progreso que indica si se está realizando la carga de notificaciones.
 * @property error Mensaje descriptivo en caso de que ocurra un error al consultar las notificaciones; `null` si no hay fallos.
 */
data class EstadoNotificaciones(
    val lista: List<EstadoNotificacion> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)
