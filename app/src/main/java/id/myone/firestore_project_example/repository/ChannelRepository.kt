package id.myone.firestore_project_example.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import id.myone.firestore_project_example.models.channel.UserChannelModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.UUID

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

                val data = value?.documents?.mapNotNull {
                    Log.i(javaClass.simpleName, "getUserChannelList: ${it.data}")
                    UserChannelModel(
                        id = it.getString("id") ?: UUID.randomUUID().toString(),
                        userName = it.getString("userName") ?: "No Name",
                        avatar = it.getString("avatar")
                            ?: "https://www.w3schools.com/howto/img_avatar.png"
                    )
                }

                trySend(data ?: emptyList())
            }

        awaitClose { listener?.remove() }
    }.flowOn(Dispatchers.IO)

    fun removeListener() {
        listener?.remove()
    }
}