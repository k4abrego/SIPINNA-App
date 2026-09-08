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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun VistaPerfil2(
    modifier: Modifier = Modifier,
    onAtras: () -> Unit = {},
    onBorradores: () -> Unit = {},
    onMisReportes: () -> Unit = {},
    onAvisoPrivacidad: () -> Unit = {},
    onEliminarCuenta: () -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 15.dp).background(Color(0xFFEAF4FC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color(0xFF3D20C9),
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(
                    id = R.drawable.imagensippina
                ),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.size(50.dp))
        }

        Spacer(modifier = Modifier.height(45.dp))

        IconButton(
            onClick = { },
            modifier = Modifier.size(130.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Perfil",
                tint = Color(0xFF3D20C9),
                modifier = Modifier.size(140.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Ariel\n Martínez Serrato",
            color = Color(0xFF3D20C9),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

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
                    fontSize = 20.sp,
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
                    fontSize = 20.sp,
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(
                onClick = onEliminarCuenta,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar cuenta",
                    modifier = Modifier.size(22.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "Eliminar cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun VistaPerfil2Preview() {
    SIPPINAAplicacionTheme {
        VistaPerfil2()
    }
}
