package com.example.sippina_aplicacion.model.Reporte

import android.net.Uri
import com.example.sippina_aplicacion.model.EvidenciaReporte
import com.google.gson.annotations.SerializedName


data class DatosMenor(
    @SerializedName("rango_edad")
    val rangoEdad: String = "",

    @SerializedName("genero")
    val genero: String = "",

    @SerializedName("descripcion_fisica")
    val descripcionFisica: String = "",

    @SerializedName("peligro_inmediato")
    val peligroInmediato: String = "" // <-- Parámetro agregado
)

/**
 * Estado UI completo del formulario de reportes.
 */
data class EstadoReporte(
    val pasoActual: Int = 0,

    // Ubicación
    val opcionUbicacion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = "",
    val ubicacionConfirmada: Boolean = false,

    // Detalles del reporte
    val horarioAprox: String = "",
    val cantidadMenores: Int = 1,
    val adultoInvolucrados: String = "",
    val descripcionHechos: String = "",

    // Lista de menores e índice activo
    val menores: List<DatosMenor> = listOf(DatosMenor()),
    val indiceMenorActual: Int = 0,

    // Propiedades individuales para edición actual
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = "",
    val peligroInmediato: String = "", // <-- Parámetro agregado

    // Evidencias
    val tipoEvidenciaSeleccionada: String = "",
    val evidencias: List<EvidenciaReporte> = emptyList(),
    val evidenciaUri: Uri? = null,

    // Privacidad
    val opcionPrivacidad: String = "anonimo",
    val correoSeguimiento: String = "",

    val folio: String = "",
    val codigoPrivado: String = "",

    // Estados de envío
    val enviando: Boolean = false,
    val reporteExitoso: Boolean = false,
    val errorEnvio: String? = null
) {
    val menorActual: DatosMenor
        get() = menores.getOrNull(indiceMenorActual) ?: DatosMenor()

    val esAnonimo: Boolean
        get() = opcionPrivacidad == "anonimo"
}

/**
 * DTO enviado en la petición HTTP POST hacia la API en Go.
 */
data class ReporteRequest(
    @SerializedName("tipo_reporte")
    val tipoReporte: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("opcion_privacidad")
    val opcionPrivacidad: String,

    @SerializedName("telefono")
    val telefono: String? = null,

    @SerializedName("direccion")
    val direccion: String? = null,

    @SerializedName("latitud")
    val latitud: Double? = null,

    @SerializedName("longitud")
    val longitud: Double? = null,

    @SerializedName("peligro_inmediato")
    val peligroInmediato: String? = null, // <-- Parámetro agregado

    @SerializedName("menores")
    val menores: List<DatosMenor> = emptyList()
)

/**
 * DTO de respuesta proveniente de la API en Go.
 */
data class ReporteResponse(
    @SerializedName("exito")
    val exito: Boolean = true,

    @SerializedName("mensaje")
    val mensaje: String? = null,

    @SerializedName("folio")
    val folio: String = "",

    @SerializedName("codigo_privacidad")
    val codigoPrivado: String? = null,

    @SerializedName("estatus")
    val estatus: String = "",

    @SerializedName("fecha_envio")
    val fechaEnvio: String? = null,

    @SerializedName("descripcion")
    val descripcion: String? = null,

    @SerializedName("direccion")
    val direccion: String? = null,

    @SerializedName("latitud")
    val latitud: Double? = null,

    @SerializedName("longitud")
    val longitud: Double? = null,

    @SerializedName("peligro_inmediato")
    val peligroInmediato: String? = null
)

/**
 * Modelo de reporte utilizado en la UI y vistas de historial/detalle.
 */
data class ReporteModel(
    val folio: String = "",
    val estatus: String = "",
    val fechaEnvio: String = "",
    val descripcion: String = "",
    val direccion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val peligroInmediato: String = ""
)

/**
 * Modelo que representa una categoría de reporte.
 */
data class CategoriaModel(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("nombre")
    val nombre: String = "",

    @SerializedName("descripcion")
    val descripcion: String? = null
)

/**
 * Modelo que representa el historial de cambios de estado de un reporte.
 */
data class HistorialEstadoModel(
    @SerializedName("estatus")
    val estatus: String = "",

    @SerializedName("fecha")
    val fecha: String = "",

    @SerializedName("comentario")
    val comentario: String? = null
)