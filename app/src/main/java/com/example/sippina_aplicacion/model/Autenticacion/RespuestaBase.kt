package com.example.sippina_aplicacion.model.Autenticacion

data class RespuestaBase(
    val status:Int? = null,
    val mensaje:String? = null,
    val token: String? = null,
    val idUsuario: String? = null

)
