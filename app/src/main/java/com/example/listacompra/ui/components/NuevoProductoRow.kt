package com.example.listacompra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.example.listacompra.R

/**
 * Parte superior: campo de texto para el nombre del producto y botón "Añadir".
 */
@Composable
fun NuevoProductoRow(
    texto: String,
    puedeAnadir: Boolean,
    onTextoChange: (String) -> Unit,
    onAnadir: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.espacio_pequeno))
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoChange,
            label = { Text(stringResource(R.string.etiqueta_producto)) },
            placeholder = { Text(stringResource(R.string.placeholder_producto)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            // La tecla "Hecho" del teclado también añade el producto
            keyboardActions = KeyboardActions(onDone = { if (puedeAnadir) onAnadir() }),
            modifier = Modifier.weight(1f)
        )
        Button(onClick = onAnadir, enabled = puedeAnadir) {
            Text(stringResource(R.string.boton_anadir))
        }
    }
}
