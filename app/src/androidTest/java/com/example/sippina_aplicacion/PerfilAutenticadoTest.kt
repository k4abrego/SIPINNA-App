package com.example.sippina_aplicacion

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import com.example.sippina_aplicacion.model.EstadoNotificacion
import com.example.sippina_aplicacion.model.EstadoReporte
import com.example.sippina_aplicacion.model.ReporteModel
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.VerListaReportesContent
import com.example.sippina_aplicacion.view.PerfilAutenticado.*
import org.junit.Rule
import org.junit.Test

class PerfilAutenticadoUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun editarPerfil_clicGuardarCambios_muestraDialogoConfirmacion() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                EditarPerfil()
            }
        }

        composeTestRule.onNodeWithText("Guardar cambios")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithText("Confirmar cambios").assertIsDisplayed()
        composeTestRule.onNodeWithText("Guardar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancelar").assertIsDisplayed()
    }

    @Test
    fun borradores_sinBorrador_muestraTextoVacio() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                Borradores(borrador = null)
            }
        }

        composeTestRule.onNodeWithText("Continuar reporte").assertIsDisplayed()
        composeTestRule.onNodeWithText("No hay reportes pendientes de completar").assertIsDisplayed()
    }

    @Test
    fun borradores_conBorrador_muestraTarjetaYBotonesContinuarYEliminar() {
        val borradorPrueba = EstadoReporte(pasoActual = 2, descripcionHechos = "Borrador de prueba")

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                Borradores(borrador = borradorPrueba)
            }
        }

        composeTestRule.onNodeWithText("Reporte en progreso").assertIsDisplayed()
        composeTestRule.onNodeWithText("Continuar en: Descripción del menor").assertIsDisplayed()
        composeTestRule.onNodeWithText("Continuar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Eliminar").assertIsDisplayed()
    }

    // --- NOTIFICACIONES (Divididas en 2 tests independientes) ---
    @Test
    fun notificaciones_listaVacia_muestraTextoInformativo() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                Notificaciones(notificaciones = emptyList())
            }
        }

        composeTestRule.onNodeWithText("Notificaciones").assertIsDisplayed()
        composeTestRule.onNodeWithText("No tienes notificaciones").assertIsDisplayed()
    }

    @Test
    fun notificaciones_conElementos_muestraTarjetasConDetalles() {
        val listaNotif = listOf(
            EstadoNotificacion(
                id = 1,
                titulo = "Actualización SIPINNA",
                descripcion = "Tu caso está en revisión",
                fecha = "Hace 5 min",
                leida = false
            )
        )

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                Notificaciones(notificaciones = listaNotif)
            }
        }

        composeTestRule.onNodeWithText("Notificaciones").assertIsDisplayed()
        composeTestRule.onNodeWithText("Actualización SIPINNA").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tu caso está en revisión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hace 5 min").assertIsDisplayed()
    }

    @Test
    fun verListaReportes_buscadorFiltraResultados() {
        val reportes = listOf(
            ReporteModel(folio = "REP-2026-AAAA", estatus = "Enviado"),
            ReporteModel(folio = "REP-2026-BBBB", estatus = "Cerrado")
        )

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                VerListaReportesContent(listaReportes = reportes)
            }
        }

        composeTestRule.onNodeWithText("Reportes").assertIsDisplayed()
        composeTestRule.onNodeWithText("REP-2026-AAAA").assertIsDisplayed()
        composeTestRule.onNodeWithText("REP-2026-BBBB").assertIsDisplayed()

        composeTestRule.onNodeWithText("Buscar por folio o estatus...")
            .performTextInput("AAAA")

        composeTestRule.onNodeWithText("REP-2026-AAAA").assertIsDisplayed()
        composeTestRule.onNodeWithText("REP-2026-BBBB").assertDoesNotExist()
    }
}