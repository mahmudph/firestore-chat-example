package id.myone.firestore_project_example.models.global

import com.google.firebase.firestore.DocumentSnapshot

data class PagingData<T>(
    val data: List<T>,
    val documentSnapshot: DocumentSnapshot?,
)
