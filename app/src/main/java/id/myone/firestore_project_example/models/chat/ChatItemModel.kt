package id.myone.firestore_project_example.models.chat

import id.myone.firestore_project_example.models.channel.UserChannelModel
import java.util.UUID

data class ChatItemModel(
    val id: String = UUID.randomUUID().toString(),
    val sessionId: String,
    val userSender: UserChannelModel,
    val userReceiver: UserChannelModel,
    val message: String,
    val time: Long,
) {

}
