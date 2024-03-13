package id.myone.firestore_project_example.ui.feature.dashboard.chat

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import id.myone.firestore_project_example.models.chat.ChatItemModel
import id.myone.firestore_project_example.models.global.PagingData
import id.myone.firestore_project_example.utils.SecureDataStore
import id.myone.firestore_project_example.viewmodels.ChatUserSessionViewModel
import id.myone.firestore_project_example.viewmodels.ChatUserViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.inject
import org.koin.core.parameter.parametersOf


@Composable
fun CreateNewChatScreen(
    viewModel: ChatUserViewModel = getViewModel(),
    navigateToChat: () -> Unit = {}
) {

    var queryMessage by remember { mutableStateOf("") }
    val user by viewModel.currentUser.collectAsState(initial = null)
    val chatItemsData by viewModel.chatUserListData.collectAsState(null)


    Scaffold {
        CreateNewChatContent(
            modifier = Modifier.padding(it),
            chatItems = chatItemsData?.data ?: emptyList(),
            onBackClick = navigateToChat,
            onCreateNewChat = { chatName ->
                queryMessage = ""
                viewModel.createNewChat(chatName)
            },
            onChatMessage = {
                queryMessage = it
            },
            chatMessage = queryMessage,
            receiverUser = user
        )
    }
}