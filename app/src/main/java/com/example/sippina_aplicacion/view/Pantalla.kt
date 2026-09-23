package com.example.sippina_aplicacion.view

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sippina_aplicacion.view.PerfilAutenticado.EditarPerfil


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
            ReporteGenerado,
            VistaPerfil1,
            RegistroNuevoPerfil,
            VistaPerfil2,
            Borradores,
            MisReportes,
            AvisoPrivacidad,
            EditarPerfil,
            Notificaciones,
            BuscarReporte,
            DetalleReporte,
            ConoceSIPINNA,
            QueSituacionesReportar
        )
        const val  RUTA_PANTALLA_CARGA = "PantallaCarga"
        const val  RUTA_PANTALLA_INICIO = "PantallaInicio"
        const val RUTA_DONDE_OCURRIO = "DondeOcurrio"
        const val RUTA_DETALLES = "Detalles"
        const val RUTA_DETALLES2 = "Detalles2"
        const val RUTA_EVIDENCIA = "Evidencia"
        const val RUTA_PRIVACIDAD_SEGUIMIENTO = "PrivacidadSeguimiento"
        const val RUTA_REPORTE_GENERADO = "ReporteGenerado"
        const val RUTA_VISTA_PERFIL1 = "VistaPerfil1"
        const val RUTA_REGISTRO_NUEVO_PERFIL = "RegistroNuevoPerfil"
        const val RUTA_VISTA_PERFIL2 = "VistaPerfil2"
        const val RUTA_BORRADORES = "Borradores"
        const val RUTA_MIS_REPORTES = "MisReportes"
        const val RUTA_AVISO_PRIVACIDAD = "AvisoPrivacidad"
        const val RUTA_EDITAR_PERFIL = "EditarPerfil"
        const val RUTA_NOTIFICACIONES = "Notificaciones"
        const val RUTA_BUSCAR_REPORTE = "BuscarReporte"
        const val RUTA_DETALLE_REPORTE = "DetalleReporte"
        const val RUTA_CONOCE_SIPINNA = "ConoceSIPINNA"
        const val RUTA_QUE_SITUACIONES_REPORTAR = "QueSituacionesReportar"
    }

    data object PantallaCarga: Pantalla(RUTA_PANTALLA_CARGA,"Carga")
    data object PantallaInicio: Pantalla(RUTA_PANTALLA_INICIO, "Inicio")
    data object DondeOcurrio : Pantalla(RUTA_DONDE_OCURRIO, "Ubicación")
    data object Detalles : Pantalla(RUTA_DETALLES, "Detalles 1")
    data object Detalles2 : Pantalla(RUTA_DETALLES2, "Detalles 2")
    data object Evidencia : Pantalla(RUTA_EVIDENCIA, "Evidencia")
    data object PrivacidadSeguimiento : Pantalla(RUTA_PRIVACIDAD_SEGUIMIENTO, "Privacidad")
    data object ReporteGenerado : Pantalla(RUTA_REPORTE_GENERADO, "Éxito")
    data object VistaPerfil1 : Pantalla(RUTA_VISTA_PERFIL1, "Perfil")
    data object RegistroNuevoPerfil : Pantalla(RUTA_REGISTRO_NUEVO_PERFIL, "Crear cuenta")
    data object VistaPerfil2 : Pantalla(RUTA_VISTA_PERFIL2, "Perfil")
    data object Borradores : Pantalla(RUTA_BORRADORES, "Borradores")
    data object MisReportes : Pantalla(RUTA_MIS_REPORTES,"Mis reportes")
    data object AvisoPrivacidad : Pantalla(RUTA_AVISO_PRIVACIDAD,"Aviso de privacidad")
    data object Notificaciones : Pantalla(RUTA_NOTIFICACIONES, "Notificaciones")
    data object BuscarReporte : Pantalla(RUTA_BUSCAR_REPORTE, "Buscar reporte")
    data object DetalleReporte : Pantalla(RUTA_DETALLE_REPORTE, "Detalle del reporte")
    data object ConoceSIPINNA : Pantalla(RUTA_CONOCE_SIPINNA, "Conoce SIPINNA")
    data object QueSituacionesReportar : Pantalla(RUTA_QUE_SITUACIONES_REPORTAR, "Qué situaciones reportar")
    data object EditarPerfil : Pantalla(RUTA_EDITAR_PERFIL, "EditarPerfil")

}