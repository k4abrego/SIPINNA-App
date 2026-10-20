package com.example.sippina_aplicacion

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.VistasExtra.AvisoPrivacidad
import com.example.sippina_aplicacion.view.VistasExtra.ConoceSIPINNA
import com.example.sippina_aplicacion.view.VistasExtra.QueSituacionesReportar
import org.junit.Rule
import org.junit.Test


class VistasInformativasUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun avisoPrivacidad_muestraTarjetasLegalesYBotonEntendido() {
        var entendidoPresionado = false

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                AvisoPrivacidad(onEntendido = { entendidoPresionado = true })
            }
        }

        composeTestRule.onNodeWithText("Aviso Privacidad").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tus datos y tu privacidad").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Quién puede ver mi información?").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Reportes anónimos").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Seguimiento").performScrollTo().assertIsDisplayed()

        composeTestRule.onNodeWithText("Entendido").performScrollTo().performClick()
        assert(entendidoPresionado)
    }

    @Test
    fun conoceSipinna_muestraSeccionesInstitucionalesYRutaRieti() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                ConoceSIPINNA()
            }
        }

        composeTestRule.onAllNodesWithText("SIPINNA").onFirst().assertIsDisplayed()

        // Secciones institucionales
        composeTestRule.onNodeWithText("Nuestro Objetivo").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Qué es la Ruta RIETI?").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Relación con SIPINNA").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Cómo ayudamos?").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun queSituacionesReportar_muestraCatalogoDeCasos() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                QueSituacionesReportar()
            }
        }

        composeTestRule.onNodeWithText("¿Qué situaciones debo reportar?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Menores de edad solos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Posible situación de violencia").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Posible situación de abandono").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Riesgo a su integridad").performScrollTo().assertIsDisplayed()
    }
}