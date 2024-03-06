package id.myone.firestore_project_example.models.chat

import java.util.UUID

data class ChatItemModel(
    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val userName: String,
    val userProfile: String,
    val time: String,
)
