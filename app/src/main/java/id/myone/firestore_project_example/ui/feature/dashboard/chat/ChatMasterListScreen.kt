package id.myone.firestore_project_example.ui.feature.dashboard.chat


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.global.SnackBarData
import id.myone.firestore_project_example.ui.widget.SnackBarHostWidget
import id.myone.firestore_project_example.viewmodels.ChatUserSessionViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun ChatMasterListScreen(
    viewModel: ChatUserSessionViewModel = getViewModel(),
    navigateToSelectUser: () -> Unit = {},
    navigateToCreateNewChat: (userId: String) -> Unit = {},
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val chatMasterErrors = viewModel.errorLiveData.collectAsState()
    val chatMasterList = viewModel.chatUserSessionData.collectAsState()

    val userChannelList by viewModel.userChannelData.collectAsState()


    LaunchedEffect(chatMasterErrors) {
        if (chatMasterErrors.value.isNotEmpty()) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    SnackBarData(
                        message = chatMasterErrors.value,
                        isError = true
                    )
                )
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackBarHostWidget(snackBarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToSelectUser ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chat_24),
                    contentDescription = null,
                )
            }
        }
    ) { paddingValues ->
        ChatMasterListContent(
            chatMasterList = chatMasterList.value,
            modifier = Modifier.padding(paddingValues),
            navigateToCreateChat = navigateToCreateNewChat,
            navigateToSelectUser = navigateToSelectUser,
            userChannelList = userChannelList
        )
    }
}

