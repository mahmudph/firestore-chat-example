package id.myone.firestore_project_example.ui.feature.dashboard.chat.widget


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.chat.ChatItemModel
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import id.myone.firestore_project_example.utils.TimeUtils
import java.util.UUID


@Composable
fun ChatListItem(
    modifier: Modifier = Modifier,
    currentUserId: String,
    chat: ChatItemModel,
) {


    Box(
        modifier = modifier.padding(
            vertical = 8.dp,
            horizontal = 16.dp
        )
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides if (chat.userSender.id == currentUserId) LayoutDirection.Ltr else LayoutDirection.Rtl
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(chat.userSender.avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier
                        .size(35.dp, 35.dp)
                        .align(Alignment.Bottom)
                        .border(1.dp, color = Color.Blue, shape = CircleShape)
                        .clip(CircleShape)
                )
                Card(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 0.dp, bottom = 0.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = chat.message,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
                        )

                        Text(
                            text = TimeUtils.timeAgo(chat.time),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp,
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun ChatListItemPreview() {
    FirestoreprojectexampleTheme {
        ChatListItem(
            currentUserId = "2",
            chat = ChatItemModel(
                id = "1",
                sessionId = UUID.randomUUID().toString(),
                message = "Chat 1",
                userSender = UserChannelModel(
                    id = "1",
                    userName = "User 1",
                    avatar = "https://randomuser.me/api/port",
                    timestamp = System.currentTimeMillis(),
                ),
                userReceiver = UserChannelModel(
                    id = "2",
                    userName = "User 2",
                    avatar = "https://randomuser.me/api/port",
                    timestamp = System.currentTimeMillis(),
                ),
                time = System.currentTimeMillis()
            )
        )
    }
}