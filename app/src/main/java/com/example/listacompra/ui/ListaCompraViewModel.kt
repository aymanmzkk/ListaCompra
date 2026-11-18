package com.example.listacompra.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.listacompra.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel de la lista de la compra.
 *
 * Guarda el estado de la pantalla y contiene la lógica: añadir, eliminar y
 * marcar productos como comprados. La interfaz solo lee [uiState] y llama
 * a estas funciones; nunca modifica el estado directamente.
 */
class ListaCompraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListaCompraUiState())
    val uiState: StateFlow<ListaCompraUiState> = _uiState.asStateFlow()

    private var siguienteId = 1L

    fun onNuevoProductoChange(texto: String) {
        _uiState.update { it.copy(nuevoProducto = texto) }
    }

    fun anadirProducto() {
        val nombre = _uiState.value.nuevoProducto.trim()
        if (nombre.isEmpty()) return

        val producto = Producto(id = siguienteId++, nombre = nombre)
        _uiState.update {
            it.copy(productos = it.productos + producto, nuevoProducto = "")
        }
        Log.d(TAG, "Producto añadido: \"${producto.nombre}\" (id=${producto.id}). Total: ${_uiState.value.productos.size}")
    }

    fun eliminarProducto(producto: Producto) {
        _uiState.update { estado ->
            estado.copy(productos = estado.productos.filterNot { it.id == producto.id })
        }
        Log.d(TAG, "Producto eliminado: \"${producto.nombre}\" (id=${producto.id}). Total: ${_uiState.value.productos.size}")
    }

    fun alternarComprado(producto: Producto) {
        _uiState.update { estado ->
            estado.copy(
                productos = estado.productos.map {
                    if (it.id == producto.id) it.copy(comprado = !it.comprado) else it
                }
            )
        }
    }

    private companion object {
        const val TAG = "ListaCompra"
    }
}
