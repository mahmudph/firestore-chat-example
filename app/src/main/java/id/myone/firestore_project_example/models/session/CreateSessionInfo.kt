package id.myone.firestore_project_example.models.session

import android.os.Parcelable
import id.myone.firestore_project_example.models.channel.UserChannelModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class CreateSessionInfo(
    val sessionId: String,
    val user: UserChannelModel,
) : Parcelable