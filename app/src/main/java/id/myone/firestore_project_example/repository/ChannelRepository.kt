package id.myone.firestore_project_example.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import id.myone.firestore_project_example.models.channel.UserChannelModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChannelRepository(
    private val fireStore: FirebaseFirestore,
) {

    companion object {
        const val CHANNEL_COLLECTION = "user-channel"
    }

    private var listener: ListenerRegistration? = null


    suspend fun loginChannel(payload: UserChannelModel): Boolean {
        return try {
            fireStore.collection(CHANNEL_COLLECTION).document().set(payload).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getUserChannelList() = callbackFlow<List<UserChannelModel>> {

        listener = fireStore.collection(CHANNEL_COLLECTION)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val list = value?.toObjects(UserChannelModel::class.java) ?: emptyList()
                trySend(list)
            }

        awaitClose { listener?.remove() }
    }

    fun removeListener() {
        listener?.remove()
    }
}