package id.myone.firestore_project_example.ui.feature.dashboard.selectUser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.ui.feature.dashboard.selectUser.widget.ListUserItem
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import id.myone.firestore_project_example.ui.widget.ToolbarWithSearchWidget


@Composable
fun ListUserContent(
    modifier: Modifier = Modifier,
    userChannelList: List<UserChannelModel>,
    navigateToChatRoom: (user: UserChannelModel) -> Unit = {},
    navigateToChatMasterList: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ToolbarWithSearchWidget(
                title = "Select User Channel",
                backEnabled = true,
                onBackClick = navigateToChatMasterList,
                modifier = Modifier.background(Color.White),
                onSearchQueryChange = onSearch
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(userChannelList.size) {
                    ListUserItem(
                        channel = userChannelList[it],
                        navigateToChatRoom = navigateToChatRoom
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ListUserContentPreview() {
    FirestoreprojectexampleTheme {
        ListUserContent(
            userChannelList = listOf(
                UserChannelModel(
                    id = "1",
                    userName = "User 1",
                    avatar = "https://randomuser.me/api/port"
                ),
                UserChannelModel(
                    id = "1",
                    userName = "User 1",
                    avatar = "https://randomuser.me/api/port"
                ),
                UserChannelModel(
                    id = "1",
                    userName = "User 1",
                    avatar = "https://randomuser.me/api/port"
                ),
            ),
            navigateToChatRoom = {},
            navigateToChatMasterList = {}
        )
    }
}