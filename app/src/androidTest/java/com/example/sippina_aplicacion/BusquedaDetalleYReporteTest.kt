package com.example.sippina_aplicacion


import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.sippina_aplicacion.model.ReporteModel
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.perfilusuario.BuscarReporteExistente
import com.example.sippina_aplicacion.view.perfilusuario.DetalleReporte
import com.google.android.gms.maps.MapsInitializer
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BusquedaYDetalleReporteUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Inicializamos Maps directamente en el hilo de interfaz de usuario (Main Thread)
        composeTestRule.runOnUiThread {
            try {
                MapsInitializer.initialize(context)
            } catch (_: Throwable) {
                // Seguro si el emulador no tiene Google Play Services instalado
            }
        }
    }

    // --- BUSCAR REPORTE EXISTENTE ---
    @Test
    fun buscarReporteExistente_capturaCampos_y_muestraResultado() {
        val reporteResultado = ReporteModel(folio = "FOLIO-ENCONTRADO-99", estatus = "En proceso")

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                BuscarReporteExistente(
                    reporteEncontrado = reporteResultado,
                    buscando = false,
                    folioInicial = "REP-1234-5678",
                    codigoInicial = "1234"
                )
            }
        }

        composeTestRule.onNodeWithText("Reportes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingrese el folio y código de seguridad para dar seguimiento y detalles de tu reporte").assertIsDisplayed()

        // Como cumple >= 8 y >= 4 caracteres, el resultado se muestra y hacemos scroll hacia él
        composeTestRule.onNodeWithText("FOLIO-ENCONTRADO-99")
            .performScrollTo()
            .assertIsDisplayed()
    }

    // --- DETALLE DEL REPORTE ---
    @Test
    fun detalleReporte_muestraTimelineYDatosDelIncidente() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                DetalleReporte(
                    folio = "REP-DETALLE-123",
                    fechaEnvio = "20/10/2026 10:00",
                    estadoActual = 1, // En revisión
                    descripcion = "Incidente en transporte público",
                    direccion = "Estación Centro",
                    latitud = 19.5591,
                    longitud = -99.2833
                )
            }
        }

        // Encabezados
        composeTestRule.onNodeWithText("REP-DETALLE-123").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enviado: 20/10/2026 10:00").assertIsDisplayed()

        // Estados del timeline
        composeTestRule.onNodeWithText("Recibido").assertIsDisplayed()
        composeTestRule.onNodeWithText("Su reporte se encuentra en revisión").assertIsDisplayed()

        // Tarjetas descriptivas
        composeTestRule.onNodeWithText("Descripción del incidente").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Incidente en transporte público").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ubicación del reporte").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Estación Centro").assertIsDisplayed()
    }
}

