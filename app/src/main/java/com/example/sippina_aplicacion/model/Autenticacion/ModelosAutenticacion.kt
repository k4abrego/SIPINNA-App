package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.annotations.SerializedName


data class ChallengeResponse(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("challenge") val challenge: String,
    @SerializedName("mensaje") val mensaje: String
)

data class VerifyChallengeInput(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("response") val response: String
)

data class TokenResponse(
    @SerializedName("token") val token: String
)

data class RegistroInput(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("contraseña") val contrasena: String, // ¡Importante la 'ñ'!
    @SerializedName("numero_de_telefono") val numeroDeTelefono: String? = null
)

data class RegistroResponse(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("mensaje") val mensaje: String
)

data class CambiarContrasenaInput(
    @SerializedName("new_password") val newPassword: String // Espera 'new_password'
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
    @SerializedName("user_id") val userId: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("numero_de_telefono") val telefono: String?
)




data class ReporteRequest(
    @SerializedName("opcion_ubicacion") val opcionUbicacion: String,
    @SerializedName("latitud") val latitud: Double,
    @SerializedName("longitud") val longitud: Double,
    @SerializedName("direccion") val direccion: String,
    @SerializedName("horario_aprox") val horarioAprox: String,
    @SerializedName("cantidad_menores") val cantidadMenores: Int,
    @SerializedName("adulto_involucrados") val adultoInvolucrados: String, // Ojo con la 's'
    @SerializedName("descripcion_hechos") val descripcionHechos: String,
    @SerializedName("opcion_privacidad") val opcionPrivacidad: String,
    @SerializedName("correo_seguimiento") val correoSeguimiento: String
)

data class ReporteCreacionResponse(
    @SerializedName("folio") val folio: String,
    @SerializedName("codigo_privado") val codigoPrivado: String,
    @SerializedName("mensaje") val mensaje: String
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
    @SerializedName("mensaje") val mensaje: String,
    @SerializedName("reporte_id") val reporteId: String
)