package com.example.sippina_aplicacion.model.Reporte

import com.example.sippina_aplicacion.model.Autenticacion.CategoriaModel
import com.example.sippina_aplicacion.model.Autenticacion.ReporteCreacionResponse
import com.example.sippina_aplicacion.model.Autenticacion.ReporteRequest
import com.example.sippina_aplicacion.model.Autenticacion.ReporteResponse
import com.example.sippina_aplicacion.model.Autenticacion.ServicioRemoto
import com.example.sippina_aplicacion.model.Autenticacion.SubirEvidenciasResponse
import okhttp3.MultipartBody
class ReporteRepository(
    private val api: ReportesAPI = ServicioRemoto.reportesAPI
) {

    /**
     * Envía la solicitud de creación de un nuevo reporte en formato anidado.
     */
    suspend fun crearReporte(
        peticion: ReporteRequest,
        token: String? = null
    ): Result<ReporteCreacionResponse> {
        return try {
            val authHeader = token?.let { if (it.startsWith("Bearer ")) it else "Bearer $it" }
            val response = api.crearReporte(request = peticion, token = authHeader)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al crear reporte: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene el catalogo de categorías disponibles para etiquetar reportes.
     */
    suspend fun obtenerCategorias(): Result<List<CategoriaModel>> {
        return try {
            val response = api.obtenerCategorias()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al cargar categorías: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene la lista de reportes asociados al usuario autenticado.
     */
    suspend fun obtenerListaReportes(token: String? = null, estado: String? = null): Result<List<ReporteResponse>> {
        return try {
            val authHeader = token?.let { if (it.startsWith("Bearer ")) it else "Bearer $it" }
            val response = api.obtenerListaReportes(token = authHeader, estado = estado)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al obtener reportes: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Consulta el detalle de un reporte utilizando únicamente su folio (usuario autenticado).
     */
    suspend fun obtenerReportePorFolio(folio: String, token: String? = null): Result<ReporteResponse> {
        return try {
            val authHeader = token?.let { if (it.startsWith("Bearer ")) it else "Bearer $it" }
            val response = api.obtenerReportePorFolio(folio = folio, token = authHeader)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Reporte no encontrado: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Consulta un reporte público mediante su folio y código privado de acceso.
     */
    suspend fun consultarConCodigo(folio: String, codigo: String): Result<ReporteResponse> {
        return try {
            val input = ReporteConsultaInput(folio = folio.trim(), codigoAcceso = codigo.trim())
            val response = api.consultarConCodigo(input)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al consultar reporte: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sube archivos de imagen o video vinculados al folio de un reporte.
     */
    suspend fun subirEvidencias(reporteId: String, archivos: List<MultipartBody.Part>): Result<SubirEvidenciasResponse> {
        return try {
            val response = api.subirEvidencias(reporteId, archivos)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al subir evidencias: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}