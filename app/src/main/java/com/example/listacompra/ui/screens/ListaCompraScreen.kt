package com.example.listacompra.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacompra.R
import com.example.listacompra.data.Producto
import com.example.listacompra.ui.ListaCompraUiState
import com.example.listacompra.ui.ListaCompraViewModel
import com.example.listacompra.ui.components.ListaVacia
import com.example.listacompra.ui.components.NuevoProductoRow
import com.example.listacompra.ui.components.ProductoCard
import com.example.listacompra.ui.theme.ListaCompraTheme

/**
 * Pantalla principal con estado: lee el [ListaCompraViewModel] y le envía
 * las acciones del usuario (patrón MVVM, flujo unidireccional de datos).
 */
@Composable
fun ListaCompraScreen(
    modifier: Modifier = Modifier,
    viewModel: ListaCompraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListaCompraContent(
        uiState = uiState,
        onNuevoProductoChange = viewModel::onNuevoProductoChange,
        onAnadir = viewModel::anadirProducto,
        onAlternarComprado = viewModel::alternarComprado,
        onEliminar = viewModel::eliminarProducto,
        modifier = modifier
    )
}

/**
 * Pantalla sin estado: solo pinta lo que recibe. Así se puede previsualizar
 * con @Preview sin necesidad de un ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCompraContent(
    uiState: ListaCompraUiState,
    onNuevoProductoChange: (String) -> Unit,
    onAnadir: () -> Unit,
    onAlternarComprado: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
                .padding(horizontal = dimensionResource(R.dimen.margen_pantalla)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.espacio_mediano))
        ) {
            ContadorPendientes(pendientes = uiState.pendientes)

            NuevoProductoRow(
                texto = uiState.nuevoProducto,
                puedeAnadir = uiState.puedeAnadir,
                onTextoChange = onNuevoProductoChange,
                onAnadir = onAnadir
            )

            if (uiState.productos.isEmpty()) {
                ListaVacia()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.espacio_pequeno)),
                    contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.margen_pantalla))
                ) {
                    items(uiState.productos, key = { it.id }) { producto ->
                        ProductoCard(
                            producto = producto,
                            onClick = { onAlternarComprado(producto) },
                            onEliminar = { onEliminar(producto) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}

/** "1 producto pendiente" / "3 productos pendientes" usando <plurals>. */
@Composable
fun ContadorPendientes(pendientes: Int, modifier: Modifier = Modifier) {
    Text(
        text = pluralStringResource(R.plurals.productos_pendientes, pendientes, pendientes),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

/** Lista de ejemplo para las previsualizaciones (nombres sacados de strings.xml). */
@Composable
private fun productosEjemplo(): List<Producto> =
    stringArrayResource(R.array.productos_ejemplo).mapIndexed { i, nombre ->
        Producto(id = i.toLong(), nombre = nombre, comprado = i == 1)
    }

@Preview(showBackground = true)
@Composable
private fun ListaCompraPreview() {
    ListaCompraTheme {
        ListaCompraContent(
            uiState = ListaCompraUiState(productos = productosEjemplo()),
            onNuevoProductoChange = {}, onAnadir = {}, onAlternarComprado = {}, onEliminar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListaVaciaPreview() {
    ListaCompraTheme {
        ListaCompraContent(
            uiState = ListaCompraUiState(),
            onNuevoProductoChange = {}, onAnadir = {}, onAlternarComprado = {}, onEliminar = {}
        )
    }
}
