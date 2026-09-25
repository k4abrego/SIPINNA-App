//package com.example.sippina_aplicacion.view.PantallasReporte.Evidencia
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.core.content.FileProvider
//import java.io.File
//
//@Composable
//fun CameraIntentScreen() {
//    val context = LocalContext.current
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//// Crear una Uri temporal para guardar la foto
//    val tempUri = remember {
//        val directory = File(context.cacheDir, "images")
//        directory.mkdirs()
//        val file = File.createTempFile("captured_image_", ".jpg", directory)
//        FileProvider.getUriForFile(
//            context, "${context.packageName}.fileprovider", file)
//    }
//// Definir el contrato para tomar la foto
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture()
//    ) { success ->
//        if (success) {
//            imageUri = tempUri // La foto se guardó correctamente en tempUri
//        }
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment =
//            Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = { // Lanzar la cámara pasando la Uri temporal
//            cameraLauncher.launch(tempUri)
//        }) {
//            Text("Abrir cámara")
//        }
//        if (imageUri != null) {
//// Mostrar la imagen con Coil o AsyncImage
//            AsyncImage(model = tempUri, contentDescription = null)
//        }
//    }
//}