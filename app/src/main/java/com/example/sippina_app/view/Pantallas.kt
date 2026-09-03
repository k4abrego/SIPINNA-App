package com.example.sippina_app.view

sealed class Pantallas(val ruta: String) {
    companion object {
        // Pantallas principales
        const val RUTA_LOGO = "Logo"
        const val RUTA_PANTALLA_INICIO = "PantallaInicio"

        // Pantallas del flujo de reporte
        const val RUTA_REPORTE_UBICACION = "ReporteUbicacion"
        const val RUTA_REPORTE_DETALLES = "ReporteDetalles"
        const val RUTA_REPORTE_EVIDENCIA = "ReporteEvidencia"
        const val RUTA_REPORTE_PRIVACIDAD = "ReportePrivacidad"
        const val RUTA_REPORTE_EXITOSO = "ReporteExistoso"


    }

    object Logo : Pantallas(RUTA_LOGO)
    object Inicio : Pantallas(RUTA_PANTALLA_INICIO)
    object ReporteUbicacion : Pantallas(RUTA_REPORTE_UBICACION)
    object ReporteDetalles : Pantallas(RUTA_REPORTE_DETALLES)
    object ReporteEvidencia : Pantallas(RUTA_REPORTE_EVIDENCIA)
    object ReportePrivacidad : Pantallas(RUTA_REPORTE_PRIVACIDAD)
    object ReporteExitoso : Pantallas(RUTA_REPORTE_EXITOSO)

}