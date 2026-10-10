package com.example.sippina_aplicacion.model

import android.content.Context
import com.google.gson.Gson

/**
 * Gestor de persistencia local para el almacenamiento temporal de reportes inconclusos (borradores).
 *
 * Previene el envío de información incompleta a los servidores remotos guardando el progreso actual
 * del formulario de reporte en el almacenamiento privado del dispositivo mediante [android.content.SharedPreferences]
 * y serialización JSON con [Gson].
 *
 * @param context Contexto de Android [Context] utilizado para inicializar las preferencias compartidas.
 */
class BorradorGuardado(context: Context) {
    private val prefs = context.getSharedPreferences("borradores_pref", Context.MODE_PRIVATE)
    private val gson = Gson()


    /**
     * Serializa y guarda la información actual del formulario en el almacenamiento local del dispositivo.
     *
     * La operación de escritura se ejecuta de forma asíncrona mediante [android.content.SharedPreferences.Editor.apply].
     *
     * @param estado Instancia de [EstadoReporte] que contiene el progreso y los datos capturados del reporte.
     */
    fun guardarBorrador(estado: EstadoReporte) {
        val json = gson.toJson(estado)
        prefs.edit().putString("borrador_actual", json).apply()
    }

    /**
     * Recupera y deserializa el borrador de reporte guardado previamente en el dispositivo.
     *
     * @return La instancia de [EstadoReporte] con la información del borrador si existe y es válida;
     * `null` si no existe un borrador previo o si ocurre un error durante el proceso de deserialización.
     */
    fun obtenerBorradorLocal(): EstadoReporte? {
        val json = prefs.getString("borrador_actual", null) ?: return null
        return try {
            gson.fromJson(json, EstadoReporte::class.java)

        } catch (e: Exception) {
            null

        }
    }

    /**
     * Purga y elimina de forma permanente el borrador almacenado localmente en el dispositivo.
     */
    fun eliminarBorradorLocal(){
        prefs.edit().remove("borrador_actual").apply()
    }

}
