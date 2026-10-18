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
    val peligroInmediato: String = ""
)

/**
 * Representa una evidencia multimedia local elegida por el usuario.
 */
data class EvidenciaReporte(
    val uri: Uri,
    val tipo: String,
    val nombre: String = ""
)

/**
 * Estado completo de la interfaz de usuario para el asistente de creación de reportes (UI State).
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

    // Propiedades individuales para la edición del menor activo
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = "",
    val peligroInmediato: String = "",

    // Evidencias
    val tipoEvidenciaSeleccionada: String = "",
    val evidencias: List<EvidenciaReporte> = emptyList(),
    val evidenciaUri: Uri? = null,

    // Privacidad
    val opcionPrivacidad: String = "anonimo",
    val correoSeguimiento: String = "",

    // Datos devueltos por el backend tras un envío exitoso
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
 * Modelo de presentación utilizado en las vistas de listado, mapa y detalle (UI).
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