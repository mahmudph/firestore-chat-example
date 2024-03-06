package id.myone.firestore_project_example.ui.feature.dashboard.newchat.widget


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.chat.ChatItemModel
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme


@Composable
fun ChatListItem(
    modifier: Modifier = Modifier,
    chat: ChatItemModel,
) {
    Box(modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(chat.userProfile)
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
                Text(
                    text = chat.message,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun ChatListItemPreview() {
    FirestoreprojectexampleTheme {
        ChatListItem(
            chat = ChatItemModel(
                id = "1",
                message = "Chat 1",
                userName = "User 1",
                userProfile = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                time = "2021-10-10T10:10:10.10Z"
            )
        )
    }
}