package com.example.sippina_aplicacion.model.Reporte

import com.google.gson.annotations.SerializedName

// 1. Objeto que se envia al servidor (Peticion POST )
data class ReporteResquest(
    @SerializedName("tipo_reporte") val tipoReporte: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("opcion_privacidad :String") val opcionPrivacidad:String,
    @SerializedName("telefono") val telefono: String? = null

)

// Respuesta que devuelve el servidor
data class ReporteResponse(
    @SerializedName("exito") val exito: Boolean,
    @SerializedName("mensaje") val mensaje: String,
    @SerializedName("folio") val folio: String,
    @SerializedName("codigo_privadidad") val codigoPrivado: String
)

// Estado único de la Pantalla osea es el IU State para stateFlow

data class ReporteState(
    val tipoReporte: String = "",
    val descripcion: String = "",
    val opcionPrivacidad: String = "anonimo",
    val telefono: String = "",
    val folio: String = "",
    val codigoPrivado: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val reporteExitoso: Boolean = false
)

