package id.myone.firestore_project_example.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import id.myone.firestore_project_example.models.chat.ChatItemModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepository(
    private val fireStore: FirebaseFirestore
) {

    companion object {
        private const val CHAT_COLLECTION = "chat"
    }

    private var listener: ListenerRegistration? = null

    suspend fun getChatSessionById(id: String): String? {
        return try {
            val result = fireStore.collection(CHAT_COLLECTION).document(id).get().await()
            result.getString("message")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getChatSessionDataByUserSessionId(id: String): Flow<List<ChatItemModel>> = callbackFlow {

        listener = fireStore.collection(CHAT_COLLECTION).addSnapshotListener { value, error ->
            if (error != null) {
                error.printStackTrace()
                return@addSnapshotListener
            }
            val chatList = value?.documents?.mapNotNull {
                ChatItemModel(
                    id = it.getString("id") ?: "",
                    message = it.getString("message") ?: "",
                    userProfile = it.getString("userProfile") ?: "",
                    userName = it.getString("userName") ?: "",
                    time = it.getString("time") ?: ""
                )
            }

            chatList?.let { trySend(it) }
        }

        awaitClose { listener?.remove() }
    }

    suspend fun createNewChat(chatItemModel: ChatItemModel): Boolean {
        return try {
            fireStore.collection(CHAT_COLLECTION).add(chatItemModel).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}