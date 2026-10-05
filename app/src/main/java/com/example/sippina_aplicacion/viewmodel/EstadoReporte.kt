package com.example.sippina_aplicacion.viewmodel

import android.net.Uri
import java.net.URI

// Datos de la evidencia del reporte
data class EvidenciaReporte(
    val uri: Uri,
    val tipo: String,
    val nombre: String = ""
)

data class ReporteModel(
    val folio: String = "",
    val estatus: String = "",
    val fechaEnvio: String = "",
    val descripcion: String = "",
    val direccion: String = ""
)

data class UsuarioModel(
    val id: String = "",
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val correo: String = ""
) {
    val nombreCompletoFormateado: String
        get() = "$nombre\n$apellidoPaterno $apellidoMaterno".trim()
}

data class DatosMenor(
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = ""
)

data class EstadoReporte(
    val pasoActual: Int = 0,
    // Paso de la ubicación
    val opcionUbicacion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = "",
    val ubicacionConfirmada: Boolean = false,

    // Detalles del reporte
    val horarioAprox: String = "12:00",
    val cantidadMenores: Int = 1,
    val adultoInvolucrados: String = "",
    val descripcionHechos: String = "",

    // Descripción de cada menor involucrado
    val menores: List<DatosMenor> = listOf(DatosMenor()),
    val indiceMenorActual: Int = 0,

    // Propiedades conservadas por compatibilidad con la View actual
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = "",

    // Evidencia
    val tipoEvidenciaSeleccionada: String = "",
    val evidencias: List<EvidenciaReporte> = emptyList(),
    val evidenciaUri: Uri? = null,

    // Privacidad
    val opcionPrivacidad: String = "anonimo",
    val correoSeguimiento: String = "",

    val folio: String = "",
    val codigoPrivado: String = "",

    // Estado de envío
    val enviando: Boolean = false,
    val reporteExitoso: Boolean = false,
    val errorEnvio: String? = null
) {
    val menorActual: DatosMenor
        get() = menores.getOrNull(indiceMenorActual) ?: DatosMenor()

    val esAnonimo: Boolean
        get() = opcionPrivacidad == "anonimo"
}
