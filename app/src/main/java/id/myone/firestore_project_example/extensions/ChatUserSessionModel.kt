package id.myone.firestore_project_example.extensions

import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.session.ChatUserSessionModel


fun ChatUserSessionModel.mapToUser(fromSender: Boolean = true): UserChannelModel {

    if (!fromSender) {
        return UserChannelModel(
            id = sender.id,
            userName = sender.userName,
            avatar = sender.avatar
        )
    }

    return UserChannelModel(
        id = receiver.id,
        userName = receiver.userName,
        avatar = receiver.avatar
    )
}

