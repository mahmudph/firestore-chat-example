package id.myone.firestore_project_example.ui.feature.dashboard.chanel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import id.myone.firestore_project_example.ui.widget.SnackBarHostWidget
import id.myone.firestore_project_example.viewmodels.ChannelViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginChannelScreen(
    viewModel: ChannelViewModel = getViewModel(),
    navigateToChatSession: () -> Unit = {}
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val loginChannelResult by viewModel.loginChannelData.collectAsState(initial = null)

    LaunchedEffect(loginChannelResult) {
        if (loginChannelResult == true) {
            snackBarHostState.showSnackbar("Account channel created")
            navigateToChatSession()
        } else if (loginChannelResult == false) {
            snackBarHostState.showSnackbar("Failed to create account channel")
        }
    }

    Scaffold(
        snackbarHost = {
            SnackBarHostWidget(snackBarHostState = snackBarHostState)
        }
    ) {
        LoginChannelContent(
            modifier = Modifier.padding(it),
            createNewUserChannel = { data ->
                viewModel.loginChannel(data)
            }
        )
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun LoginChannelScreenPreview() {
    FirestoreprojectexampleTheme {
        LoginChannelScreen()
    }
}