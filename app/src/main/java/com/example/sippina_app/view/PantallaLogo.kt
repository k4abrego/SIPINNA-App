package com.example.sippina_app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material3.Text


@Composable
fun PantallaLogo(navController: NavHostController)
{
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEBF3FC)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SIPPINA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4F46E5)
        )

    }
}
