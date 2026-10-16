package com.example.sippina_aplicacion.view.SinAutenticacion

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

/**
 * Pantalla de perfil para usuarios no autenticados (modo Invitado).
 *
 * Muestra una interfaz simplificada que permite a los usuarios sin sesión activa acceder a funciones públicas
 * como la consulta de borradores locales ([onBorradores]), la búsqueda de reportes existentes ([onBuscarReporte]),
 * la lectura del aviso de privacidad ([onAvisoPrivacidad]) y accesos directos para la creación de una cuenta
 * ([onCrearCuenta]) o inicio de sesión ([onIniciarSesion]).
 *
 * @param modifier [Modifier] opcional para el contenedor principal de la vista.
 * @param onAtras Callback invocado al presionar el botón de regresar.
 * @param onBorradores Callback para acceder a la lista de borradores guardados localmente.
 * @param onBuscarReporte Callback para consultar el estatus de un reporte mediante folio y código privado.
 * @param onAvisoPrivacidad Callback para abrir el documento del Aviso de Privacidad.
 * @param onCrearCuenta Callback para navegar al formulario de registro de un nuevo perfil.
 * @param onIniciarSesion Callback para navegar a la pantalla de inicio de sesión.
 */
@Composable
fun VistaPerfil1(
    modifier: Modifier = Modifier,
    onAtras: () -> Unit = {},
    onBorradores: () -> Unit = {},
    onBuscarReporte: () -> Unit = {},
    onAvisoPrivacidad: () -> Unit = {},
    onCrearCuenta: () -> Unit = {},
    onIniciarSesion: () -> Unit ={ }
) {
    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)

    Box(
        modifier = modifier
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
                        containerColor = fondo,
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
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .size(130.dp)
                    .border(width = 3.dp, color = morado, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil de invitado",
                    tint = morado,
                    modifier = Modifier.size(75.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Invitado",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                Button(onClick = onBorradores,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF666666)
                    )
                ) {
                    Text(
                        text = "Borradores",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = onBuscarReporte,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF666666)
                    )
                ) {
                    Text(
                        text = "Buscar reporte",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(onClick = onAvisoPrivacidad,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF666666)
                    )
                ) {
                    Text(
                        text = "Aviso de privacidad",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                OutlinedButton(
                    onClick = onCrearCuenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(width = 1.dp, color = morado),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = fondo,
                        contentColor = morado
                    )
                ) {
                    Text(
                        text = "Crear cuenta",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}


/**
 * Previsualización de la pantalla [VistaPerfil1] en el entorno de desarrollo de Android Studio.
 */
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Perfil de invitado"
)
@Composable
fun VistaPerfil1Preview() {
    SIPPINAAplicacionTheme {
        VistaPerfil1()
    }
}
