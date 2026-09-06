package com.example.sippina_aplicacion.view

import androidx.compose.ui.graphics.vector.ImageVector


sealed class Pantalla(
    val ruta:String,
    val etiqueta:String = "",
    val icono: ImageVector? = null
)
{
    // Rutas
    companion object{

        val listaPantallas = listOf(
            PantallaCarga,
            PantallaInicio,
            PantallaReporte
        )
        const val  RUTA_PANTALLA_CARGA = "PantallaCarga"
        const val  RUTA_PANTALLA_INICIO = "PantallaInicio"
        const val RUTA_PANTALLA_REPORTE = "DondeOcurrio"

    }
    data object PantallaCarga: Pantalla(RUTA_PANTALLA_CARGA,"Carga")
    data object PantallaInicio: Pantalla(RUTA_PANTALLA_INICIO, "Inicio")
    data object PantallaReporte:Pantalla(RUTA_PANTALLA_REPORTE, "Reporte")


}