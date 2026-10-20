package com.example.sippina_aplicacion

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.sippina_aplicacion.model.Autenticacion.AuthRepository
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.perfilusuario.PantallaInicioSesion
import com.example.sippina_aplicacion.viewmodel.AuthVM
import org.junit.Rule
import org.junit.Test

class PantallaInicioSesionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Instancia del ViewModel configurada con el servicio de prueba
    private val authVMPrueba = AuthVM(AuthRepository(PruebaAutenticacionAPI()))

    @Test
    fun verificarElementosVisualesIniciales_seMuestranCorrectamente() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                PantallaInicioSesion(authVM = authVMPrueba)
            }
        }

        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate aquí").assertIsDisplayed()
    }

    @Test
    fun ingresarSinDatos_debeMostrarMensajesDeErrorDeCampoObligatorio() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                PantallaInicioSesion(authVM = authVMPrueba)
            }
        }

        composeTestRule.onNodeWithText("Ingresar").performClick()
        composeTestRule.onAllNodesWithText("Campo obligatorio").assertCountEquals(2)
    }

    @Test
    fun ingresarCorreoInvalido_debeMostrarErrorDeFormato() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                PantallaInicioSesion(authVM = authVMPrueba)
            }
        }

        composeTestRule.onNodeWithText("Correo electrónico").performTextInput("correo_invalido_sin_arroba")
        composeTestRule.onNodeWithText("Ingresar").performClick()

        composeTestRule.onNodeWithText("Correo electrónico inválido").assertIsDisplayed()
    }

    @Test
    fun clicEnRegistrarse_ejecutaCallback() {
        var navegoARegistro = false

        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                PantallaInicioSesion(
                    authVM = authVMPrueba,
                    onRegistrarse = { navegoARegistro = true }
                )
            }
        }

        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate aquí").performClick()
        assert(navegoARegistro)
    }
}