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

@Composable
fun RegistroNuevoPerfil(
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
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
        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painter = painterResource(id = R.drawable.sipinnalogosinfondo),
            contentDescription = "Logo SIPINNA",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

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

