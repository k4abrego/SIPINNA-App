package com.example.sippina_aplicacion

import com.example.sippina_aplicacion.viewmodel.NotificacionesVM
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotificacionesVMTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: NotificacionesVM

    @Before
    fun setUp() {
        viewModel = NotificacionesVM()
    }

    @Test
    fun `cargarNotificaciones debe actualizar la lista de notificaciones y desactivar cargando`() = runTest {
        viewModel.cargarNotificaciones("usuario_1")

        val state = viewModel.state.value
        assertFalse(state.cargando)
        assertFalse(state.lista.isEmpty())
        assertEquals(2, state.lista.size)
        assertEquals(1, state.lista[0].id)
    }

    @Test
    fun `marcarComoLeida debe actualizar optimistamente el estado leida a true`() = runTest {
        viewModel.cargarNotificaciones("usuario_1")
        assertFalse(viewModel.state.value.lista.first { it.id == 1 }.leida)

        viewModel.marcarComoLeida(1)

        val notifActualizada = viewModel.state.value.lista.first { it.id == 1 }
        assertTrue(notifActualizada.leida)
    }
}