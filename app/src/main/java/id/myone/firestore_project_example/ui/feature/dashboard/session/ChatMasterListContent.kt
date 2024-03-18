package id.myone.firestore_project_example.ui.feature.dashboard.session

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.extensions.mapToUser
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.models.session.ChatUserSessionModel
import id.myone.firestore_project_example.models.session.CreateSessionInfo
import id.myone.firestore_project_example.ui.feature.dashboard.session.widget.AvatarProfile
import id.myone.firestore_project_example.ui.feature.dashboard.session.widget.ChatUserSessionListItem
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import id.myone.firestore_project_example.ui.widget.ToolbarWidget
import java.util.UUID


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatMasterListContent(
    modifier: Modifier = Modifier,
    userId: String,
    userChannelList: List<UserChannelModel>,
    chatMasterList: List<ChatUserSessionModel>,
    chatMasterPagingList: LazyPagingItems<ChatUserSessionModel>? = null,
    deleteSession: (sessionId: String) -> Unit = {},
    navigateToProfile: () -> Unit = {},
    navigateToCreateChat: (session: CreateSessionInfo) -> Unit = {}
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToolbarWidget(
                title = "Chat",
                backEnabled = false,
                actionIcon = R.drawable.baseline_person_24,
                onActionClick = { navigateToProfile() },
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
                                    navigateToCreateChat(
                                        CreateSessionInfo("", user)
                                    )
                                }
                            )
                        }
                    }
                }

                items(chatMasterList.size) { index ->
                    ChatUserSessionListItem(
                        sessionId = chatMasterList[index].id,
                        user = if (chatMasterList[index].sender.id == userId) chatMasterList[index].receiver else chatMasterList[index].sender,
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                navigateToCreateChat(
                                    CreateSessionInfo(
                                        chatMasterList[index].id,
                                        chatMasterList[index].mapToUser(
                                            chatMasterList[index].sender.id == userId
                                        )
                                    )
                                )
                            },
                            onLongClick = {
                                deleteSession(chatMasterList[index].id)
                            }
                        )
                    )
                }
//
//                items(chatMasterPagingList?.itemCount ?: 0) { index ->
//                    ChatUserSessionListItem(
//                        sessionId = chatMasterPagingList?.get(index)!!.id,
//                        user = if (chatMasterPagingList[index]!!.sender.id == userId) chatMasterPagingList[index]!!.receiver else chatMasterPagingList[index]!!.sender,
//                        modifier = Modifier.combinedClickable(
//                            onClick = {
//                                navigateToCreateChat(
//                                    CreateSessionInfo(
//                                        chatMasterPagingList[index]!!.id,
//                                        chatMasterPagingList[index]!!.mapToUser(
//                                            chatMasterPagingList[index]!!.sender.id == userId
//                                        )
//                                    )
//                                )
//                            },
//                            onLongClick = {
//                                deleteSession(chatMasterPagingList[index]!!.id)
//                            }
//                        )
//                    )
//                }

//                chatMasterPagingList?.apply {
//                    when {
//                        loadState.refresh is LoadState.Loading -> {
//                            item {
//                                CircularProgressIndicator(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .wrapContentHeight()
//                                        .padding(16.dp)
//                                )
//                            }
//                        }
//
//                        loadState.refresh is LoadState.Error -> {
//                            val error = chatMasterPagingList.loadState.refresh as LoadState.Error
//                            item {
//                                Text(text = error.error.localizedMessage!!)
//                            }
//                        }
//
//                        loadState.append is LoadState.Loading -> {
//                            item {
//                                CircularProgressIndicator(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .wrapContentHeight()
//                                        .padding(16.dp)
//                                )
//                            }
//                        }
//
//                        loadState.append is LoadState.Error -> {
//                            val error = chatMasterPagingList.loadState.append as LoadState.Error
//                            item {
//                                Text(text = error.error.localizedMessage!!)
//                            }
//                        }
//                    }
//                }
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
                    sender = ChatUserSessionModel.User(
                        id = "1",
                        userName = "Jhon doe",
                        avatar = "https://www.w3schools.com/howto/img_avatar.png",
                    ),
                    receiver = ChatUserSessionModel.User(
                        id = "1",
                        userName = "Jhon doe",
                        avatar = "https://www.w3schools.com/howto/img_avatar.png",
                    ),
                    id = UUID.randomUUID().toString()
                )
            ),
            userId = "1",
        )
    }
}