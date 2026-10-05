package com.example.sippina_aplicacion.model.Reporte

data class PeticionCrearReporte(
    val tipoReporte: String,
    val descripcion: String,
    val opcionPrivacidad: String,
    val telefono: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
)

data class RespuestaReporte(
    val exito: Boolean = true,
    val mensaje: String? = null,
    val folio: String,
    val codigoPrivado: String? = null,
    val estatus: String = "",
    val fechaEnvio: String? = null

)

data class CategoriaModel(
    val id: String,
    val nombre: String
)

data class HistorialEstadoModel(
    val estado: String,
    val fecha: String
)
