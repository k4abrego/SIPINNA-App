package com.example.sippina_aplicacion.view.PerfilAutenticado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun EditarPerfil(
    onAtras: () -> Unit = {}
) {

    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)

    // Datos de ejemplo para la vista previa.
    var nombreCompleto by remember {
        mutableStateOf("Roberto Martínez Román")
    }

    var correoElectronico by remember {
        mutableStateOf("roberto.martinezm@flopware.com")
    }

    var telefono by remember {
        mutableStateOf("55 1234 5678")
    }

    var contrasena by remember {
        mutableStateOf("1234567")
    }

    var confirmarContrasena by remember {
        mutableStateOf("1234567")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ENCABEZADO

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
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
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(135.dp)
                        .border(width = 3.dp, color = morado, shape = CircleShape)
                        .background(color = Color.Transparent, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar de usuario",
                        tint = morado,
                        modifier = Modifier.size(78.dp)
                    )
                }

                // BOTÓN EDITAR FOTO

                IconButton(
                    onClick = {
                        // TODO: Implementar selección de foto de perfil.
                    },

                    modifier = Modifier.size(38.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-2).dp, y = 4.dp)
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

            Spacer(modifier = Modifier.height(14.dp))

            CampoPerfil(
                etiqueta = "Correo electrónico",
                valor = correoElectronico,
                onValorChange = {
                    correoElectronico = it
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoPerfil(
                etiqueta = "Teléfono",
                valor = telefono,
                onValorChange = {
                    telefono = it
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoPerfil(
                etiqueta = "Contraseña",
                valor = contrasena,
                onValorChange = {
                    contrasena = it
                },
                esContrasena = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoPerfil(
                etiqueta = "Confirmar contraseña",
                valor = confirmarContrasena,
                onValorChange = {
                    confirmarContrasena = it
                },
                esContrasena = true
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CampoPerfil(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    esContrasena: Boolean = false
) {

    var mostrarContrasena by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = Color.Gray,

            modifier = Modifier.padding(
                start = 2.dp,
                bottom = 2.dp
            )
        )

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            visualTransformation = if (
                esContrasena && !mostrarContrasena
            ) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = if (esContrasena) {
                {
                    IconButton(
                        onClick = {
                            mostrarContrasena = !mostrarContrasena
                        }
                    ) {

                        Icon(
                            imageVector = if (mostrarContrasena) { Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },

                            contentDescription = if (mostrarContrasena) { "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            }, tint = Color(0xFF381DC3)
                        )
                    }
                }
            } else {
                null
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF381DC3),
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Editar perfil"
)
@Composable
fun EditarPerfilPreview() {

    SIPPINAAplicacionTheme {
        EditarPerfil()
    }
}