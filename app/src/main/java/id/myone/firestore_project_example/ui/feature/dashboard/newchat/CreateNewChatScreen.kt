package id.myone.firestore_project_example.ui.feature.dashboard.newchat

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import id.myone.firestore_project_example.viewmodels.ChatUserSessionViewModel
import id.myone.firestore_project_example.viewmodels.ChatUserViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun CreateNewChatScreen(
    viewModel: ChatUserViewModel = getViewModel(),
    navigateToChat: () -> Unit = {}
) {

    val chatItemsData = viewModel.chatUserListData.collectAsState()

    Scaffold {
        CreateNewChatContent(
            modifier = Modifier.padding(it),
            chatItems = chatItemsData.value,
            onBackClick = navigateToChat,
            onCreateNewChat = { chatName ->
                viewModel.createNewChat(chatName)
            }
        )
    }
}