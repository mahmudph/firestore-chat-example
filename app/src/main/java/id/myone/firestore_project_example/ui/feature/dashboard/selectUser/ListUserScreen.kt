package id.myone.firestore_project_example.ui.feature.dashboard.selectUser

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.viewmodels.ChannelViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun ListUserScreen(
    modifier: Modifier = Modifier,
    viewModel: ChannelViewModel = getViewModel(),
    navigateToChatRoom: (user: UserChannelModel) -> Unit = {},
    navigateToChatMasterList: () -> Unit = {}
) {

    val userChannelList by viewModel.channelListData.collectAsState(null)

    Scaffold {
        ListUserContent(
            modifier = modifier.padding(it),
            userChannelList = userChannelList?.data ?: emptyList(),
            navigateToChatRoom = navigateToChatRoom,
            navigateToChatMasterList = navigateToChatMasterList,
            onSearch = {
                viewModel.searchUserChannel(it)
            }
        )
    }
}

