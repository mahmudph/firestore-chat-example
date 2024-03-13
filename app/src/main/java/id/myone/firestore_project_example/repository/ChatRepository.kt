package id.myone.firestore_project_example.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.myone.firestore_project_example.extensions.snapshotFlow
import id.myone.firestore_project_example.models.global.PagingData
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.chat.ChatItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ChatRepository(
    private val fireStore: FirebaseFirestore
) {

    companion object {
        private const val CHAT_COLLECTION = "chat"
    }


    fun getChatSessionDataByUserSessionId(
        sessionId: String,
        lastDocumentSnapshot: DocumentSnapshot? = null
    ): Flow<PagingData<ChatItemModel>> {

        val data = fireStore.collection(CHAT_COLLECTION).whereEqualTo("sessionId", sessionId)
            .orderBy("time", Query.Direction.ASCENDING)


        if (lastDocumentSnapshot != null) data.startAt(lastDocumentSnapshot)

        return data.snapshotFlow()
            .map {

                PagingData(
                    documentSnapshot = it.documents.lastOrNull(),
                    data = it.documents.mapNotNull { item ->

                        ChatItemModel(
                            id = item.getString("id") ?: "",
                            message = item.getString("message") ?: "",
                            time = item.getLong("time") ?: 0L,
                            sessionId = item.getString("sessionId") ?: "",
                            userSender = UserChannelModel.fromMap(item.get("userSender") as Map<String, Any>),
                            userReceiver = UserChannelModel.fromMap(item.get("userReceiver") as Map<String, Any>),
                        )
                    }
                )

            }
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

    suspend fun deleteChatBySessionId(sessionId: String): Boolean {

        return try {

            val chat = fireStore.collection(CHAT_COLLECTION)
                .whereEqualTo("sessionId", sessionId)
                .get()
                .await()

            chat.documents.forEach {
                fireStore.collection(CHAT_COLLECTION).document(it.id).delete().await()
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}