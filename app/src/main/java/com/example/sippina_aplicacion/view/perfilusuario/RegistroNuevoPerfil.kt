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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults

@Composable
fun RegistroNuevoPerfil(
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {},
) {

    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var numeroMovil by remember { mutableStateOf("") }

    val fondoDegradado = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD3E7FF),
            Color(0xFFBCA7E8)
        )
    )
    Column(
        modifier = modifier.fillMaxSize().background(fondoDegradado).padding(horizontal = 20.dp, vertical = 20.dp),
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
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        CampoRegistro(
            valor = nombre,
            onValorCambio = { nombre = it },
            texto = "Nombre(s)"
        )

        Spacer(modifier = Modifier.height(15.dp))

        CampoRegistro(
            valor = apellidoPaterno,
            onValorCambio = { apellidoPaterno = it },
            texto = "Apellido Paterno"
        )

        Spacer(modifier = Modifier.height(15.dp))

        CampoRegistro(
            valor = apellidoMaterno,
            onValorCambio = { apellidoMaterno = it },
            texto = "Apellido Materno"
        )

        Spacer(modifier = Modifier.height(15.dp))

        CampoRegistro(
            valor = correo,
            onValorCambio = { correo = it },
            texto = "Correo electrónico",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(15.dp))

        CampoRegistro(
            valor = numeroMovil,
            onValorCambio = { numeroMovil = it },
            texto = "Número Móvil",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = onSiguiente,
            modifier = Modifier.fillMaxWidth(0.65f).height(48.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3D20C9)
            )
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun CampoRegistro(
    valor: String,
    onValorCambio: (String) -> Unit,
    texto: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    TextField(
        value = valor,
        onValueChange = onValorCambio,
        placeholder = {
            Text(
                text = texto,
                color = Color.Gray,
                fontSize = 15.sp
            )
        },
        modifier = Modifier.width(300.dp).height(50.dp),
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegistroNuevoPerfilPreview() {
    SIPPINAAplicacionTheme {
        RegistroNuevoPerfil()
    }
}
