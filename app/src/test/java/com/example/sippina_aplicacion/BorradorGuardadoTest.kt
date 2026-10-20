package com.example.sippina_aplicacion

import android.content.Context
import android.content.SharedPreferences
import com.example.sippina_aplicacion.model.BorradorGuardado
import com.example.sippina_aplicacion.model.EstadoReporte
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BorradorGuardadoTest {

    private val context: Context = mockk()
    private val prefs: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)

    private lateinit var borradorGuardado: BorradorGuardado

    @Before
    fun setUp() {
        every { context.getSharedPreferences("borradores_pref", Context.MODE_PRIVATE) } returns prefs
        every { prefs.edit() } returns editor

        // CORRECCIÓN: Hacemos que putString y remove devuelvan el mismo editor encadenado
        every { editor.putString(any(), any()) } returns editor
        every { editor.remove(any()) } returns editor

        borradorGuardado = BorradorGuardado(context)
    }

    @Test
    fun `guardarBorrador debe serializar el estado y llamar apply`() {
        val estado = EstadoReporte(pasoActual = 2, descripcionHechos = "Test de hechos")

        borradorGuardado.guardarBorrador(estado)

        verify(exactly = 1) {
            editor.putString("borrador_actual", match { it.contains("Test de hechos") && it.contains("\"pasoActual\":2") })
            editor.apply()
        }
    }

    @Test
    fun `obtenerBorradorLocal cuando existe json valido debe deserializar correctamente`() {
        val jsonGuardado = """
            {
               "pasoActual": 3,
               "opcionUbicacion": "mapa",
               "latitud": 19.55,
               "longitud": -99.28,
               "direccion": "Av Central",
               "descripcionHechos": "Situacion de prueba"
            }
        """.trimIndent()

        every { prefs.getString("borrador_actual", null) } returns jsonGuardado

        val resultado = borradorGuardado.obtenerBorradorLocal()

        assertNotNull(resultado)
        assertEquals(3, resultado?.pasoActual)
        assertEquals(19.55, resultado?.latitud ?: 0.0, 0.001)
        assertEquals("Situacion de prueba", resultado?.descripcionHechos)
    }

    @Test
    fun `obtenerBorradorLocal cuando json es corrupto o nulo debe retornar null`() {
        every { prefs.getString("borrador_actual", null) } returns null
        assertNull(borradorGuardado.obtenerBorradorLocal())

        every { prefs.getString("borrador_actual", null) } returns "{ JSON_CORRUPTO //"
        assertNull(borradorGuardado.obtenerBorradorLocal())
    }

    @Test
    fun `eliminarBorradorLocal debe remover la clave y aplicar`() {
        borradorGuardado.eliminarBorradorLocal()

        verify(exactly = 1) {
            editor.remove("borrador_actual")
            editor.apply()
        }
    }
}