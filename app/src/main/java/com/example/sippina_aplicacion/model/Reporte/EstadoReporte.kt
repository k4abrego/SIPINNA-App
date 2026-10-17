package com.example.sippina_aplicacion.model

import android.net.Uri

/**
 * Representa un archivo de evidencia adjunto a un reporte de vulneración de derechos.
 *
 * Encapsula la información necesaria para gestionar la carga y visualización de recursos
 * multimedia (imágenes, videos, audios o documentos) seleccionados por el usuario.
 *
 * @property uri Ubicación [Uri] del archivo multimedia dentro del almacenamiento local del dispositivo.
 * @property tipo Categoría o tipo MIME del archivo adjunto (ej. "imagen", "video", "audio").
 * @property nombre Nombre descriptivo u original del archivo. Por defecto es una cadena vacía.
 */
data class EvidenciaReporte(
    val uri: Uri,
    val tipo: String,
    val nombre: String = ""
)

/**
 * Modelo de datos que representa la información de un reporte enviado previamente para su consulta e historial.
 *
 * Utilizado para mostrar el resumen del estado de atención de un reporte dentro de las vistas de seguimiento.
 *
 * @property folio Clave o identificador único asignado al reporte para su rastreo.
 * @property estatus Estado actual del trámite dentro del sistema (ej. "Pendiente", "En revisión", "Concluido").
 * @property fechaEnvio Fecha y hora en que fue registrado el reporte en el servidor.
 * @property descripcion Resumen o síntesis de la situación reportada.
 * @property direccion Ubicación física asignada al incidente.
 */
data class ReporteModel(
    val folio: String = "",
    val estatus: String = "",
    val fechaEnvio: String = "",
    val descripcion: String = "",
    val direccion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0
)

/**
 * Representa los datos del usuario autenticado en la plataforma SIPINNA.
 *
 * Contiene la información de identificación utilizada para personalizar la interfaz y vincular los
 * reportes no anónimos al perfil del ciudadano.
 *
 * @property id Identificador único del usuario registrado en la base de datos o servicio de autenticación.
 * @property nombre Nombre(s) del usuario.
 * @property apellidoPaterno Primer apellido del usuario.
 * @property apellidoMaterno Segundo apellido del usuario.
 * @property correo Dirección de correo electrónico asociada a la cuenta del usuario.
 */
data class UsuarioModel(
    val id: String = "",
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val correo: String = ""
) {
    /**
     * Nombre completo del usuario estructurado en dos líneas para su presentación en componentes visuales.
     *
     * @return Cadena de texto con el nombre en la primera línea y los apellidos en la segunda,
     * recortando espacios en blanco innecesarios.
     */
    val nombreCompletoFormateado: String
        get() = "$nombre\n$apellidoPaterno $apellidoMaterno".trim()
}

/**
 * Información descriptiva e individual de un menor de edad involucrado en el reporte.
 *
 * Permite registrar las características físicas e identificación básica de cada menor afectado
 * para facilitar el abordaje por parte de las autoridades competentes.
 *
 * @property rangoEdad Intervalo estimado de edad del menor (ej. "0 a 5 años", "6 a 11 años").
 * @property genero Género o sexo del menor registrado.
 * @property descripcionFisica Rasgos particulares, vestimenta o señas de identificación del menor.
 */
data class DatosMenor(
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = "",
    val peligroInmediato: String = ""
)

