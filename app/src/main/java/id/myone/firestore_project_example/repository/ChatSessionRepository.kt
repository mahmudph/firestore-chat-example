package id.myone.firestore_project_example.repository

import com.google.firebase.firestore.FirebaseFirestore
import id.myone.firestore_project_example.models.chat.ChatUserSessionModel
import kotlinx.coroutines.tasks.await

class ChatSessionRepository(
    private val fireStore: FirebaseFirestore
) {

    companion object {
        private const val CHAT_SESSION_COLLECTION = "chat-session"
    }

    suspend fun getChatUserSession(): List<ChatUserSessionModel> {
        return try {

            val result = fireStore.collection(CHAT_SESSION_COLLECTION).get().await()
            result.documents.map { it.toObject(ChatUserSessionModel::class.java)!! }

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getChatSessionById(id: String): String? {
        return try {
            val result = fireStore.collection(CHAT_SESSION_COLLECTION).document(id).get().await()
            result.getString("message")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}