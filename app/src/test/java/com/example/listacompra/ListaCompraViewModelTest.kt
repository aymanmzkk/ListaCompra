package com.example.listacompra

import com.example.listacompra.ui.ListaCompraViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ListaCompraViewModelTest {

    private val viewModel = ListaCompraViewModel()

    private fun anadir(nombre: String) {
        viewModel.onNuevoProductoChange(nombre)
        viewModel.anadirProducto()
    }

    @Test
    fun anadirProducto_loAgregaYLimpiaElCampo() {
        anadir("  Leche  ")

        val estado = viewModel.uiState.value
        assertEquals(listOf("Leche"), estado.productos.map { it.nombre })
        assertEquals("", estado.nuevoProducto)
        assertEquals(1, estado.pendientes)
    }

    @Test
    fun anadirProducto_ignoraTextoVacio() {
        anadir("   ")

        assertTrue(viewModel.uiState.value.productos.isEmpty())
        assertFalse(viewModel.uiState.value.puedeAnadir)
    }

    @Test
    fun eliminarProducto_loQuitaDeLaLista() {
        anadir("Pan")
        anadir("Huevos")
        val pan = viewModel.uiState.value.productos.first()

        viewModel.eliminarProducto(pan)

        assertEquals(listOf("Huevos"), viewModel.uiState.value.productos.map { it.nombre })
    }

    @Test
    fun alternarComprado_actualizaElContadorDePendientes() {
        anadir("Pan")
        anadir("Huevos")
        val pan = viewModel.uiState.value.productos.first()

        viewModel.alternarComprado(pan)
        assertEquals(1, viewModel.uiState.value.pendientes)

        viewModel.alternarComprado(viewModel.uiState.value.productos.first())
        assertEquals(2, viewModel.uiState.value.pendientes)
    }
}
