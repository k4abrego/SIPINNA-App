package com.example.sippina_aplicacion

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import com.example.sippina_aplicacion.model.EstadoReporte
import com.example.sippina_aplicacion.model.Reporte.ReporteRepository
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.*
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import org.junit.Rule
import org.junit.Test

class PantallasReporteRestantesUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val reporteVM = ReporteVM(ReporteRepository(PruebaReportesAPI()))

    @Test
    fun dondeOcurrio_clicIngresarDireccionManual_abreDialogoParaEscribir() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                DondeOcurrio(viewModel = reporteVM)
            }
        }

        composeTestRule.onNodeWithText("¿Dónde ocurrió?").assertIsDisplayed()

        composeTestRule.onNodeWithText("Escribir dirección").assertDoesNotExist()

        composeTestRule.onNodeWithText("Ingresar dirección manualmente")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithText("Escribir dirección").assertIsDisplayed()
        composeTestRule.onNodeWithText("Guardar dirección").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancelar").performClick()
        composeTestRule.onNodeWithText("Escribir dirección").assertDoesNotExist()
    }

    @Test
    fun evidencia_muestraOpcionesMultimediaYAvisoOpcional() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                Evidencia(viewModel = reporteVM)
            }
        }

        composeTestRule.onNodeWithText("Evidencia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Si tienes alguna evidencia, puedes agregarla aquí.").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Tomar foto").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Galería").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Grabar video").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Grabar audio").performScrollTo().assertIsDisplayed()

        composeTestRule.onNodeWithText("Este paso es opcional. Puedes continuar sin adjuntar archivos.")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun privacidad_modalidadActualizaciones_sinCorreo_muestraError() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                PrivacidadSeguimiento(
                    viewModel = reporteVM,
                    estaAutenticado = false
                )
            }
        }

        composeTestRule.onNodeWithText("Privacidad y seguimiento").assertIsDisplayed()

        composeTestRule.onNodeWithText("Me interesa recibir actualizaciones")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithText("Correo para recibir actualizaciones").assertIsDisplayed()

        composeTestRule.onNodeWithText("Finalizar reporte")
            .performClick()

        composeTestRule.onNodeWithText("* Por favor ingresa un correo para recibir actualizaciones").assertIsDisplayed()
    }

    @Test
    fun reporteGenerado_muestraFolioYCodigoPrivado() {
        reporteVM.reiniciarEstadoReporte()

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                ReporteGenerado(viewModel = reporteVM)
            }
        }

        composeTestRule.onNodeWithText("¡Gracias!").assertIsDisplayed()
        composeTestRule.onNodeWithText("¡Tu reporte ha sido registrado con\néxito!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Folio").assertIsDisplayed()
        composeTestRule.onNodeWithText("Código privado").assertIsDisplayed()
        composeTestRule.onNodeWithText("Copiar datos del reporte").performScrollTo().assertIsDisplayed()
    }
}