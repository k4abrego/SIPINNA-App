package com.example.sippina_aplicacion

import com.example.sippina_aplicacion.model.DatosMenor
import com.example.sippina_aplicacion.model.EstadoReporte
import com.example.sippina_aplicacion.model.Usuario
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ModelosDominioTest {

    @Test
    fun `Usuario get id debe retornar string del idUsuario`() {
        val usuario = Usuario(idUsuario = 42, nombre = "Juan", apellidos = "Perez")
        assertEquals("42", usuario.id)
    }

    @Test
    fun `Usuario nombreCompletoFormateado debe estructurarse en dos lineas`() {
        val usuario = Usuario(idUsuario = 1, nombre = "Roberto", apellidos = "Martinez")
        assertEquals("Roberto\nMartinez", usuario.nombreCompletoFormateado)
    }

    @Test
    fun `EstadoReporte propiedades computadas deben evaluar correctamente`() {
        val menor1 = DatosMenor(rangoEdad = "6-11", genero = "Femenino")
        val estadoAnonimo = EstadoReporte(
            opcionPrivacidad = "anonimo",
            menores = listOf(menor1),
            indiceMenorActual = 0
        )

        assertTrue(estadoAnonimo.esAnonimo)
        assertEquals(menor1, estadoAnonimo.menorActual)

        val estadoSeguimiento = EstadoReporte(
            opcionPrivacidad = "actualizaciones",
            menores = emptyList(),
            indiceMenorActual = 5
        )

        assertFalse(estadoSeguimiento.esAnonimo)
        assertEquals("", estadoSeguimiento.menorActual.genero)
    }
}