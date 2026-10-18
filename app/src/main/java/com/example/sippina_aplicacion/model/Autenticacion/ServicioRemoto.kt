package com.example.sippina_aplicacion.model.Autenticacion

import android.content.Context
import com.example.sippina_aplicacion.model.Reporte.ReportesAPI
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Objeto Singleton que centraliza la creación y configuración de los clientes HTTP y servicios API REST de la aplicación.
 *
 * Administra las instancias de [Retrofit] configuradas con múltiples URLs base para comunicarse con los
 * microservicios independientes de autenticación y de reportes.
 * Configura convertidores en cadena ([ScalarsConverterFactory] y [GsonConverterFactory]) para procesar
 * tanto respuestas en texto plano (como tokens de sesión) como objetos formateados en JSON.
 */
object ServicioRemoto {
    private const val BASE_URL_AUTH= "http://localhost:8080/"
    private const val BASE_URL_REPORTES= "http://localhost:8085/"

    private val httpClient = OkHttpClient.Builder().build()

    // Esta es para la autenticacion

    private val retrofitAuth: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_AUTH)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitReportes: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_REPORTES)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Cliente de red inicializado de forma diferida (lazy) para consumir los endpoints de [AutenticacionAPI].
     */
    val autenticacionAPI: AutenticacionAPI by lazy {
        retrofitAuth.create(AutenticacionAPI::class.java)
    }

    /**
     * Cliente de red inicializado de forma diferida (lazy) para consumir los endpoints de [ReportesAPI].
     */
    val reportesAPI: ReportesAPI by lazy{
        retrofitReportes.create(ReportesAPI::class.java)
    }
}
