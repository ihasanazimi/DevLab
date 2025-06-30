package ir.hasanazimi.devlab.common.base

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties

@Composable
fun BaseDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    confirmText: String? = null,
    onConfirm: () -> Unit = {},
    dismissText: String? = null,
    onDismiss: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start,
                )
            }
        },
        confirmButton = {
            confirmText?.let {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        },
        dismissButton = {
            dismissText?.let {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        },
        text = { content() },
        properties = properties
    )
}


@Preview
@Composable
private fun BaseDialogPreview() {
    Surface {
        BaseDialog(
            onDismissRequest = { /*dismiss dialog*/ },
            title = "Alert Title",
            confirmText = "Confirm",
            onConfirm = { /*confirm action*/ },
            dismissText = "Cancel",
            onDismiss = { /*close dialog*/ }
        ) {
            Text(
                text = "are you share close the dialog?",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}