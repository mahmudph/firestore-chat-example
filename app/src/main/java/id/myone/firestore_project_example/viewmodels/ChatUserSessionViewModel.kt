package id.myone.firestore_project_example.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import id.myone.firestore_project_example.repository.ChannelRepository
import id.myone.firestore_project_example.repository.ChatRepository
import id.myone.firestore_project_example.repository.ChatSessionRepository
import id.myone.firestore_project_example.ui.paging.ChatSessionPagingSource
import id.myone.firestore_project_example.utils.SecureDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ChatUserSessionViewModel(
    private val store: SecureDataStore,
    private val repository: ChatSessionRepository,
    private val chatRepository: ChatRepository,
    private val channelRepository: ChannelRepository,
) : ViewModel() {

    private val errorData = MutableStateFlow("")
    val errorLiveData: StateFlow<String> = errorData

    val loadingDeleteSession = MutableStateFlow(false)
    private val lastDocumentSnapshot = MutableStateFlow<DocumentSnapshot?>(null)

    val currentUserId = flow { emit(store.getUserId()) }

    val chatUserSessionData = flow {
        repository.getChatUserSession(lastDocumentSnapshot.value).collect {
            emit(it)
        }
    }

    val userChannelData = flow {
        channelRepository.getUserChannelList().collect {
            emit(it)
        }
    }

    val pagingChatSessionData = repository.getPagingUserChatSession().cachedIn(viewModelScope)


    fun deleteSession(sessionId: String) {

        viewModelScope.launch(Dispatchers.IO) {

            loadingDeleteSession.value = true

            chatRepository.deleteChatBySessionId(sessionId)
            repository.deleteChatSession(sessionId)

            loadingDeleteSession.value = false
        }

    }
}