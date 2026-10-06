package com.example.sippina_aplicacion.model

import android.content.Context
import com.example.sippina_aplicacion.viewmodel.EstadoReporte
import com.google.gson.Gson

// Se utiliza este para guardar la parte de los borradores porque es una mala practica
// mandar datos incompletos a la base de datos
// Se tiene que guardar el borrador en el almacenamiento local del disposito
// Se tiene que obetner el borrador para que en la pantalla de Borradores pueda verse y poder continuar
// Se tiene que eliminar para el borrador de manera local si se elimina de la base de datos
class BorradorGuardado(context: Context) {
    private val prefs = context.getSharedPreferences("borradores_pref", Context.MODE_PRIVATE)
    private val gson = Gson()


    // En esta parte se guardan lo que es en el almacenamiento del telefono
    fun guardarBorrador(estado: EstadoReporte) {
        val json = gson.toJson(estado)
        prefs.edit().putString("borrador_actual", json).apply()
    }

    fun obtenerBorradorLocal(): EstadoReporte? {
        val json = prefs.getString("borrador_actual", null) ?: return null
        return try {
            gson.fromJson(json, EstadoReporte::class.java)

        } catch (e: Exception) {
            null

        }
    }

    fun eliminarBorradorLocal(){
        prefs.edit().remove("borrador_actual").apply()
    }

}