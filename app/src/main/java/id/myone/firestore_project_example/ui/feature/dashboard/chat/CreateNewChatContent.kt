package id.myone.firestore_project_example.ui.feature.dashboard.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.chat.ChatItemModel
import id.myone.firestore_project_example.models.session.ChatUserSessionModel
import id.myone.firestore_project_example.ui.feature.dashboard.chat.widget.ChatListItem
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import id.myone.firestore_project_example.ui.widget.ToolbarWidget
import java.util.UUID


@Composable
fun CreateNewChatContent(
    modifier: Modifier = Modifier,
    receiverUser: UserChannelModel?,
    chatItems: List<ChatItemModel>,
    onBackClick: () -> Unit = {},
    chatMessage: String = "",
    onChatMessage: (String) -> Unit = {},
    onCreateNewChat: (String) -> Unit = {}
) {


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToolbarWidget(
                title = receiverUser?.userName.orEmpty(),
                backEnabled = true,
                onBackClick = onBackClick,
                modifier = Modifier.background(Color.White)
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxHeight()
                ) {

                    items(chatItems.size) { index ->
                        ChatListItem(
                            chat = chatItems[index],
                            currentUserId = receiverUser?.id.orEmpty()
                        )
                    }

                }

                Surface(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {

                        OutlinedTextField(
                            value = chatMessage,
                            onValueChange = { onChatMessage(it) },
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Send,
                            ),
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(text = stringResource(R.string.enter_chat_message))
                            },
                            shape = RoundedCornerShape(16.dp)
                        )

                        IconButton(
                            modifier = Modifier.padding(start = 8.dp),
                            onClick = {
                                onCreateNewChat(chatMessage)
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.baseline_send_24),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun CreateNewChatContentPreview() {
    FirestoreprojectexampleTheme {
        CreateNewChatContent(
            receiverUser = UserChannelModel(
                id = "1",
                userName = "User 1",
                avatar = "https://randomuser.me/api/port",
            ),
            chatItems = listOf(
                ChatItemModel(
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
                ),
                ChatItemModel(
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
            ),
        )
    }
}