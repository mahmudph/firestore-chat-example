package id.myone.firestore_project_example.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import id.myone.firestore_project_example.extensions.snapshotFlow
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.global.PagingData
import id.myone.firestore_project_example.utils.SecureDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChannelRepository(
    private val fireStore: FirebaseFirestore,
    private val dataStore: SecureDataStore,
) {

    companion object {
        const val CHANNEL_COLLECTION = "user-channel"
    }

    suspend fun loginChannel(payload: UserChannelModel): Boolean {
        return try {

            val user = fireStore.collection(CHANNEL_COLLECTION)
                .whereEqualTo("userName", payload.userName.lowercase())
                .get()
                .await()

            if (user.documents.isEmpty()) {
                fireStore.collection(CHANNEL_COLLECTION).document().set(payload).await()
                return true
            }

            user.documents.firstOrNull()?.let { userData ->
                dataStore.loginChannelSession(
                    UserChannelModel(
                        id = userData.getString("id") ?: UUID.randomUUID().toString(),
                        userName = userData.getString("userName") ?: "No Name",
                        avatar = userData.getString("avatar")
                            ?: "https://www.w3schools.com/howto/img_avatar.png",
                        timestamp = userData.getLong("timestamp") ?: System.currentTimeMillis()
                    )
                )
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getUserChannelList(
        lastDocumentSnapshot: DocumentSnapshot? = null
    ): Flow<PagingData<UserChannelModel>> {

        val userId = dataStore.getUserId()
        val data = fireStore.collection(CHANNEL_COLLECTION).where(Filter.notEqualTo("id", userId))

        if (lastDocumentSnapshot != null) data.startAfter(lastDocumentSnapshot)

        return data.orderBy("id")
            .limit(20)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .snapshotFlow().map {
                PagingData(
                    documentSnapshot = it.documents.lastOrNull(),
                    data = it.documents.mapNotNull { item ->
                        UserChannelModel(
                            id = item.getString("id") ?: UUID.randomUUID().toString(),
                            userName = item.getString("userName") ?: "No Name",
                            avatar = item.getString("avatar")
                                ?: "https://www.w3schools.com/howto/img_avatar.png",
                            timestamp = item.getLong("timestamp") ?: System.currentTimeMillis()
                        )
                    }
                )
            }
    }


    suspend fun searchUserChannel(
        q: String,
        lastDocumentSnapshot: DocumentSnapshot? = null,
    ): PagingData<UserChannelModel> {

        return try {

            val response = fireStore.collection(CHANNEL_COLLECTION)
                .orderBy("userName")
                .whereGreaterThanOrEqualTo("userName", q)
                .whereLessThanOrEqualTo("userName", q + "\uf8ff")


            if (lastDocumentSnapshot != null) response.startAfter(lastDocumentSnapshot)
            val resultTemp = response.limit(20)
                .get(Source.CACHE)
                .await()

            PagingData(
                documentSnapshot = resultTemp.documents.last(),
                data = resultTemp.documents.mapNotNull { item ->
                    UserChannelModel(
                        id = item.getString("id") ?: UUID.randomUUID().toString(),
                        userName = item.getString("userName") ?: "No Name",
                        avatar = item.getString("avatar")
                            ?: "https://www.w3schools.com/howto/img_avatar.png",
                        timestamp = item.getLong("timestamp") ?: System.currentTimeMillis()
                    )
                }
            )


        } catch (e: Exception) {
            e.printStackTrace()
            PagingData(
                documentSnapshot = lastDocumentSnapshot,
                data = emptyList()
            )
        }
    }

}