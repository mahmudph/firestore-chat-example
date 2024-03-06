package id.myone.firestore_project_example.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.myone.firestore_project_example.models.global.SnackBarData


@Composable
fun SnackBarHostWidget(snackBarHostState: SnackbarHostState) {
    SnackbarHost(snackBarHostState) { data ->
        val isError = (data.visuals as? SnackBarData)?.isError ?: false
        val buttonColor = if (isError) {
            ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            )

        } else {
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.inversePrimary
            )
        }

        Snackbar(
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.secondary)
                .padding(12.dp),
            action = {
                TextButton(
                    colors = buttonColor,
                    onClick = { if (isError) data.dismiss() else data.performAction() },
                ) {
                    Text(data.visuals.actionLabel ?: "")
                }
            }
        ) {
            Text(data.visuals.message)
        }
    }
}

