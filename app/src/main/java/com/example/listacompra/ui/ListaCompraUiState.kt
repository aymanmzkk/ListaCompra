package com.example.listacompra.ui

import com.example.listacompra.data.Producto

/**
 * Estado inmutable que la pantalla necesita para pintarse.
 * El ViewModel publica una copia nueva cada vez que algo cambia.
 */
data class ListaCompraUiState(
    val productos: List<Producto> = emptyList(),
    val nuevoProducto: String = ""
) {
    /** Productos que todavía no se han comprado (para el contador). */
    val pendientes: Int
        get() = productos.count { !it.comprado }

    /** Solo se puede añadir si el usuario ha escrito algo. */
    val puedeAnadir: Boolean
        get() = nuevoProducto.isNotBlank()
}
