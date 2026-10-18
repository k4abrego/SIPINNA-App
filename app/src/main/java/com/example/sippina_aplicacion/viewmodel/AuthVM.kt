package com.example.sippina_aplicacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sippina_aplicacion.model.Autenticacion.AuthRepository
import com.example.sippina_aplicacion.model.EstadoAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la gestión de la lógica de negocio y el estado reactivo del módulo de autenticación.
 *
 * Administra operaciones asíncronas de inicio de sesión, registro de nuevos usuarios, consulta y actualización de perfil,
 * recuperación de contraseña, cierre de sesión y eliminación de cuenta. Expone el estado global de autenticación
 * mediante un [StateFlow] de tipo [EstadoAuth].
 *
 * @property repository Repositorio [AuthRepository] utilizado para la comunicación con los endpoints de red y criptografía.
 */
class AuthVM(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(EstadoAuth())
    val state: StateFlow<EstadoAuth> = _state.asStateFlow()

    /**
     * Inicia sesión en la plataforma de forma asíncrona utilizando el repositorio de autenticación.
     */
    fun iniciarSesion(correo: String, contrasena: String) {
        val correoLimpio = correo.trim().lowercase()
        val contrasenaLimpia = contrasena.trim()

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.iniciarSesion(correoLimpio, contrasenaLimpia)

            resultado.onSuccess { token ->
                _state.update {
                    it.copy(
                        token = token,
                        correo = correoLimpio,
                        autenticado = true,
                        cargando = false
                    )
                }
                obtenerPerfil()
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        cargando = false,
                        autenticado = false,
                        error = error.localizedMessage ?: "Error al iniciar sesión"
                    )
                }
            }
        }
    }

    /**
     * Registra una nueva cuenta de usuario en el servidor y ejecuta el inicio de sesión automático al finalizar.
     */
    fun registrarUsuario(
        correo: String,
        contrasena: String,
        nombre: String,
        apellidos: String,
        telefono: String?
    ) {
        val correoLimpio = correo.trim().lowercase()
        val contrasenaLimpia = contrasena.trim()
        val nombreLimpio = nombre.trim()
        val apellidosLimpios = apellidos.trim()
        val telefonoLimpio = telefono?.trim()

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.registrarUsuario(
                correo = correoLimpio,
                contrasena = contrasenaLimpia,
                nombre = nombreLimpio,
                apellidos = apellidosLimpios,
                telefono = telefonoLimpio
            )
            resultado.onSuccess { registroResp ->
                _state.update {
                    it.copy(
                        usuarioId = registroResp.userId.toString(),
                        nombre = nombreLimpio,
                        apellidos = apellidosLimpios,
                        correo = correoLimpio,
                        telefono = telefonoLimpio
                    )
                }
                iniciarSesion(correoLimpio, contrasenaLimpia)
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        cargando = false,
                        autenticado = false,
                        error = error.localizedMessage ?: "Error al registrar usuario"
                    )
                }
            }
        }
    }

    /**
     * Obtiene la información detallada del perfil del usuario autenticado desde el servidor.
     */
    fun obtenerPerfil(correoTarget: String = _state.value.correo) {
        val correoLimpio = correoTarget.trim().lowercase()
        val token = _state.value.token
        if (token.isBlank() || correoLimpio.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }

            val resultado = repository.obtenerPerfil(token, correoLimpio)

            resultado.onSuccess { usuario ->
                _state.update {
                    it.copy(
                        usuarioId = usuario.userId.toString(),
                        nombre = usuario.nombre ?: "",
                        apellidos = usuario.apellido ?: "",
                        correo = correoLimpio,
                        telefono = usuario.telefono,
                        cargando = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        cargando = false,
                        error = error.localizedMessage ?: "Error al obtener perfil"
                    )
                }
            }
        }
    }
    /**
     * Actualiza la información personal del usuario autenticado en la plataforma.
     */
    fun actualizarPerfil(nombre: String, apellidos: String, telefono: String?) {
        val token = _state.value.token
        val correo = _state.value.correo

        if (token.isBlank() || correo.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.actualizarPerfil(
                token = token,
                correoTarget = correo,
                nombre = nombre.ifBlank { null },
                apellidos = apellidos.ifBlank { null },
                telefono = telefono?.ifBlank { null }
            )

            resultado.onSuccess {
                _state.update {
                    it.copy(
                        nombre = nombre,
                        apellidos = apellidos,
                        telefono = telefono,
                        cargando = false,
                        mensajeExito = "Perfil actualizado con éxito"
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        cargando = false,
                        error = error.localizedMessage ?: "Error al actualizar perfil"
                    )
                }
            }
        }
    }

    /**
     * Solicita la recuperación de contraseña para el correo electrónico indicado.
     */
    fun recuperarContrasena(correo: String) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.recuperarContrasena(correo)

            resultado.onSuccess { respuesta ->
                _state.update {
                    it.copy(
                        cargando = false,
                        mensajeExito = respuesta.mensaje ?: "Correo de recuperación enviado"
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        cargando = false,
                        error = error.localizedMessage ?: "Error al solicitar recuperación"
                    )
                }
            }
        }
    }

    /**
     * Elimina permanentemente la cuenta del usuario en el servidor y purga el estado local.
     */
    fun eliminarCuenta(onExito: () -> Unit) {
        val token = _state.value.token

        if (token.isBlank()) {
            limpiarEstadoLocal()
            onExito()
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }

            val resultado = repository.eliminarCuenta(token)

            resultado.onSuccess {
                limpiarEstadoLocal()
                onExito()
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        cargando = false,
                        error = error.localizedMessage ?: "Error al eliminar la cuenta"
                    )
                }
            }
        }
    }

    /**
     * Cierra la sesión activa del usuario, notificando al servidor e invalidando el estado local.
     */
    fun cerrarSesion() {
        val token = _state.value.token
        val userId = _state.value.usuarioId?.toLongOrNull() ?: 0L

        if (token.isNotBlank()) {
            viewModelScope.launch {
                repository.cerrarSesion(userId = userId, token = token)
            }
        }
        limpiarEstadoLocal()
    }

    /**
     * Restablece el estado de autenticación a su valor predeterminado.
     */
    private fun limpiarEstadoLocal() {
        _state.value = EstadoAuth()
    }

    /**
     * Limpia los mensajes temporales de error o éxito almacenados en el estado actual.
     */
    fun limpiarMensajes() {
        _state.update { it.copy(error = null, mensajeExito = null) }
    }
}