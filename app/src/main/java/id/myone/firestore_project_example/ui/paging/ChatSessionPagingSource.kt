package id.myone.firestore_project_example.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import id.myone.firestore_project_example.models.session.ChatUserSessionModel
import kotlinx.coroutines.tasks.await

class ChatSessionPagingSource(
    private val fireStore: FirebaseFirestore
): PagingSource<QuerySnapshot, ChatUserSessionModel>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, ChatUserSessionModel>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, ChatUserSessionModel> {
        return try {

            val chatSessions = fireStore.collection("chat-session")
                .limit(10)
                .get()
                .await()

            val currentPage = params.key ?: chatSessions
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = chatSessions.query.startAfter(lastVisibleProduct).get().await()

            LoadResult.Page(
                data = currentPage.documents.mapNotNull { item ->

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
                },
                prevKey = null,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}