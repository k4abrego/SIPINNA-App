package com.example.sippina_aplicacion.viewmodel

import android.net.Uri
import java.net.URI

data class EvidenciaReporte(
    val uri: Uri,
    val tipo: String,
    val nombre: String = ""
)

data class DatosMenor(
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = ""
)

data class EstadoReporte(
    val pasoActual:Int = 0,

    // Paso de la ubicación
    val opcionUbicacion :String = "",
//    val latitud: Double = 19.432608, // ejemplo para probar
//    val longitud: Double = -99.133209,
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = "",
    val ubicacionConfirmada: Boolean = false,

    // Detalles del reporte
    val horarioAprox:String = "12:00",
    val cantidadMenores: Int = 1,
    val adultoInvolucrados : String = "",
    val descripcionHechos:String = "",

    // descripción de cada menor involucrado
    val menores: List<DatosMenor> = listOf(DatosMenor()),

    // índice del menor que se está describiendo
    val indiceMenorActual: Int = 0,

    // propiedades conservadas por compatibilidad con la View actual
    val rangoEdad: String = "",
    val genero: String = "",
    val descripcionFisica: String = "",

    //Evidencia
    val tipoEvidenciaSeleccionada:String = "",
    val evidencias: List<EvidenciaReporte> = emptyList(),
    val evidenciaUri : Uri? = null,


    // Privacidad
    val esAnonimo: Boolean = true,
    val recibirActualizaciones:Boolean = false,
    val opcionPrivacidad: String = "",
    val correoSeguimiento: String = "",

    val folio: String = "",
    val codigoPrivado: String = "",

    //Estado de envío
    val enviando: Boolean = false,
    val reporteExitoso: Boolean = false,
    val errorEnvio: String? = null
)

