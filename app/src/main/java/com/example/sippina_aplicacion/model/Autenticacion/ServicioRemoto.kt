package com.example.sippina_aplicacion.model.Autenticacion

import android.content.Context
import com.example.sippina_aplicacion.model.Reporte.ReportesAPI
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServicioRemoto {
    private const val BASE_URL = "http://localhost:8080/api/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val autenticacionAPI: AutenticacionAPI by lazy {
        retrofit.create(AutenticacionAPI::class.java)
    }

    val reportesAPI: ReportesAPI by lazy{
        retrofit.create(ReportesAPI::class.java)
    }
}