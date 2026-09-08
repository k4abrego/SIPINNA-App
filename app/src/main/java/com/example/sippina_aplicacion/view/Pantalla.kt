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
            DondeOcurrio,
            Detalles,
            Detalles2,
            Evidencia,
            PrivacidadSeguimiento,
            ReporteGenerado
        )
        const val  RUTA_PANTALLA_CARGA = "PantallaCarga"
        const val  RUTA_PANTALLA_INICIO = "PantallaInicio"
        const val RUTA_DONDE_OCURRIO = "DondeOcurrio"
        const val RUTA_DETALLES = "Detalles"
        const val RUTA_DETALLES2 = "Detalles2"
        const val RUTA_EVIDENCIA = "Evidencia"
        const val RUTA_PRIVACIDAD_SEGUIMIENTO = "PrivacidadSeguimiento"
        const val RUTA_REPORTE_GENERADO = "ReporteGenerado"

    }
    data object PantallaCarga: Pantalla(RUTA_PANTALLA_CARGA,"Carga")
    data object PantallaInicio: Pantalla(RUTA_PANTALLA_INICIO, "Inicio")
    // Flujo completo de reporte
    data object DondeOcurrio : Pantalla(RUTA_DONDE_OCURRIO, "Ubicación")
    data object Detalles : Pantalla(RUTA_DETALLES, "Detalles 1")
    data object Detalles2 : Pantalla(RUTA_DETALLES2, "Detalles 2")
    data object Evidencia : Pantalla(RUTA_EVIDENCIA, "Evidencia")
    data object PrivacidadSeguimiento : Pantalla(RUTA_PRIVACIDAD_SEGUIMIENTO, "Privacidad")
    data object ReporteGenerado : Pantalla(RUTA_REPORTE_GENERADO, "Éxito")



}