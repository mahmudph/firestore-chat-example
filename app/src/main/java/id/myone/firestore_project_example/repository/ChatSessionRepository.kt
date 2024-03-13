package id.myone.firestore_project_example.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.myone.firestore_project_example.extensions.snapshotFlow
import id.myone.firestore_project_example.models.global.PagingData
import id.myone.firestore_project_example.models.session.ChatUserSessionModel
import id.myone.firestore_project_example.ui.paging.ChatSessionPagingSource
import id.myone.firestore_project_example.utils.SecureDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ChatSessionRepository(
    private val fireStore: FirebaseFirestore,
    private val dataStore: SecureDataStore,
) {

    companion object {
        private const val CHAT_SESSION_COLLECTION = "chat-session"
    }

    suspend fun createChatSession(chatUserSessionModel: ChatUserSessionModel): Boolean {
        return try {
            fireStore.collection(CHAT_SESSION_COLLECTION).add(chatUserSessionModel).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun isExistSessionUserReceiver(sessionId: String): Boolean {

        return try {

            val isExist = fireStore.collection(CHAT_SESSION_COLLECTION)
                .whereEqualTo("id", sessionId)
                .get()
                .await()

            isExist.documents.isNotEmpty()

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getChatUserSession(
        lastDocumentSnapshot: DocumentSnapshot? = null
    ): Flow<PagingData<ChatUserSessionModel>> {

        val currentUserId = dataStore.getUserId()
        val response = fireStore.collection(CHAT_SESSION_COLLECTION).where(
            Filter.or(
                Filter.equalTo("sender.id", currentUserId),
                Filter.equalTo("receiver.id", currentUserId)
            )
        ).orderBy("timestamp", Query.Direction.ASCENDING)


        if (lastDocumentSnapshot != null) response.startAfter(lastDocumentSnapshot)


        return response.snapshotFlow()
            .map {
                PagingData(
                    documentSnapshot = it.documents.lastOrNull(),
                    data = it.documents.mapNotNull { item ->

                        val sender = item.get("sender") as? Map<String, Any>
                        val receiver = item.get("receiver") as Map<String, Any>?

                        sender?.let { senderData ->
                            receiver?.let { receiverData ->
                                ChatUserSessionModel(
                                    id = item.getString("id") ?: "",
                                    sender = ChatUserSessionModel.fromMapUser(senderData),
                                    receiver = ChatUserSessionModel.fromMapUser(receiverData),
                                    timestamp = item.getLong("timestamp") ?: 0
                                )
                            }
                        }
                    }
                )
            }
    }


    fun getPagingUserChatSession(): Flow<androidx.paging.PagingData<ChatUserSessionModel>> {
        return Pager(
            PagingConfig(pageSize = 5,)
        ) {
            ChatSessionPagingSource(fireStore)

        }.flow
    }

    suspend fun deleteChatSession(chatSessionId: String): Boolean {
        return try {
            fireStore.collection(CHAT_SESSION_COLLECTION).document(chatSessionId).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}