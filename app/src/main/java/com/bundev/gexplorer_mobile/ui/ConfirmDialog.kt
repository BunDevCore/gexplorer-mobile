package com.bundev.gexplorer_mobile.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.bundev.gexplorer_mobile.R

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    confirmRequest: () -> Unit,
    text: String,
    rejectRequest: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onDismissRequest(); confirmRequest() }) {
                Text(text = stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest(); rejectRequest() }) {
                Text(text = stringResource(id = R.string.no))
            }
        },
        text = { Text(text = text, fontSize = 20.sp, lineHeight = 24.sp) }
    )
}