package id.myone.firestore_project_example.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.firestore_project_example.models.chat.ChatUserSessionModel
import id.myone.firestore_project_example.repository.ChannelRepository
import id.myone.firestore_project_example.repository.ChatSessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatUserSessionViewModel(
    private val repository: ChatSessionRepository,
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private val errorData = MutableStateFlow("")
    val errorLiveData: StateFlow<String> = errorData

    private val chatUserSessionDataStore = MutableStateFlow<List<ChatUserSessionModel>>(emptyList())
    val chatUserSessionData: StateFlow<List<ChatUserSessionModel>> = chatUserSessionDataStore

    private val userChannelDataStore = channelRepository.getUserChannelList()
    val userChannelData = userChannelDataStore.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = emptyList()
    )

    init {
        getChatUserSession()
    }

    private fun getChatUserSession() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getChatUserSession()
            chatUserSessionDataStore.value = result

        }
    }

    override fun onCleared() {
        super.onCleared()
        repository
    }
}