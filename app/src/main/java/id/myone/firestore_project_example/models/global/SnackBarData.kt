package id.myone.firestore_project_example.models.global

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class SnackBarData(
    override val message: String,
    val isError: Boolean
) : SnackbarVisuals {

    override val actionLabel: String
        get() = if (isError) "Retry" else "Ok"

    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short

    override val withDismissAction: Boolean
        get() = true
}