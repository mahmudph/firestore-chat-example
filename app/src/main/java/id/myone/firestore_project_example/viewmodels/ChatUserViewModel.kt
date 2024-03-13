package id.myone.firestore_project_example.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.chat.ChatItemModel
import id.myone.firestore_project_example.models.global.PagingData
import id.myone.firestore_project_example.models.session.ChatUserSessionModel
import id.myone.firestore_project_example.models.session.CreateSessionInfo
import id.myone.firestore_project_example.repository.ChatRepository
import id.myone.firestore_project_example.repository.ChatSessionRepository
import id.myone.firestore_project_example.utils.SecureDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatUserViewModel(
    private val session: CreateSessionInfo,
    private val dataStore: SecureDataStore,
    private val repository: ChatRepository,
    private val chatUserSessionModel: ChatSessionRepository,
) : ViewModel() {

    private val chatSessionId by lazy {
        UUID.randomUUID().toString()
    }

    val currentUser = flow { emit(session.user) }
    val chatUserListData = flow {

        val isExistSession = chatUserSessionModel.isExistSessionUserReceiver(
            sessionId = session.sessionId
        )

        val chatSessionIdTemporal = if (isExistSession) session.sessionId else chatSessionId

        repository.getChatSessionDataByUserSessionId(chatSessionIdTemporal).collect {
            Log.d(javaClass.simpleName, "data: $it")
            emit(it)
        }
    }

    init {
        createNewSessionsChat()
    }

    private fun createNewSessionsChat() {

        viewModelScope.launch(Dispatchers.IO) {

            val isExistSession = chatUserSessionModel.isExistSessionUserReceiver(
                sessionId = session.sessionId
            )

            if (isExistSession) return@launch

            val payload = ChatUserSessionModel(

                id = chatSessionId,
                sender = ChatUserSessionModel.User(
                    id = dataStore.getUserId(),
                    userName = dataStore.getUserName(),
                    avatar = dataStore.getUserAvatar()
                ),

                receiver = ChatUserSessionModel.User(
                    id = session.user.id,
                    userName = session.user.userName,
                    avatar = session.user.avatar,
                ),
            )

            chatUserSessionModel.createChatSession(payload)

        }
    }


    fun createNewChat(chatName: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val isExistSession = chatUserSessionModel.isExistSessionUserReceiver(
                sessionId = session.sessionId
            )
            val chatSessionId = if (isExistSession) session.sessionId else chatSessionId

            repository.createNewChat(
                ChatItemModel(
                    message = chatName,
                    userReceiver = session.user,
                    userSender = UserChannelModel(
                        id = dataStore.getUserId(),
                        userName = dataStore.getUserName(),
                        avatar = dataStore.getUserAvatar()
                    ),
                    time = System.currentTimeMillis(),
                    sessionId = chatSessionId
                )
            )
        }
    }
}