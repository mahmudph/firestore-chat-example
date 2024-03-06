package id.myone.firestore_project_example.ui.feature.dashboard.chat.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.myone.firestore_project_example.R
import id.myone.firestore_project_example.models.channel.UserChannelModel


@Composable
fun AvatarProfile(
    modifier: Modifier = Modifier,
    user: UserChannelModel,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.size(75.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatar)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .size(50.dp, 50.dp)
                .border(1.dp, color = Color.Gray, shape = CircleShape)
                .clip(CircleShape)
                .clickable { onClick() },
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
        )

        Text(
            text = user.userName,
            color = Color.Gray,
            fontSize = 10.sp,
            softWrap = true,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun AvatarProfilePreview() {
    AvatarProfile(
        user = UserChannelModel(
            userName = "User Name",
            avatar = "https://www.w3schools.com/howto/img_avatar.png"
        )
    )
}