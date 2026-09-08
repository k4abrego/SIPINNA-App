package com.example.sippina_aplicacion.view.perfilusuario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme


@Composable
fun VistaPerfil1(
    modifier: Modifier = Modifier,
    onAtras: () -> Unit = {},
    onBorradores: () -> Unit = {},
    onMisReportes: () -> Unit = {},
    onAvisoPrivacidad: () -> Unit = {},
    onCrearCuenta: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize().background(Color(0xFFEAF4FC)).padding(horizontal = 24.dp, vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                onClick = onAtras,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF3D27C9)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Regresar",
                    modifier = Modifier.size(30.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.sipinnalogosinfondo),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(150.dp)
            )

            Spacer(
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Usuario",
                modifier = Modifier.size(150.dp),
                tint = Color(0xFF3D27C9)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Invitado",
                fontSize = 27.sp,
                color = Color(0xFF3D27C9),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(55.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Button(
                onClick = onBorradores,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF666666))
            ) {
                Text(
                    text = "Borradores",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = onMisReportes,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF666666)
                )
            ) {
                Text(
                    text = "Mis reportes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = onAvisoPrivacidad,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White,
                    contentColor = Color(0xFF666666)
                )
            ) {
                Text(text = "Aviso de privacidad",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(
                onClick = onCrearCuenta,
                modifier = modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF4F8BC9))
            ) {
                Text(
                    text = "Crear cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Vista Perfil 1"
)
@Composable
fun VistaPerfil1Preview() {
    SIPPINAAplicacionTheme {
        VistaPerfil1()
    }
}