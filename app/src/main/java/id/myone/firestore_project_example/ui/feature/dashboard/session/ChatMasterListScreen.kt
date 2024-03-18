package id.myone.firestore_project_example.ui.feature.dashboard.session


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.collectAsLazyPagingItems
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.global.SnackBarData
import id.myone.firestore_project_example.models.session.CreateSessionInfo
import id.myone.firestore_project_example.ui.widget.SnackBarHostWidget
import id.myone.firestore_project_example.viewmodels.ChatUserSessionViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun ChatMasterListScreen(
    viewModel: ChatUserSessionViewModel = getViewModel(),
    navigateToSelectUser: () -> Unit = {},
    navigateToProfile: () -> Unit = {},
    navigateToCreateNewChat: (user: CreateSessionInfo) -> Unit = {},
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val deleteSessionLoading by viewModel.loadingDeleteSession.collectAsState()

    val chatMasterErrors = viewModel.errorLiveData.collectAsState()
    val chatMasterList by viewModel.chatUserSessionData.collectAsState(null)

    val userChannelList by viewModel.userChannelData.collectAsState(null)
    val userId = viewModel.currentUserId.collectAsState(initial = "")

    val userSessionChats = viewModel.pagingChatSessionData.collectAsLazyPagingItems()


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
            FloatingActionButton(onClick = navigateToSelectUser) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chat_24),
                    contentDescription = null,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues),
        ) {
            ChatMasterListContent(
                chatMasterList = chatMasterList?.data ?: emptyList(),
                chatMasterPagingList = userSessionChats,
                navigateToCreateChat = { session ->
                    navigateToCreateNewChat(session)
                },
                deleteSession = {
                    viewModel.deleteSession(it)
                },
                navigateToProfile = navigateToProfile,
                userChannelList = userChannelList?.data ?: emptyList(),
                userId = userId.value
            )

            if (deleteSessionLoading) {
                Dialog(onDismissRequest = { }) {
                    Card(modifier = Modifier.padding(16.dp)) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

