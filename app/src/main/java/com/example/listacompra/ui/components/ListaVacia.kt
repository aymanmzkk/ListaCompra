package com.example.listacompra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.listacompra.R

/** Mensaje centrado que se muestra en lugar de la lista cuando no hay productos. */
@Composable
fun ListaVacia(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.espacio_mediano))
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = stringResource(R.string.cd_lista_vacia),
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(dimensionResource(R.dimen.tamano_icono_lista_vacia))
            )
            Text(
                text = stringResource(R.string.lista_vacia),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
