package com.example.sippina_aplicacion.model.Reporte

import com.google.gson.annotations.SerializedName

data class ReporteRequest(
    @SerializedName("tipo_reporte")
    val tipoReporte: String,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("opcion_privacidad")
    val opcionPrivacidad: String,
    @SerializedName("telefono")
    val telefono: String? = null,
    @SerializedName("latitud")
    val latitud: Double? = null,
    @SerializedName("longitud")
    val longitud: Double? = null
)

data class ReporteResponse(
    @SerializedName("exito")
    val exito: Boolean = true,
    @SerializedName("mensaje")
    val mensaje: String? = null,
    @SerializedName("folio")
    val folio: String,
    @SerializedName("codigo_privacidad")
    val codigoPrivado: String? = null,
    @SerializedName("estatus")
    val estatus: String = "",
    @SerializedName("fecha_envio")
    val fechaEnvio: String? = null
)

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