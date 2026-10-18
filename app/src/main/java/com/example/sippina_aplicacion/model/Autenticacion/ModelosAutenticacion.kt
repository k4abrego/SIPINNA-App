package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName


data class ChallengeResponse(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("challenge") val challenge: String,
    @SerializedName("message") val mensaje: String? = null
)

data class VerifyChallengeInput(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("response") val response: String
)

data class TokenResponse(
    @SerializedName("token") val token: String,
    @SerializedName("role") val role: String? = null
)

data class RegistroInput(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("contraseña") val contrasena: String,
    @SerializedName("numero_de_telefono") val numeroDeTelefono: String? = null
)
data class RegistroResponse(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("mensaje") val mensaje: String? = null
)

data class CambiarContrasenaInput(
    @SerializedName("new_password") val newPassword: String
)

data class EditarUsuarioInput(
    @SerializedName("nombre") val nombre: String? = null,
    @SerializedName("apellido") val apellido: String? = null,
    @SerializedName("numero_de_telefono") val telefono: String? = null
)

data class CambiarCorreoInput(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("nuevo_correo") val nuevoCorreo: String
)

data class UsuarioResponse(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("nombre") val nombre: String? = null,
    @SerializedName("apellido") val apellido: String? = null,
    @SerializedName("numero_de_telefono") val telefono: String? = null
)

// ============================================================================
// MODELOS DE REPORTES (comms-api :8085 / handlers.go)
// ============================================================================

data class ReporteRequest(
    @SerializedName("id_usuario") val idUsuario: Long? = null,
    @SerializedName("correo_contacto") val correoContacto: String? = null,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("horario_aproximado") val horarioAproximado: String? = null,
    @SerializedName("es_anonimo") val esAnonimo: Boolean,
    @SerializedName("desea_seguimiento") val deseaSeguimiento: Boolean,
    @SerializedName("hay_adulto_involucrado") val hayAdultoInvolucrado: Boolean,
    @SerializedName("peligro_inmediato") val peligroInmediato: String,
    @SerializedName("ubicacion") val ubicacion: UbicacionInput,
    @SerializedName("menores") val menores: List<MenorInput>,
    @SerializedName("etiquetas") val etiquetas: List<EtiquetaInput>
)
data class UbicacionInput(
    @SerializedName("latitud") val latitud: Double,
    @SerializedName("longitud") val longitud: Double,
    @SerializedName("direccion_texto") val direccionTexto: String = "",
    @SerializedName("id_territorio") val idTerritorio: Int = 1
)

data class MenorInput(
    @SerializedName("contador") val contador: Int = 1,
    @SerializedName("genero") val genero: String = "No identificado",
    @SerializedName("edad_aproximada") val edadAproximada: Int? = null,
    @SerializedName("descripcion_fisica") val descripcionFisica: String = ""
)

data class EtiquetaInput(
    @SerializedName("nombre_etiqueta") val nombreEtiqueta: String,
    @SerializedName("tipo") val tipo: String
)

data class ReporteCreacionResponse(
    @SerializedName("folio") val folio: String? = null,
    @SerializedName("codigo_privado") val codigoPrivado: String? = null,
    @SerializedName("mensaje") val mensaje: String? = null
)

data class ReporteResponse(
    @SerializedName("folio") val folio: String? = null,
    @SerializedName("estatus") val estatus: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("fecha_envio") val fechaEnvio: String? = null,
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("mensaje") val mensaje: String? = null
)

data class CategoriaModel(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String
)

data class ReporteDetalleResponse(
    @SerializedName("folio") val folio: String,
    @SerializedName("estatus") val estatus: String,
    @SerializedName("descripcion") val descripcion: String
)

data class HistorialEstadoModel(
    @SerializedName("fecha") val fecha: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("comentario") val comentario: String
)

data class SubirEvidenciasResponse(
    @SerializedName("mensaje") val mensaje: String? = null,
    @SerializedName("reporte_id") val reporteId: String? = null
)