/**
 * Estado inmutable que gestiona el flujo del formulario de creación y envío de un reporte.
 *
 * Actúa como la fuente única de verdad dentro de la arquitectura de la pantalla de reporte.
 * Mantiene la información capturada a través de las distintas etapas del asistente (ubicación,
 * descripción de los hechos, registro de menores involucrados, evidencias y configuración de privacidad),
 * además de controlar el estado de la petición asíncrona hacia el servidor.
 *
 * @property pasoActual Índice del paso o pantalla activa dentro del flujo secuencial del reporte.
 * @property opcionUbicacion Modalidad elegida para indicar el lugar de los hechos (ej. "actual", "manual").
 * @property latitud Coordenada de latitud del incidente.
 * @property longitud Coordenada de longitud del incidente.
 * @property direccion Texto descriptivo de la dirección o referencia del lugar.
 * @property ubicacionConfirmada Flag que indica si la ubicación especificada ha sido validada por el usuario.
 * @property horarioAprox Hora o periodo aproximado en el que ocurrieron los hechos.
 * @property cantidadMenores Número total de menores involucrados en la situación.
 * @property adultoInvolucrados Información o descripción de los adultos presentes o presuntos responsables.
 * @property descripcionHechos Narrativa detallada de la vulneración de derechos presenciada.
 * @property menores Lista de instancias [DatosMenor] con la descripción de cada menor involucrado.
 * @property indiceMenorActual Índice del menor que se está capturando o editando en la interfaz actual.
 * @property rangoEdad Propiedad conservada por compatibilidad hacia atrás con vistas heredadas.
 * @property genero Propiedad conservada por compatibilidad hacia atrás con vistas heredadas.
 * @property descripcionFisica Propiedad conservada por compatibilidad hacia atrás con vistas heredadas.
 * @property tipoEvidenciaSeleccionada Tipo de archivo multimedia que el usuario intenta adjuntar actualmente.
 * @property evidencias Lista de archivos [EvidenciaReporte] adjuntados al reporte.
 * @property evidenciaUri [Uri] temporal del archivo multimedia seleccionado previo a su procesamiento.
 * @property opcionPrivacidad Modalidad de privacidad elegida para el envío (ej. "anonimo" o "registro").
 * @property correoSeguimiento Correo electrónico opcional especificado para recibir notificaciones del caso.
 * @property folio Folio único generado y retornado por el sistema tras un registro exitoso.
 * @property codigoPrivado Código privado entregado al usuario para el seguimiento de un reporte anónimo.
 * @property enviando Flag de progreso que indica si el reporte se encuentra en proceso de transmisión asíncrona.
 * @property reporteExitoso Flag que confirma la recepción y almacenamiento exitoso del reporte en el servidor.
 * @property errorEnvio Mensaje detallado del error ocurrido si el proceso de envío falla; `null` si no hay error.
 */
data class EstadoReporte(
    val pasoActual: Int = 0,
    val opcionUbicacion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = "",
    val ubicacionConfirmada: Boolean = false,

    val horarioAprox: String = "",
    val cantidadMenores: Int = 1,
    val adultoInvolucrados: String = "",
    val descripcionHechos: String = "",

    val menores: List<DatosMenor> = listOf(DatosMenor()),
    val indiceMenorActual: Int = 0,

    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = "",
    val peligroInmediato: String = "", // <-- Agregar este campo

    val tipoEvidenciaSeleccionada: String = "",
    val evidencias: List<EvidenciaReporte> = emptyList(),
    val evidenciaUri: Uri? = null,

    val opcionPrivacidad: String = "anonimo",
    val correoSeguimiento: String = "",

    val folio: String = "",
    val codigoPrivado: String = "",

    val enviando: Boolean = false,
    val reporteExitoso: Boolean = false,
    val errorEnvio: String? = null
) {
    /**
     * Obtiene los datos del menor en edición actual según el [indiceMenorActual].
     *
     * @return La instancia de [DatosMenor] en la posición [indiceMenorActual] de la lista [menores],
     * o una nueva instancia vacía de [DatosMenor] si el índice se encuentra fuera de los límites.
     */
    val menorActual: DatosMenor
        get() = menores.getOrNull(indiceMenorActual) ?: DatosMenor()

    /**
     * Indica si la solicitud de reporte está configurada para procesarse de manera anónima.
     *
     * @return `true` si la [opcionPrivacidad] es igual a `"anonimo"`, o `false` en caso contrario.
     */
    val esAnonimo: Boolean
        get() = opcionPrivacidad == "anonimo"
}
