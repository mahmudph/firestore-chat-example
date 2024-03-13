package id.myone.firestore_project_example.ui.feature.dashboard.selectUser.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme
import java.util.UUID


@Composable
fun ListUserItem(
    modifier: Modifier = Modifier,
    channel: UserChannelModel,
    navigateToChatRoom: (user: UserChannelModel) -> Unit = {},
) {

    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 3.dp,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navigateToChatRoom(channel)
            },
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(channel.avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .border(1.dp, color = Color.Red, shape = CircleShape)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
                    .wrapContentSize(align = Alignment.CenterStart)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    text = channel.userName,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Text(
                    text = channel.timestamp.toString(),
                    style = TextStyle(fontSize = 10.sp, color = Color.Gray),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ListUserItemPreview() {
    FirestoreprojectexampleTheme {
        ListUserItem(
            channel = UserChannelModel(
                id = UUID.randomUUID().toString(),
                userName = "John Doe",
                avatar = "https://www.w3schools.com/howto/img_avatar.png",
            )
        )
    }
}