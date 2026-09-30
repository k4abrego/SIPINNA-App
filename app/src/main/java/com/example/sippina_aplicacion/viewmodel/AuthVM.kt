package com.example.sippina_aplicacion.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthState(
    val usuarioId: Int? = null,
    val nombre: String = "",
    val correo: String = "",
    val autenticado: Boolean = false,
    val cargando: Boolean = false,
    val error: String? = null
)

class AuthVM : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()


    fun iniciarSesion(
        correo: String,
        contrasena: String
    ) {
        //Aquí lo de irá Retrofit
    }


    fun registrarUsuario(
        correo: String,
        contrasena: String,
        nombre: String,
        apellidos: String,
        telefono: String?
    ) {
        //Retrofit
    }


    fun cerrarSesion(){
        _state.value = AuthState()
    }

}