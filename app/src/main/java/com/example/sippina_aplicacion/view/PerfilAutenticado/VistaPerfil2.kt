package com.example.sippina_aplicacion.view.PerfilAutenticado

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog

/**
 * Pantalla principal de perfil para usuarios autenticados en la plataforma SIPINNA.
 *
 * Ofrece acceso rápido a la edición de información del usuario ([onEditarPerfil]), la gestión de borradores ([onBorradores]),
 * consulta de historial de reportes ([onMisReportes]) y
 * el Aviso de Privacidad ([onAvisoPrivacidad]).
 *
 * Además, incluye la acción de cierre de sesión mediante un diálogo de confirmación personalizado ([Dialog])
 * de advertencia e indicador de carga ([CircularProgressIndicator]) mientras se obtienen los datos del perfil.
 *
 * @param modifier [Modifier] opcional para personalizar el diseño del contenedor principal.
 * @param nombreUsuario Nombre del usuario autenticado que se mostrará en el encabezado.
 * @param cargando Flag que indica si la consulta del perfil del usuario está en progreso.
 * @param onAtras Callback invocado al presionar el botón de regresar.
 * @param onBorradores Callback para navegar hacia la vista de gestión de borradores guardados localmente.
 * @param onMisReportes Callback para navegar a la lista de reportes enviados por el usuario.
 * @param onAvisoPrivacidad Callback para abrir el documento del Aviso de Privacidad.
 * @param onCerrarSesion Callback invocado tras confirmar el cierre de sesión.
 * @param onEditarPerfil Callback para abrir el formulario de actualización de datos de perfil.
 */
@Composable
fun VistaPerfil2(
    modifier: Modifier = Modifier,
    nombreUsuario:String = "Usuario",
    cargando : Boolean = false,
    onAtras: () -> Unit = {},
    onBorradores: () -> Unit = {},
    onMisReportes: () -> Unit = {},
    onAvisoPrivacidad: () -> Unit = {},
    onCerrarSesion: () -> Unit = {},
    onEditarPerfil: () -> Unit = {}
) {
    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)
    var mostrarAlertaCerrarSesion by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(fondo)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = fondo,
                        contentColor = Color(0xFF3D27C9)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logorieti),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(
                modifier = Modifier.size(145.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .border(width = 3.dp, color = morado, shape = CircleShape)
                        .clickable(onClick = onEditarPerfil),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = morado,
                        modifier = Modifier.size(90.dp)
                    )
                }
                IconButton(
                    onClick = onEditarPerfil,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-1).dp, y = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar perfil",
                        tint = morado,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            if(cargando){
                CircularProgressIndicator(color = morado, modifier = Modifier.padding(16.dp))
            }
            else {
                Text(
                    text = nombreUsuario.ifBlank { "Usuario" },
                    color = Color(0xFF3D20C9),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center

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
                    onClick = onMisReportes,
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, contentColor = Color(0xFF666666)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
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
                    onClick = { mostrarAlertaCerrarSesion = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Red),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Cerrar sesión",
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "Cerrar sesión",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
        if (mostrarAlertaCerrarSesion){
            Dialog(
                onDismissRequest = {
                    mostrarAlertaCerrarSesion = false
                }
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFC62828),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¿Estás seguro de que quieres cerrar sesión?",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 27.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Podrás volver a iniciar sesión con tu cuenta.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = {
                                    mostrarAlertaCerrarSesion = false
                                    // Solicita el cierre de sesión a través del callback existente.
                                    onCerrarSesion()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(width = 1.dp, color = Color.White),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color(0xFFB90000),
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = "Cerrar sesión",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Button(
                                onClick = { mostrarAlertaCerrarSesion = false },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = morado,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = "Volver",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


/**
 * Previsualización de la pantalla [VistaPerfil2] en el entorno de desarrollo de Android Studio.
 */
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun VistaPerfil2Preview() {
    SIPPINAAplicacionTheme {
        VistaPerfil2(nombreUsuario = "Ariel Martinez")
    }
}
