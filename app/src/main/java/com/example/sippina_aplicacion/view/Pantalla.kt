package com.example.sippina_aplicacion.view

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase sellada que define el catálogo y las rutas de navegación de las pantallas en la aplicación SIPINNA.
 *
 * Cada pantalla está modelada como un objeto de datos ([data object]) que hereda de [Pantalla],
 * estandarizando la ruta de navegación, su etiqueta legible y un icono opcional.
 *
 * @property ruta Identificador único de la ruta utilizado en el grafo de navegación.
 * @property etiqueta Título descriptivo o nombre de la pantalla.
 * @property icono Icono vectorial [ImageVector] opcional asociado a la pantalla.
 */
sealed class Pantalla(
    val ruta: String,
    val etiqueta: String = "",
    val icono: ImageVector? = null
) {
    /**
     * Contenedor de constantes de rutas de navegación y listados globales de pantallas.
     */
    companion object {
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
            PantallaInicioSesion, // <-- Agregado a la lista global
            VistaPerfil2,
            Borradores,
            MisReportes,
            AvisoPrivacidad,
            EditarPerfil,
            Notificaciones,
            BuscarReporte,
            BuscarReporteExis,
            DetallesReporte,
            ConoceSIPINNA,
            QueSituacionesReportar
        )

        const val RUTA_PANTALLA_CARGA = "PantallaCarga"
        const val RUTA_PANTALLA_INICIO = "PantallaInicio"
        const val RUTA_DONDE_OCURRIO = "DondeOcurrio"
        const val RUTA_DETALLES = "Detalles"
        const val RUTA_DETALLES2 = "Detalles2"
        const val RUTA_EVIDENCIA = "Evidencia"
        const val RUTA_PRIVACIDAD_SEGUIMIENTO = "PrivacidadSeguimiento"
        const val RUTA_REPORTE_GENERADO = "ReporteGenerado"
        const val RUTA_VISTA_PERFIL1 = "VistaPerfil1"
        const val RUTA_REGISTRO_NUEVO_PERFIL = "RegistroNuevoPerfil"
        const val RUTA_PANTALLA_INICIO_SESION = "PantallaInicioSesion" // <-- Constante agregada
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

    /** Pantalla de carga inicial (Splash Screen). */
    data object PantallaCarga : Pantalla(RUTA_PANTALLA_CARGA, "Carga")
    /** Pantalla principal de inicio. */
    data object PantallaInicio : Pantalla(RUTA_PANTALLA_INICIO, "Inicio")
    /** Paso 1 del reporte: Selección de ubicación geográfica. */
    data object DondeOcurrio : Pantalla(RUTA_DONDE_OCURRIO, "Ubicación")
    /** Paso 2 del reporte: Detalles del incidente y descripción de hechos. */
    data object Detalles : Pantalla(RUTA_DETALLES, "Detalles 1")
    /** Paso 3 del reporte: Descripción física y datos de los menores involucrados. */
    data object Detalles2 : Pantalla(RUTA_DETALLES2, "Detalles 2")
    /** Paso 4 del reporte: Adjuntar archivos de evidencia multimedia. */
    data object Evidencia : Pantalla(RUTA_EVIDENCIA, "Evidencia")
    /** Paso 5 del reporte: Opciones de privacidad y envío del reporte. */
    data object PrivacidadSeguimiento : Pantalla(RUTA_PRIVACIDAD_SEGUIMIENTO, "Privacidad")
    /** Pantalla de confirmación y obtención de folio tras un envío exitoso. */
    data object ReporteGenerado : Pantalla(RUTA_REPORTE_GENERADO, "Éxito")
    /** Pantalla de perfil para usuarios no autenticados (Invitado). */
    data object VistaPerfil1 : Pantalla(RUTA_VISTA_PERFIL1, "Perfil")
    /** Pantalla de registro de nueva cuenta de usuario. */
    data object RegistroNuevoPerfil : Pantalla(RUTA_REGISTRO_NUEVO_PERFIL, "Crear cuenta")
    /** Pantalla de inicio de sesión para usuarios existentes. */
    data object PantallaInicioSesion : Pantalla(RUTA_PANTALLA_INICIO_SESION, "Iniciar sesión") // <-- Objeto agregado
    /** Pantalla de perfil para usuarios autenticados. */
    data object VistaPerfil2 : Pantalla(RUTA_VISTA_PERFIL2, "Perfil")
    /** Pantalla de gestión de borradores de reportes guardados localmente. */
    data object Borradores : Pantalla(RUTA_BORRADORES, "Borradores")
    /** Pantalla de historial de reportes enviados por el usuario. */
    data object MisReportes : Pantalla(RUTA_MIS_REPORTES, "Mis reportes")
    /** Pantalla con las políticas y aviso de privacidad de SIPINNA. */
    data object AvisoPrivacidad : Pantalla(RUTA_AVISO_PRIVACIDAD, "Aviso de privacidad")
    /** Pantalla del centro de notificaciones de la cuenta. */
    data object Notificaciones : Pantalla(RUTA_NOTIFICACIONES, "Notificaciones")
    /** Pantalla de búsqueda general de reportes. */
    data object BuscarReporte : Pantalla(RUTA_BUSCAR_REPORTE, "Buscar reporte")
    /** Pantalla de consulta detallada del estatus de un reporte. */
    data object DetallesReporte : Pantalla(RUTA_DETALLE_REPORTE, "Detalles del reporte")
    /** Pantalla informativa sobre la institución SIPINNA. */
    data object ConoceSIPINNA : Pantalla(RUTA_CONOCE_SIPINNA, "Conoce SIPINNA")
    /** Pantalla informativa sobre qué situaciones proceden para reporte. */
    data object QueSituacionesReportar : Pantalla(RUTA_QUE_SITUACIONES_REPORTAR, "Qué situaciones reportar")
    /** Pantalla de edición de datos de perfil y contraseña. */
    data object EditarPerfil : Pantalla(RUTA_EDITAR_PERFIL, "EditarPerfil")
    /** Pantalla de búsqueda de un reporte existente mediante folio y código privado. */
    data object BuscarReporteExis : Pantalla("BuscarReporteExis", "Buscar reporte existente")
}