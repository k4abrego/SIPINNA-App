package com.example.sippina_aplicacion
import com.example.sippina_aplicacion.model.Autenticacion.*
import com.example.sippina_aplicacion.model.Reporte.ReporteConsultaInput
import com.example.sippina_aplicacion.model.Reporte.ReportesAPI
import okhttp3.MultipartBody
import retrofit2.Response

/**
 * Implementación de prueba para el servicio de autenticación.
 * Simula las respuestas del servidor en memoria sin requerir conexión a internet.
 */
class PruebaAutenticacionAPI : AutenticacionAPI {
    override suspend fun solicitarReto(correo: String): Response<ChallengeResponse> =
        Response.success(ChallengeResponse(userId = 1L, challenge = "dGVzdA=="))

    override suspend fun verificarReto(request: VerifyChallengeInput): Response<TokenResponse> =
        Response.success(TokenResponse(token = "token_de_prueba_jwt"))

    override suspend fun registrar(request: RegistroInput): Response<RegistroResponse> =
        Response.success(RegistroResponse(userId = 1L, mensaje = "Usuario registrado exitosamente"))

    override suspend fun obtenerUsuario(correo: String, token: String): Response<UsuarioResponse> =
        Response.success(UsuarioResponse(userId = 1L, nombre = "Roberto", apellido = "Martínez", telefono = "5512345678"))

    override suspend fun editarUsuario(correo: String, token: String, request: EditarUsuarioInput): Response<Unit> =
        Response.success(Unit)

    override suspend fun cambiarContrasenna(correo: String, token: String, request: CambiarContrasenaInput): Response<Unit> =
        Response.success(Unit)

    override suspend fun logout(userId: Long, token: String): Response<Unit> =
        Response.success(Unit)
}

/**
 * Implementación de prueba para el servicio de reportes.
 * Simula la creación, consulta y carga de evidencias de reportes en memoria.
 */
class PruebaReportesAPI : ReportesAPI {
    override suspend fun crearReporte(request: ReporteRequest, token: String?): Response<ReporteCreacionResponse> =
        Response.success(ReporteCreacionResponse(folio = "REP-2026-TEST", codigoPrivado = "COD-PRUEBA-123"))

    override suspend fun subirEvidencias(folio: String, archivos: List<MultipartBody.Part>): Response<SubirEvidenciasResponse> =
        Response.success(SubirEvidenciasResponse(mensaje = "Evidencias procesadas"))

    override suspend fun obtenerReportePorFolio(folio: String, token: String?): Response<ReporteResponse> =
        Response.success(ReporteResponse(folio = folio, estatus = "En proceso", descripcion = "Reporte de prueba"))

    override suspend fun obtenerListaReportes(token: String?, estado: String?): Response<List<ReporteResponse>> =
        Response.success(listOf(ReporteResponse(folio = "REP-001", estatus = "En revisión", descripcion = "Caso inicial de prueba")))

    override suspend fun consultarConCodigo(request: ReporteConsultaInput): Response<ReporteResponse> =
        Response.success(ReporteResponse(folio = request.folio, estatus = "En proceso"))

    override suspend fun obtenerCategorias(): Response<List<CategoriaModel>> =
        Response.success(emptyList())
}