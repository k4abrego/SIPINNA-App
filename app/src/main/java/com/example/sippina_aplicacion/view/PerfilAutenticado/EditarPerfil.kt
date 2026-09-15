package com.example.sippina_aplicacion.view.PerfilAutenticado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun EditarPerfil(onAtras: () -> Unit = {}) {
    var nombreCompleto by remember { mutableStateOf("Roberto Martínez Román") }
    var correoElectronico by remember { mutableStateOf("roberto.martinezm@flopware.com") }
    var telefono by remember { mutableStateOf("55 1234 5678") }
    var contrasena by remember { mutableStateOf("1234567") }
    var confirmarContrasena by remember { mutableStateOf("1234567") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            )
            {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Descripción",
                        modifier = Modifier.size(22.dp)
                    )


                }
                Image(
                    painter = painterResource(id = R.drawable.sipinnalogosinfondo),
                    contentDescription = "LogoSippina",
                    modifier = Modifier.size(120.dp)
                )

            }
            Spacer(modifier = Modifier.height(28.dp))
            Box(modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center)
            {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .border(3.dp, Color.Blue, CircleShape)
                        .background(Color.Transparent, CircleShape),
                    contentAlignment = Alignment.Center
                )
                {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar de Usuario",
                        tint = Color.Blue,
                        modifier = Modifier.size(65.dp)
                    )

                }
                IconButton(
                    onClick = { /* TODO: Cambiar foto */ },
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-4).dp, y = 4.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Foto",
                        tint = Color.Blue,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            CampoPerfil(
                etiqueta = "Nombre completo",
                valor = nombreCompleto,
                onValorChange = { nombreCompleto = it }
            )

            Spacer(modifier = Modifier.height(14.dp))
            CampoPerfil(
                etiqueta = "Correo electrónico",
                valor = correoElectronico,
                onValorChange = { correoElectronico = it })


            Spacer(modifier = Modifier.height(14.dp))


            CampoPerfil(
                etiqueta = "Teléfono",
                valor = telefono,
                onValorChange = { telefono = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoPerfil(
                etiqueta = "Contraseña",
                valor = contrasena,
                onValorChange = { contrasena = it },
                esContrasena = true
            )
            Spacer(modifier = Modifier.height(14.dp))

            CampoPerfil(
                etiqueta = "Confirmar contraseña",
                valor = confirmarContrasena,
                onValorChange = { confirmarContrasena = it },
                esContrasena = true
            )
            Spacer(modifier = Modifier.height(14.dp))


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
    var mostrarContrasena by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            visualTransformation = if (esContrasena && !mostrarContrasena) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (esContrasena) {
                {
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        val AzulMorado = null
                        Icon(
                            imageVector = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Alternar visibilidad de contraseña",
                            tint = Color.Blue
                        )
                    }
                }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Perfil de Usuario - Diseño Exacto"
)
@Composable
fun PerfilUsuarioPreview() {
    SIPPINAAplicacionTheme {
        EditarPerfil()
    }
}