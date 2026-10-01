//package com.example.sippina_aplicacion.model.Reporte
//
//import com.example.sippina_aplicacion.model.Autenticacion.AutentificacionAPI
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import kotlin.getValue
//
//object RetrofitClient{
//    val AutentificacionAPI: AutentificacionAPI
//    private const val BASE_URL = "http://10.0.2.2:8080" //->se cambia por la URL base del API esto es dummy pongan lo necesario
//    val apiService: ApiService by lazy{
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
//}