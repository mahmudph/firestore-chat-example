package id.myone.firestore_project_example.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.firestore_project_example.models.chat.ChatItemModel
import id.myone.firestore_project_example.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatUserViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _chatUserListData = MutableStateFlow<List<ChatItemModel>>(emptyList())
    val chatUserListData = _chatUserListData

    fun getChatDataByUserSessionId(id: String) {
        viewModelScope.launch {
            repository.getChatSessionById(id)
        }
    }

    fun createNewChat(chatName: String) {
        viewModelScope.launch {
            repository.createNewChat(
                ChatItemModel(
                    message = chatName,
                    userName = "MyOne",
                    userProfile = "https://avatars.githubusercontent.com/u/47259793?v=4",
                    time = "2021-10-10T10:10:10.10Z"
                )
            )
        }
    }
}