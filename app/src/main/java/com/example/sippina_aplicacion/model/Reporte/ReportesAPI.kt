package com.example.sippina_aplicacion.model.Reporte

import com.example.sippina_aplicacion.model.Autenticacion.CategoriaModel
import com.example.sippina_aplicacion.model.Autenticacion.ReporteCreacionResponse
import com.example.sippina_aplicacion.model.Autenticacion.ReporteRequest
import com.example.sippina_aplicacion.model.Autenticacion.ReporteResponse
import com.example.sippina_aplicacion.model.Autenticacion.SubirEvidenciasResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz de Retrofit para consumir los endpoints del módulo de reportes en comms-api.
 */
interface ReportesAPI {

    /**
     * Registra un nuevo reporte en la base de datos con estructura JSON anidada.
     * Corresponde a: POST /api/v1/reportes (handlers.CreateReport)
     */
    @POST("api/v1/reportes")
    suspend fun crearReporte(
        @Body request: ReporteRequest,
        @Header("Authorization") token: String? = null
    ): Response<ReporteCreacionResponse>

    /**
     * Sube archivos de evidencia (imágenes o videos) vinculados a un folio generado.
     * Corresponde a: POST /api/v1/reportes/{folio}/evidencias (handlers.UploadEvidence)
     */
    @Multipart
    @POST("api/v1/reportes/{folio}/evidencias")
    suspend fun subirEvidencias(
        @Path("folio") folio: String,
        @Part archivos: List<MultipartBody.Part>
    ): Response<SubirEvidenciasResponse>

    /**
     * Consulta un reporte específico por folio utilizando el token de sesión.
     * Corresponde a: GET /api/v1/reportes/{folio} (handlers.ConsultFolio)
     */
    @GET("api/v1/reportes/{folio}")
    suspend fun obtenerReportePorFolio(
        @Path("folio") folio: String,
        @Header("Authorization") token: String? = null
    ): Response<ReporteResponse>

    /**
     * Obtiene la lista de reportes pertenecientes al usuario autenticado.
     * Corresponde a: GET /api/v1/reportes (handlers.ListUserReports)
     */
    @GET("api/v1/reportes")
    suspend fun obtenerListaReportes(
        @Header("Authorization") token: String? = null,
        @Query("estado") estado: String? = null
    ): Response<List<ReporteResponse>>

    /**
     * Consulta un reporte público mediante folio y código de acceso privado.
     * Permite enviar cuerpo JSON dentro de un método HTTP GET conforme exige Go.
     * Corresponde a: GET /api/v1/reportes/consultar (handlers.ConsultReport)
     */
    @POST("api/v1/reportes/consultar")
    suspend fun consultarConCodigo(
        @Body request: ReporteConsultaInput
    ): Response<ReporteResponse>

    /**
     * Obtiene la lista de categorías o instituciones disponibles.
     * Corresponde a: GET /api/v1/instituciones (handlers.Institutions)
     */
    @GET("api/v1/instituciones")
    suspend fun obtenerCategorias(): Response<List<CategoriaModel>>
}

/**
 * Modelo DTO para la consulta pública por folio y código privado de acceso.
 */
data class ReporteConsultaInput(
    @com.google.gson.annotations.SerializedName("folio")
    val folio: String,

    @com.google.gson.annotations.SerializedName("codigo_acceso")
    val codigoAcceso: String
)