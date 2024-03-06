package id.myone.firestore_project_example.ui.feature.dashboard.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.chat.ChatUserSessionModel
import id.myone.firestore_project_example.ui.feature.dashboard.chat.widget.AvatarProfile
import id.myone.firestore_project_example.ui.feature.dashboard.chat.widget.ChatUserSessionListItem
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import id.myone.firestore_project_example.ui.widget.ToolbarWidget


@Composable
fun ChatMasterListContent(
    modifier: Modifier = Modifier,
    userChannelList: List<UserChannelModel>,
    chatMasterList: List<ChatUserSessionModel>,
    navigateToSelectUser: () -> Unit = {},
    navigateToCreateChat: (userId: String) -> Unit = {}
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToolbarWidget(
                title = "Chat",
                backEnabled = false,
                actionIcon = R.drawable.baseline_chat_24,
                onActionClick = { navigateToSelectUser() },
                modifier = Modifier.background(Color.White)
            )
            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    ) {
                        items(userChannelList) { user ->
                            AvatarProfile(
                                modifier = Modifier.padding(end = 4.dp),
                                user = user,
                                onClick = {
                                    navigateToCreateChat(user.id)
                                }
                            )
                        }
                    }
                }

                items(chatMasterList) { chat ->
                    ChatUserSessionListItem(chat = chat)
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun ChatMasterListContentPreview() {
    FirestoreprojectexampleTheme {
        ChatMasterListContent(
            userChannelList = listOf(
                UserChannelModel(
                    id = "1",
                    userName = "John Doe",
                    avatar = "https://www.w3schools.com/howto/img_avatar.png",
                ),
                UserChannelModel(
                    id = "1",
                    userName = "John Doe",
                    avatar = "https://www.w3schools.com/howto/img_avatar.png",
                ),
                UserChannelModel(
                    id = "1",
                    userName = "John Doe",
                    avatar = "https://www.w3schools.com/howto/img_avatar.png",
                ),
            ),
            chatMasterList = listOf(
                ChatUserSessionModel(
                    chatId = "1",
                    userId = "2121212",
                    username = "John Doe",
                    userAvatar = "https://www.w3schools.com/howto/img_avatar.png",
                ),
                ChatUserSessionModel(
                    chatId = "1",
                    userId = "2121212",
                    username = "John Doe",
                    userAvatar = "https://www.w3schools.com/howto/img_avatar.png",
                )
            )
        )
    }
}