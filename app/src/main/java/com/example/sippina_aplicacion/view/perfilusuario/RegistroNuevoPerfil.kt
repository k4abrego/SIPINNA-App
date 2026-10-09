package com.example.sippina_aplicacion.view.perfilusuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.viewmodel.AuthVM
@Composable
fun RegistroNuevoPerfil(
    modifier: Modifier = Modifier,
    authVM: AuthVM = viewModel(),
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {},
) {
    val state by authVM.state.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var numeroMovil by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var mostrarErrores by remember { mutableStateOf(false) }

    val esCorreoValido = android.util.Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()
    val esTelefonoValido = numeroMovil.trim().length >= 10
    val esContrasenaValida = contrasena.trim().length >= 6

    val mensajeErrorCorreo = when {
        !mostrarErrores -> ""
        correo.isBlank() -> "Campo obligatorio"
        !esCorreoValido -> "Correo electrónico inválido"
        else -> ""
    }

    val mensajeErrorTelefono = when {
        !mostrarErrores -> ""
        numeroMovil.isBlank() -> "Campo obligatorio"
        !esTelefonoValido -> "El teléfono debe tener 10 dígitos"
        else -> ""
    }

    val mensajeErrorContrasena = when {
        !mostrarErrores -> ""
        contrasena.isBlank() -> "Campo obligatorio"
        !esContrasenaValida -> "La contraseña debe tener al menos 6 caracteres"
        else -> ""
    }

    val fondoDegradado = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD3E7FF),
            Color(0xFFBCA7E8)
        )
    )

    // Redirección automática cuando el backend responde de forma exitosa
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
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(170.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CampoRegistro(
            valor = nombre,
            onValorCambio = {
                nombre = it
                if (mostrarErrores) mostrarErrores = false
            },
            texto = "Nombre(s)",
            isError = mostrarErrores && nombre.isBlank()
        )

        Spacer(modifier = Modifier.height(12.dp))

        CampoRegistro(
            valor = apellidoPaterno,
            onValorCambio = {
                apellidoPaterno = it
                if (mostrarErrores) mostrarErrores = false
            },
            texto = "Apellido Paterno",
            isError = mostrarErrores && apellidoPaterno.isBlank()
        )

        Spacer(modifier = Modifier.height(12.dp))

        CampoRegistro(
            valor = apellidoMaterno,
            onValorCambio = { apellidoMaterno = it },
            texto = "Apellido Materno (Opcional)"
        )

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        CampoRegistro(
            valor = numeroMovil,
            onValorCambio = {
                numeroMovil = it
                if (mostrarErrores) mostrarErrores = false
            },
            texto = "Número Móvil",
            keyboardType = KeyboardType.Phone,
            isError = mostrarErrores && (!esTelefonoValido || numeroMovil.isBlank()),
            mensajeError = mensajeErrorTelefono
        )

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                val esFormularioValido = nombre.isNotBlank() &&
                        apellidoPaterno.isNotBlank() &&
                        esCorreoValido &&
                        esTelefonoValido &&
                        esContrasenaValida

                if (esFormularioValido) {
                    val apellidosCompletos = "$apellidoPaterno $apellidoMaterno".trim()
                    authVM.registrarUsuario(
                        correo = correo.trim(),
                        contrasena = contrasena.trim(),
                        nombre = nombre.trim(),
                        apellidos = apellidosCompletos,
                        telefono = numeroMovil.trim().ifBlank { null }
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
                    text = "Crear cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun CampoRegistro(
    valor: String,
    onValorCambio: (String) -> Unit,
    texto: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    mensajeError: String = "Campo obligatorio"
) {
    val colorRojoError = Color(0xFFC01B1E)

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = valor,
            onValueChange = onValorCambio,
            placeholder = {
                Text(
                    text = texto,
                    color = if (isError) colorRojoError else Color.Gray,
                    fontSize = 15.sp
                )
            },
            isError = isError,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .then(
                    if (isError) {
                        Modifier.border(
                            width = 1.5.dp,
                            color = colorRojoError,
                            shape = RoundedCornerShape(20.dp)
                        )
                    } else {
                        Modifier
                    }
                ),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )

        if (isError) {
            Text(
                text = mensajeError,
                color = colorRojoError,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Vista Previa Registro"
)
@Composable
fun RegistroNuevoPerfilPreview() {
    SIPPINAAplicacionTheme {
        RegistroNuevoPerfil(
            onSiguiente = {},
            onAtras = {}
        )
    }
}