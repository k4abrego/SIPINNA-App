package com.example.sippina_aplicacion.view.perfilusuario

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.AuthVM

/**
 * Pantalla para el inicio de sesión de usuarios existentes.
 *
 * @param modifier Modificador de diseño.
 * @param authVM ViewModel encargado de gestionar la autenticación.
 * @param onSiguiente Callback ejecutado tras un inicio de sesión exitoso.
 * @param onRegistrarse Callback para navegar a la pantalla de registro si no tiene cuenta.
 * @param onAtras Callback invocado al presionar el botón de regresar.
 */
@Composable
fun PantallaInicioSesion(
    modifier: Modifier = Modifier,
    authVM: AuthVM = viewModel(),
    onSiguiente: () -> Unit = {},
    onRegistrarse: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    val state by authVM.state.collectAsState()

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var mostrarErrores by remember { mutableStateOf(false) }

    val esCorreoValido = Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()
    val esContrasenaValida = contrasena.trim().isNotEmpty()

    val mensajeErrorCorreo = when {
        !mostrarErrores -> ""
        correo.isBlank() -> "Campo obligatorio"
        !esCorreoValido -> "Correo electrónico inválido"
        else -> ""
    }

    val mensajeErrorContrasena = when {
        !mostrarErrores -> ""
        contrasena.isBlank() -> "Campo obligatorio"
        else -> ""
    }

    val fondoDegradado = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD3E7FF),
            Color(0xFFBCA7E8)
        )
    )

    // Redirección automática cuando el backend autentica de forma exitosa
    LaunchedEffect(state.autenticado) {
        if (state.autenticado) {
            onSiguiente()
            authVM.limpiarMensajes()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondoDegradado)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                onClick = onAtras,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterStart),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFFEAF4FC),
                    contentColor = Color(0xFF3D27C9)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Botón atrás",
                    modifier = Modifier.size(22.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logorieti),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(170.dp)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Iniciar Sesión",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3D20C9)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CampoRegistro(
            valor = correo,
            onValorCambio = {
                correo = it
                if (mostrarErrores) mostrarErrores = false
            },
            texto = "Correo electrónico",
            keyboardType = KeyboardType.Email,
            isError = mostrarErrores && (!esCorreoValido || correo.isBlank()),
            mensajeError = mensajeErrorCorreo
        )

        Spacer(modifier = Modifier.height(14.dp))

        CampoRegistro(
            valor = contrasena,
            onValorCambio = {
                contrasena = it
                if (mostrarErrores) mostrarErrores = false
            },
            texto = "Contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            isError = mostrarErrores && (!esContrasenaValida || contrasena.isBlank()),
            mensajeError = mensajeErrorContrasena
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = state.error!!,
                color = Color(0xFFC01B1E),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                val esFormularioValido = esCorreoValido && esContrasenaValida

                if (esFormularioValido) {
                    authVM.iniciarSesion(
                        correo = correo.trim(),
                        contrasena = contrasena.trim()
                    )
                } else {
                    mostrarErrores = true
                }
            },
            enabled = !state.cargando,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(48.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3D20C9)
            )
        ) {
            if (state.cargando) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.5.dp
                )
            } else {
                Text(
                    text = "Ingresar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onRegistrarse) {
            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                color = Color(0xFF3D20C9),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Vista Previa Iniciar Sesión"
)
@Composable
fun PantallaInicioSesionPreview() {
    SIPPINAAplicacionTheme {
        PantallaInicioSesion(
            onSiguiente = {},
            onRegistrarse = {},
            onAtras = {}
        )
    }
}