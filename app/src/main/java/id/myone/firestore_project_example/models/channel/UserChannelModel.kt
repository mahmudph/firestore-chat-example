package id.myone.firestore_project_example.models.channel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class UserChannelModel(
    val id: String = UUID.randomUUID().toString(),
    val userName: String,
    val avatar: String = "https://www.w3schools.com/howto/img_avatar.png",
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    companion object {
        fun fromMap(data: Map<String, Any>): UserChannelModel {
            return UserChannelModel(
                id = data["id"] as String,
                userName = data["userName"] as String,
                avatar = data["avatar"] as String,
                timestamp = data["timestamp"] as Long
            )
        }
    }
}

