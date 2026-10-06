package com.example.sippina_aplicacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sippina_aplicacion.model.Autenticacion.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AuthVM(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(EstadoAuth())
    val state: StateFlow<EstadoAuth> = _state.asStateFlow()

    fun iniciarSesion(correo: String, contrasena: String) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.iniciarSesion(correo, contrasena)

            resultado.onSuccess { token ->
                _state.update {
                    it.copy(
                        token = token,
                        correo = correo,
                        autenticado = true,
                        cargando = false
                    )
                }
                // Una vez que se tiene el token entonces se consultan los datos del perfil
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

    fun registrarUsuario(
        correo: String,
        contrasena: String,
        nombre: String,
        apellidos: String,
        telefono: String?
    ) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.registrarUsuario(
                correo = correo,
                contrasena = contrasena,
                nombre = nombre,
                apellidos = apellidos,
                telefono = telefono
            )
            resultado.onSuccess { registroResp ->
                _state.update {
                    it.copy(
                        usuarioId = registroResp.userId.toString(),
                        nombre = nombre,
                        apellidos = apellidos,
                        correo = correo,
                        telefono = telefono,
                        cargando = false,
                        mensajeExito = registroResp.mensaje
                    )
                }
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

    fun obtenerPerfil() {
        val token = _state.value.token
        if (token.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }

            val resultado = repository.obtenerPerfil(token)

            resultado.onSuccess { usuario ->
                _state.update {
                    it.copy(
                        usuarioId = usuario.idUsuario.toString(),
                        nombre = usuario.nombre,
                        apellidos = usuario.apellidos,
                        correo = usuario.correo,
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

    fun actualizarPerfil(nombre: String, apellidos: String, telefono: String?) {
        val token = _state.value.token
        if (token.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensajeExito = null) }

            val resultado = repository.actualizarPerfil(token, nombre, apellidos, telefono)

            resultado.onSuccess { usuario ->
                _state.update {
                    it.copy(
                        nombre = usuario.nombre,
                        apellidos = usuario.apellidos,
                        telefono = usuario.telefono,
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

    fun cerrarSesion() {
        val token = _state.value.token
        if (token.isNotBlank()) {
            viewModelScope.launch {
                repository.cerrarSesion(token)
            }
        }
        limpiarEstadoLocal()
    }

    private fun limpiarEstadoLocal() {
        _state.value = EstadoAuth()
    }

    fun limpiarMensajes() {
        _state.update { it.copy(error = null, mensajeExito = null) }
    }
}
