import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.sippina_aplicacion.PruebaAutenticacionAPI
import com.example.sippina_aplicacion.model.Autenticacion.AuthRepository
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.perfilusuario.RegistroNuevoPerfil
import com.example.sippina_aplicacion.viewmodel.AuthVM
import org.junit.Rule
import org.junit.Test

class RegistroNuevoPerfilUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val authVMPrueba = AuthVM(AuthRepository(PruebaAutenticacionAPI()))

    @Test
    fun formularioRegistroVacio_alIntentarCrearCuenta_marcaErroresEnPantalla() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                RegistroNuevoPerfil(authVM = authVMPrueba)
            }
        }

        // Hacemos scroll hasta el botón inferior y lo presionamos
        composeTestRule.onNodeWithText("Crear cuenta")
            .performScrollTo()
            .performClick()

        composeTestRule.onAllNodesWithText("Campo obligatorio").assertCountEquals(5)
    }

    @Test
    fun telefonoConMenosDeDiezDigitos_muestraErrorDeLongitud() {
        composeTestRule.setContent {
            SIPPINAAplicacionTheme {
                RegistroNuevoPerfil(authVM = authVMPrueba)
            }
        }

        composeTestRule.onNodeWithText("Número Móvil")
            .performScrollTo()
            .performTextInput("12345")

        composeTestRule.onNodeWithText("Crear cuenta")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithText("El teléfono debe tener 10 dígitos").assertIsDisplayed()
    }
}