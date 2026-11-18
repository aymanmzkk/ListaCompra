package com.example.listacompra.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import com.example.listacompra.R
import com.example.listacompra.data.Producto

/**
 * Tarjeta de un producto. Al pulsarla se marca/desmarca como comprado.
 *
 * Se usa un Box para superponer el icono de estado en la esquina superior
 * derecha de la tarjeta, por encima de la fila con el nombre y el botón borrar.
 * Todos los colores salen de MaterialTheme.colorScheme, así la tarjeta se ve
 * bien tanto en modo claro como en modo oscuro.
 */
@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = MaterialTheme.colorScheme

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (producto.comprado) colores.surfaceContainerHighest else colores.secondaryContainer,
            contentColor = if (producto.comprado) colores.onSurfaceVariant else colores.onSecondaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                // Accesibilidad: TalkBack lo anuncia como casilla marcada / no marcada
                .toggleable(
                    value = producto.comprado,
                    role = Role.Checkbox,
                    onValueChange = { onClick() }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_tarjeta),
                        end = dimensionResource(R.dimen.hueco_icono_estado)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (producto.comprado) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = dimensionResource(R.dimen.padding_tarjeta))
                )
                IconButton(onClick = onEliminar) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(R.string.cd_eliminar, producto.nombre)
                    )
                }
            }

            // Icono de estado en la esquina superior derecha
            Icon(
                imageVector = if (producto.comprado) Icons.Filled.CheckCircle else Icons.Outlined.ShoppingCart,
                contentDescription = stringResource(
                    if (producto.comprado) R.string.cd_comprado else R.string.cd_pendiente
                ),
                tint = if (producto.comprado) colores.primary else colores.onSecondaryContainer,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimensionResource(R.dimen.margen_icono_esquina))
                    .size(dimensionResource(R.dimen.tamano_icono_estado))
            )
        }
    }
}
