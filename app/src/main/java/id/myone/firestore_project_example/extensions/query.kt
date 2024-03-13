package id.myone.firestore_project_example.extensions

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listener = addSnapshotListener { value, error ->
        if (error != null) {
            close(error)
            return@addSnapshotListener
        }
        trySend(value!!)
    }
    awaitClose { listener.remove() }
}