package com.example.sippina_aplicacion.model.Reporte
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

interface ReportesAPI {

    @POST("api/v1/reportes")
    suspend fun crearReporte(
        @Body request: PeticionCrearReporte
    ): Response<RespuestaReporte>

    @GET("api/v1/reportes/categorias")
    suspend fun obtenerCategorias(): Response<List<CategoriaModel>>

    @GET("api/v1/reportes")
    suspend fun obtenerListaReportes(
        @Query("estado") estado: String? = null,
        @Query("limite") limite: Int = 20,
        @Query("pagina") pagina: Int = 1
    ): Response<List<RespuestaReporte>>

    @GET("api/v1/reportes/folio/{folio}")
    suspend fun obtenerReportePorFolio(
        @Path("folio") folio: String
    ): Response<RespuestaReporte>

    @GET("api/v1/reportes/{id}/historial")
    suspend fun obtenerHistorialReporte(
        @Path("id") reporteId: String
    ): Response<List<HistorialEstadoModel>>

    @Multipart
    @POST("api/v1/reportes/{reporteId}/evidencias")
    suspend fun subirEvidencias(
        @Path("reporteId") reporteId: String,
        @Part archivos: List<MultipartBody.Part>
    ): Response<RespuestaReporte>
}