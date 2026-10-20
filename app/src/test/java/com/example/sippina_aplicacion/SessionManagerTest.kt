package com.example.sippina_aplicacion

import com.example.sippina_aplicacion.model.Autenticacion.SessionManager
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class SessionManagerTest {

    @Before
    fun setUp() {
        SessionManager.cerrarSesion()
    }

    @Test
    fun `guardarSesion debe persistir el token en memoria`() {
        val tokenPrueba = "jwt_token_12345"
        SessionManager.guardarSesion(tokenPrueba)

        assertEquals(tokenPrueba, SessionManager.obtenerToken())
    }

    @Test
    fun `cerrarSesion debe eliminar el token de memoria`() {
        SessionManager.guardarSesion("jwt_token_12345")
        SessionManager.cerrarSesion()

        assertNull(SessionManager.obtenerToken())
    }
}