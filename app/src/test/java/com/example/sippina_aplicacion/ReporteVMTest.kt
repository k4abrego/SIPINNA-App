import android.content.Context
import android.net.Uri
import com.example.sippina_aplicacion.MainDispatcherRule
import com.example.sippina_aplicacion.model.Autenticacion.ReporteCreacionResponse
import com.example.sippina_aplicacion.model.Autenticacion.ReporteResponse
import com.example.sippina_aplicacion.model.Reporte.ReporteRepository
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReporteVMTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val repository: ReporteRepository = mockk(relaxed = true)
    private val mockContext: Context = mockk(relaxed = true)
    private lateinit var viewModel: ReporteVM

    @Before
    fun setUp() {
        viewModel = ReporteVM(repository)
    }

    @Test
    fun `pasos del asistente deben avanzar hasta maximo 4 y retroceder hasta minimo 0`() {
        assertEquals(0, viewModel.uiState.value.pasoActual)

        repeat(6) { viewModel.siguientePaso() }
        assertEquals(4, viewModel.uiState.value.pasoActual)

        repeat(6) { viewModel.anteriorPaso() }
        assertEquals(0, viewModel.uiState.value.pasoActual)
    }

    @Test
    fun `setUbicacion debe registrar coordenadas, direccion y marcar ubicacionConfirmada`() {
        viewModel.setUbicacion(
            lat = 19.5591,
            lng = -99.2833,
            direccion = "Av Hidalgo 20, Atizapán",
            idTerritorio = 126
        )

        val state = viewModel.uiState.value
        assertEquals(19.5591, state.latitud, 0.0001)
        assertEquals(-99.2833, state.longitud, 0.0001)
        assertEquals("Av Hidalgo 20, Atizapán", state.direccion)
        assertEquals(126, state.idTerritorio)
        assertTrue(state.ubicacionConfirmada)
        assertTrue(viewModel.ubicacionValida())
    }

    @Test
    fun `gestion dinamica de menores debe agregar y navegar secuencialmente`() {
        viewModel.cambiarCantidadMenores(2) // Total = 3 menores
        assertEquals(3, viewModel.uiState.value.cantidadMenores)
        assertEquals(3, viewModel.uiState.value.menores.size)
        assertEquals(0, viewModel.uiState.value.indiceMenorActual)

        viewModel.setRangoEdad("0-5")
        viewModel.setGenero("Masculino")
        viewModel.setDescripcionFisica("Sudadera roja")

        assertTrue(viewModel.avanzarAlSiguienteMenor())
        assertEquals(1, viewModel.uiState.value.indiceMenorActual)

        viewModel.setRangoEdad("12-14")
        viewModel.setGenero("Femenino")

        assertTrue(viewModel.regresarAlMenorAnterior())
        assertEquals(0, viewModel.uiState.value.indiceMenorActual)
        assertEquals("0-5", viewModel.uiState.value.rangoEdad)
        assertEquals("Masculino", viewModel.uiState.value.genero)

        assertFalse(viewModel.regresarAlMenorAnterior())
    }

    @Test
    fun `agregarEvidencia no debe admitir duplicados por URI`() {
        val uri1 = mockk<Uri>()
        val uri2 = mockk<Uri>()

        viewModel.agregarEvidencia(uri1, "imagen", "foto1.jpg")
        viewModel.agregarEvidencia(uri1, "imagen", "foto1.jpg") // Intento de duplicado
        viewModel.agregarEvidencia(uri2, "video", "video.mp4")

        assertEquals(2, viewModel.uiState.value.evidencias.size)

        viewModel.eliminarEvidencia(uri1)
        assertEquals(1, viewModel.uiState.value.evidencias.size)
        assertEquals(uri2, viewModel.uiState.value.evidencias[0].uri)
    }

    @Test
    fun `validaciones de formulario deben responder correctamente`() {
        assertFalse(viewModel.descripcionHechosValida())
        assertFalse(viewModel.descripcionFisicaValida())

        viewModel.setDescripcionHechos("Situación observada en calle")
        viewModel.setDescripcionFisica("Complexión delgada")

        assertTrue(viewModel.descripcionHechosValida())
        assertTrue(viewModel.descripcionFisicaValida())

        // Validación de correo en privacidad
        viewModel.actualizarOpcionPrivacidad("anonimo")
        assertTrue(viewModel.correoSeguimientoValido())

        viewModel.actualizarOpcionPrivacidad("actualizaciones")
        viewModel.setCorreoSeguimiento("correo_sin_formato")
        assertFalse(viewModel.correoSeguimientoValido())

        viewModel.setCorreoSeguimiento("contacto@sipinna.gob.mx")
        assertTrue(viewModel.correoSeguimientoValido())
    }

    @Test
    fun `formatearFechaLocal debe procesar cadenas ISO correctamente o devolver el texto original si falla`() {
        val isoDate = "2026-10-20T12:00:00Z"
        val fechaFormateada = viewModel.formatearFechaLocal(isoDate)
        assertTrue(fechaFormateada.contains("2026"))
        assertTrue(fechaFormateada.contains("/"))

        assertEquals("", viewModel.formatearFechaLocal(null))
        assertEquals("", viewModel.formatearFechaLocal("   "))
        assertEquals("fecha_invalida", viewModel.formatearFechaLocal("fecha_invalida"))
    }

    @Test
    fun `obtenerMisReportesDelBackEnd exitoso debe formatear fechas y poblar misReportes`() = runTest {
        val reporteBack = ReporteResponse(
            folio = "FOLIO-101",
            estatus = "En proceso",
            fechaEnvio = "2026-10-20T10:00:00Z",
            descripcion = "Reporte de prueba",
            direccion = "Calle 1"
        )
        coEvery { repository.obtenerListaReportes(any(), any()) } returns Result.success(listOf(reporteBack))

        viewModel.obtenerMisReportesDelBackEnd(token = "Bearer test_token")

        val reportes = viewModel.misReportes.value
        assertEquals(1, reportes.size)
        assertEquals("FOLIO-101", reportes[0].folio)
        assertEquals("En proceso", reportes[0].estatus)
        assertFalse(viewModel.cargando.value)
    }

    @Test
    fun `enviarReporteAlBackEnd exitoso debe aplicar reglas de negocio para Go y actualizar folio y codigo`() = runTest {
        val respMock = ReporteCreacionResponse(folio = "REP-2026-0001", codigoPrivado = "COD-999")

        coEvery {
            repository.crearReporte(
                match { request ->
                    // Valida que si idTerritorio es <= 1, el ViewModel lo asigne a 126
                    request.ubicacion.idTerritorio == 126 &&
                            // Valida que el peligro "si" se normalice a "Sí"
                            request.peligroInmediato == "Sí" &&
                            // Valida que el género "Desconocido" se transforme a "No identificado"
                            request.menores.all { it.genero == "No identificado" }
                },
                token = "token_abc"
            )
        } returns Result.success(respMock)

        coEvery { repository.obtenerListaReportes(any(), any()) } returns Result.success(emptyList())

        viewModel.setDescripcionHechos("Menor en situación de calle")
        viewModel.setUbicacion(19.5, -99.2, "Centro", idTerritorio = 1)
        viewModel.setPeligroInmediato("si")
        viewModel.setGenero("Desconocido")

        viewModel.enviarReporteAlBackEnd(mockContext, token = "token_abc", userId = 5L)

        val state = viewModel.uiState.value
        assertFalse(state.enviando)
        assertTrue(state.reporteExitoso)
        assertEquals("REP-2026-0001", state.folio)
        assertEquals("COD-999", state.codigoPrivado)
        assertNull(state.errorEnvio)
    }

    @Test
    fun `enviarReporteAlBackEnd con error debe reflejar el mensaje en errorEnvio`() = runTest {
        coEvery { repository.crearReporte(any(), any()) } returns Result.failure(Exception("Error de conexión al servidor"))

        viewModel.enviarReporteAlBackEnd(mockContext, token = null)

        val state = viewModel.uiState.value
        assertFalse(state.enviando)
        assertFalse(state.reporteExitoso)
        assertEquals("Error de conexión al servidor", state.errorEnvio)
    }

    @Test
    fun `obtenerDetalleReportePorFolio exitoso debe poblar detalleReporte`() = runTest {
        val reporteMock = ReporteResponse(
            folio = "REP-777",
            estatus = "En revisión",
            fechaEnvio = "2026-10-20T10:00:00Z",
            descripcion = "Detalle reporte prueba"
        )
        coEvery { repository.obtenerReportePorFolio("REP-777", any()) } returns Result.success(reporteMock)

        viewModel.obtenerDetalleReportePorFolio("REP-777", token = "token_test")

        val detalle = viewModel.detalleReporte.value
        assertNotNull(detalle)
        assertEquals("REP-777", detalle?.folio)
        assertEquals("En revisión", detalle?.estatus)
        assertFalse(viewModel.cargando.value)
    }

    @Test
    fun `limpiarFormulario y reiniciarEstadoReporte deben reiniciar uiState a valores por defecto`() {
        viewModel.setDescripcionHechos("Hechos previos")
        viewModel.siguientePaso()

        viewModel.limpiarFormulario()

        assertEquals(0, viewModel.uiState.value.pasoActual)
        assertEquals("", viewModel.uiState.value.descripcionHechos)
    }
}