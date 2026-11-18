package com.example.listacompra.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacompra.R
import com.example.listacompra.ui.ListaCompraViewModel

/**
 * Pantalla principal. Lee el estado del [ListaCompraViewModel] y le
 * envía las acciones del usuario (patrón MVVM, flujo unidireccional).
 */
@Composable
fun ListaCompraScreen(
    modifier: Modifier = Modifier,
    viewModel: ListaCompraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = uiState.nuevoProducto,
            onValueChange = viewModel::onNuevoProductoChange,
            label = { Text(stringResource(R.string.etiqueta_producto)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = viewModel::anadirProducto,
            enabled = uiState.puedeAnadir
        ) {
            Text(stringResource(R.string.boton_anadir))
        }
        uiState.productos.forEach { producto ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = producto.nombre, modifier = Modifier.weight(1f))
                TextButton(onClick = { viewModel.eliminarProducto(producto) }) {
                    Text(stringResource(R.string.boton_eliminar))
                }
            }
        }
    }
}
