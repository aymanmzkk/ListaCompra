package com.example.listacompra.data

/**
 * Modelo de datos: un producto de la lista de la compra.
 *
 * @property id identificador único (sirve de clave estable en la LazyColumn).
 * @property nombre texto que escribe el usuario.
 * @property comprado true cuando el producto ya está en el carro.
 */
data class Producto(
    val id: Long,
    val nombre: String,
    val comprado: Boolean = false
)
