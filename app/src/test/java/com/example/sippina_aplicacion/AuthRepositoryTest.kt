package com.example.sippina_aplicacion

import android.util.Base64
import com.example.sippina_aplicacion.model.Autenticacion.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthRepositoryTest {

    private val api: AutenticacionAPI = mockk()
    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        SessionManager.cerrarSesion()

        mockkStatic(Base64::class)
        every { Base64.decode(any<String>(), any()) } answers {
            java.util.Base64.getDecoder().decode(firstArg<String>())
        }
        every { Base64.encodeToString(any<ByteArray>(), any()) } answers {
            java.util.Base64.getEncoder().encodeToString(firstArg<ByteArray>())
        }

        repository = AuthRepository(api)
    }

    @After
    fun tearDown() {
        unmockkStatic(Base64::class)
    }

    @Test
    fun `iniciarSesion exito debe resolver reto HMAC, guardar token y retornar Result Success`() = runBlocking {
        val correo = "test@sipinna.gob.mx"
        val password = "PasswordSeguro123"
        val challengeBase64 = "dGVzdGNoYWxsZW5nZTEyMzQ1Njc4OWFiY2RlZg=="
        val fakeToken = "jwt_bearer_token_xyz"

        val retoResponse = ChallengeResponse(userId = 10L, challenge = challengeBase64)
        val tokenResponse = TokenResponse(token = fakeToken, role = "Ciudadano")

        coEvery { api.solicitarReto(correo) } returns Response.success(retoResponse)
        coEvery { api.verificarReto(any()) } returns Response.success(tokenResponse)

        val resultado = repository.iniciarSesion(correo, password)

        assertTrue("El inicio de sesión debió ser exitoso", resultado.isSuccess)
        assertEquals(fakeToken, resultado.getOrNull())
        assertEquals(fakeToken, SessionManager.obtenerToken())
        coVerify(exactly = 1) { api.solicitarReto(correo) }
        coVerify(exactly = 1) { api.verificarReto(any()) }
    }

    @Test
    fun `iniciarSesion reto fallido debe retornar failure`() = runBlocking {
        val correo = "invalido@sipinna.gob.mx"
        coEvery { api.solicitarReto(correo) } returns Response.error(404, "".toResponseBody(null))

        val resultado = repository.iniciarSesion(correo, "123456")

        assertTrue(resultado.isFailure)
        assertEquals("Usuario no encontrado o correo inválido", resultado.exceptionOrNull()?.message)
        assertNull(SessionManager.obtenerToken())
    }

    @Test
    fun `iniciarSesion verificacion de reto incorrecta debe retornar failure`() = runBlocking {
        val correo = "test@sipinna.gob.mx"
        val retoResponse = ChallengeResponse(userId = 10L, challenge = "dGVzdGNoYWxsZW5nZQ==")

        coEvery { api.solicitarReto(correo) } returns Response.success(retoResponse)
        coEvery { api.verificarReto(any()) } returns Response.error(401, "".toResponseBody(null))

        val resultado = repository.iniciarSesion(correo, "badpassword")

        assertTrue(resultado.isFailure)
        assertEquals("Credenciales inválidas", resultado.exceptionOrNull()?.message)
    }

    @Test
    fun `registrarUsuario exitoso debe retornar RegistroResponse`() = runBlocking {
        val regResponse = RegistroResponse(userId = 99L, mensaje = "Usuario creado")
        coEvery { api.registrar(any()) } returns Response.success(regResponse)

        val resultado = repository.registrarUsuario(
            correo = "nuevo@sipinna.gob.mx",
            contrasena = "pass123",
            nombre = "Ana",
            apellidos = "Gomez",
            telefono = "5551234567"
        )

        assertTrue(resultado.isSuccess)
        assertEquals(99L, resultado.getOrNull()?.userId)
    }

    @Test
    fun `obtenerPerfil con token sin prefijo Bearer debe agregar prefijo`() = runBlocking {
        val token = "token_limpio"
        val usuarioResponse = UsuarioResponse(userId = 1L, nombre = "Carlos")
        coEvery { api.obtenerUsuario(any(), "Bearer token_limpio") } returns Response.success(usuarioResponse)

        val resultado = repository.obtenerPerfil(token, "carlos@correo.com")

        assertTrue(resultado.isSuccess)
        assertEquals("Carlos", resultado.getOrNull()?.nombre)
    }

    @Test
    fun `cerrarSesion debe limpiar la sesion local incluso si la API falla`() = runBlocking {
        SessionManager.guardarSesion("token_activo")
        coEvery { api.logout(any(), any()) } throws RuntimeException("Network Error")

        val resultado = repository.cerrarSesion(1L, "token_activo")

        assertTrue(resultado.isSuccess)
        assertNull(SessionManager.obtenerToken())
    }
}