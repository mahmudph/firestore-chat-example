package id.myone.firestore_project_example.models.session

data class ChatUserSessionModel(
    val id: String,
    val sender: User,
    val receiver: User,
    val timestamp: Long = System.currentTimeMillis()
) {
    data class User(
        val id: String,
        val userName: String,
        val avatar: String,
    )

    companion object {
        fun fromMapUser(map: Map<String, Any>): User {
            return User(
                id = map["id"]  as String,
                userName = map["userName"] as String,
                avatar = map["avatar"] as String
            )
        }
    }
}