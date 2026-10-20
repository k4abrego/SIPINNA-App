package com.example.sippina_aplicacion

import com.example.sippina_aplicacion.model.Autenticacion.AuthRepository
import com.example.sippina_aplicacion.model.Autenticacion.RegistroResponse
import com.example.sippina_aplicacion.model.Autenticacion.UsuarioResponse
import com.example.sippina_aplicacion.viewmodel.AuthVM
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthVMTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = mockk(relaxed = true)
    private lateinit var authVM: AuthVM

    @Before
    fun setUp() {
        authVM = AuthVM(repository)
    }

    @Test
    fun `iniciarSesion exitoso debe actualizar estado a autenticado y consultar perfil`() = runTest {
        val correo = "usuario@sipinna.gob.mx"
        val password = "Password123"
        val token = "jwt_token_sample"
        val perfil = UsuarioResponse(userId = 5L, nombre = "Laura", apellido = "Hernandez", telefono = "5559876543")

        coEvery { repository.iniciarSesion(correo, password) } returns Result.success(token)
        coEvery { repository.obtenerPerfil(token, correo) } returns Result.success(perfil)

        authVM.iniciarSesion(correo, password)

        val state = authVM.state.value
        assertTrue(state.autenticado)
        assertEquals(token, state.token)
        assertEquals("5", state.usuarioId)
        assertEquals("Laura", state.nombre)
        assertEquals("Hernandez", state.apellidos)
        assertFalse(state.cargando)
        assertNull(state.error)
    }

    @Test
    fun `iniciarSesion fallido debe actualizar mensaje de error y autenticado en false`() = runTest {
        val correo = "error@sipinna.gob.mx"
        coEvery { repository.iniciarSesion(any(), any()) } returns Result.failure(Exception("Credenciales inválidas"))

        authVM.iniciarSesion(correo, "badpass")

        val state = authVM.state.value
        assertFalse(state.autenticado)
        assertEquals("Credenciales inválidas", state.error)
        assertFalse(state.cargando)
    }

    @Test
    fun `registrarUsuario exitoso debe encadenar inicio de sesion`() = runTest {
        val regResp = RegistroResponse(userId = 15L, mensaje = "Registrado")
        coEvery { repository.registrarUsuario(any(), any(), any(), any(), any()) } returns Result.success(regResp)
        coEvery { repository.iniciarSesion(any(), any()) } returns Result.success("fake_token")
        coEvery { repository.obtenerPerfil(any(), any()) } returns Result.success(UsuarioResponse(userId = 15L, nombre = "Mario"))

        authVM.registrarUsuario("mario@sipinna.gob.mx", "pass", "Mario", "Bros", null)

        val state = authVM.state.value
        assertTrue(state.autenticado)
        assertEquals("15", state.usuarioId)
        coVerify(exactly = 1) { repository.iniciarSesion("mario@sipinna.gob.mx", "pass") }
    }

    @Test
    fun `cerrarSesion debe reiniciar el estado local a valores limpios`() = runTest {
        coEvery { repository.iniciarSesion(any(), any()) } returns Result.success("token_123")
        coEvery { repository.obtenerPerfil(any(), any()) } returns Result.success(UsuarioResponse(userId = 1L))
        authVM.iniciarSesion("test@test.com", "pass")

        assertTrue(authVM.state.value.autenticado)

        authVM.cerrarSesion()

        val state = authVM.state.value
        assertFalse(state.autenticado)
        assertEquals("", state.token)
        assertNull(state.usuarioId)
    }

    @Test
    fun `limpiarMensajes debe eliminar error y mensajeExito del estado`() = runTest {
        coEvery { repository.iniciarSesion(any(), any()) } returns Result.failure(Exception("Error temporal"))
        authVM.iniciarSesion("a@b.com", "123")
        assertNotNull(authVM.state.value.error)

        authVM.limpiarMensajes()

        assertNull(authVM.state.value.error)
        assertNull(authVM.state.value.mensajeExito)
    }
}