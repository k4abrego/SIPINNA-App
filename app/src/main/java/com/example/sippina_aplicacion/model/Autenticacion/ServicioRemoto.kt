package com.example.sippina_aplicacion.model.Autenticacion

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServicioRemoto {
    private const val BASE_URL = "1234567890"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val autentificacionAPI: AutentificacionAPI by lazy{
        retrofit.create(AutentificacionAPI::class.java)
    }
}