package id.myone.firestore_project_example.models.chat

data class ChatUserSessionModel(
    val chatId: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
)