package id.myone.firestore_project_example.models.channel

import java.util.UUID

data class UserChannelModel(
    val id: String = UUID.randomUUID().toString(),
    val userName: String,
    val avatar: String = "https://www.w3schools.com/howto/img_avatar.png",
)

