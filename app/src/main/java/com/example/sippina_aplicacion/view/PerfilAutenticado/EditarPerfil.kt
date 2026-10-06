package com.example.sippina_aplicacion.view.PerfilAutenticado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.SolidColor

// Datos editables del perfil.
// No incluye la contraseña para evitar almacenarla como parte
// de los datos generales del usuario.
data class DatosPerfil(
    val nombreCompleto: String,
    val correoElectronico: String,
    val telefono: String
)

@Composable
fun EditarPerfil(
    onAtras: () -> Unit = {},
    onGuardar: (DatosPerfil, String?) -> Unit = { _, _ -> }
) {

    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)

    var nombreCompleto by remember {
        mutableStateOf("Roberto Martínez Román")
    }

    var correoElectronico by remember {
        mutableStateOf("roberto.martinezm@flopware.com")
    }

    var telefono by remember {
        mutableStateOf("55 1234 5678")
    }

    // Se dejan vacíos hasta que el usuario decida
    // establecer una contraseña nueva.
    var contrasena by remember {
        mutableStateOf("")
    }

    var confirmarContrasena by remember {
        mutableStateOf("")
    }

    // CONTROLES DE LA PANTALLA

    var mostrarConfirmacion by remember {
        mutableStateOf(false)
    }

    var mostrarResultado by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    fun validarDatos(): Boolean {
        mensajeError = when {
            nombreCompleto.isBlank() ->
                "Por favor, ingresa tu nombre completo."

            correoElectronico.isBlank() ->
                "Por favor, ingresa tu correo electrónico."

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(correoElectronico.trim())
                .matches() ->
                "Ingresa un correo electrónico válido."

            telefono.isBlank() ->
                "Por favor, ingresa tu número de teléfono."

            contrasena.isNotEmpty() &&
                    contrasena.length < 8 ->
                "La nueva contraseña debe tener al menos 8 caracteres."

            contrasena != confirmarContrasena ->
                "Las contraseñas no coinciden."
            else -> null
        }
        return mensajeError == null
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(fondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 10.dp),
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
                    modifier = Modifier.size(120.dp)
                )
            }
            Box(
                modifier = Modifier.size(145.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(130.dp)
                        .border(width = 3.dp, color = morado, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar de usuario",
                        tint = morado,
                        modifier = Modifier.size(75.dp)
                    )
                }
                IconButton(
                    onClick = {
                        // TODO: Seleccionar foto de perfil.
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .align(Alignment.TopEnd)
                        .offset(
                            x = (-2).dp,
                            y = 4.dp
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar foto de perfil",
                        tint = morado,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            CampoPerfil(
                etiqueta = "Nombre completo",
                valor = nombreCompleto,
                onValorChange = {
                    nombreCompleto = it
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoPerfil(
                etiqueta = "Correo electrónico",
                valor = correoElectronico,
                onValorChange = {
                    correoElectronico = it
                },
                tipoTeclado = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoPerfil(
                etiqueta = "Teléfono",
                valor = telefono,
                onValorChange = {
                    telefono = it
                },
                tipoTeclado = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoPerfil(
                etiqueta = "Nueva contraseña",
                valor = contrasena,
                onValorChange = {
                    contrasena = it
                },
                esContrasena = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            CampoPerfil(
                etiqueta = "Confirmar contraseña",
                valor = confirmarContrasena,
                onValorChange = {
                    confirmarContrasena = it
                },
                esContrasena = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Deja los campos de contraseña vacíos si no deseas cambiarla.",
                fontSize = 11.sp,
                color = Color.Gray
            )

            if (mensajeError != null) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = mensajeError ?: "",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (validarDatos()) {
                        mostrarConfirmacion = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = morado)
            ) {
                Text(
                    text = "Guardar cambios",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
        }

        if (mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = {
                    mostrarConfirmacion = false
                },
                title = {
                    Text(
                        text = "Confirmar cambios",
                        fontWeight = FontWeight.Bold,
                        color = morado
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas guardar los cambios realizados en tu perfil?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarConfirmacion = false
                            if (validarDatos()) {
                                val datosActualizados = DatosPerfil(
                                    nombreCompleto = nombreCompleto.trim(),
                                    correoElectronico = correoElectronico.trim(),
                                    telefono = telefono.trim()
                                )
                                val nuevaContrasena =
                                    contrasena.takeIf {
                                        it.isNotEmpty()
                                    }
                                // Envía los datos a la capa encargada de guardarlos
                                onGuardar(
                                    datosActualizados,
                                    nuevaContrasena
                                )
                                contrasena = ""
                                confirmarContrasena = ""
                            }
                        }
                    ) {
                        Text(
                            text = "Guardar",
                            fontWeight = FontWeight.Bold,
                            color = morado
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarConfirmacion = false
                        }
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.Gray
                        )
                    }
                }
            )
        }

        if (mostrarResultado) {
            AlertDialog(
                onDismissRequest = {
                    mostrarResultado = false
                },
                title = {
                    Text(
                        text = "Cambios guardados",
                        color = morado,
                        fontWeight = FontWeight.Bold
                    )
                },

                text = {
                    Text(
                        text = "Los cambios de tu perfil se han guardado correctamente."
                    )
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarResultado = false
                        }
                    ) {
                        Text(
                            text = "Entendido",
                            color = morado
                        )
                    }
                }
            )
        }
    }
}


@Composable
private fun CampoPerfil(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    esContrasena: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {

    var mostrarContrasena by remember {
        mutableStateOf(false)
    }

    val morado = Color(0xFF381DC3)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(
                start = 4.dp,
                bottom = 4.dp
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(14.dp))
                .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                .padding(start = 16.dp, end = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = valor,
                    onValueChange = onValorChange,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    cursorBrush = SolidColor(morado),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (esContrasena) {
                            KeyboardType.Password
                        } else {
                            tipoTeclado
                        }
                    ),
                    visualTransformation = if (
                        esContrasena && !mostrarContrasena
                    ) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },

                    modifier = Modifier.weight(1f)
                )
                if (esContrasena) {
                    IconButton(
                        onClick = { mostrarContrasena = !mostrarContrasena },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (mostrarContrasena) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = if (mostrarContrasena) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            tint = morado,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun EditarPerfilPreview() {
    SIPPINAAplicacionTheme {
        EditarPerfil()
    }
}