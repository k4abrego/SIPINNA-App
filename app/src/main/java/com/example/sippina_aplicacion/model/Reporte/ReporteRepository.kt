package com.example.sippina_aplicacion.model.Reporte
import com.example.sippina_aplicacion.model.Autenticacion.ServicioRemoto
import okhttp3.MultipartBody

class ReporteRepository(
    private val api: ReportesAPI = ServicioRemoto.reportesAPI
) {

    suspend fun crearReporte(peticion: PeticionCrearReporte): Result<RespuestaReporte> {
        return try {
            val response = api.crearReporte(peticion)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al crear reporte: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerCategorias(): Result<List<CategoriaModel>> {
        return try {
            val response = api.obtenerCategorias()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar categorías: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerListaReportes(estado: String? = null): Result<List<RespuestaReporte>> {
        return try {
            val response = api.obtenerListaReportes(estado = estado)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener reportes: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerReportePorFolio(folio: String): Result<RespuestaReporte> {
        return try {
            val response = api.obtenerReportePorFolio(folio)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Reporte no encontrado: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun subirEvidencias(reporteId: String, archivos: List<MultipartBody.Part>): Result<RespuestaReporte> {
        return try {
            val response = api.subirEvidencias(reporteId, archivos)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al subir evidencias: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}