package id.myone.firestore_project_example.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.firestore_project_example.R

@Composable
fun ToolbarWidget(
    modifier: Modifier = Modifier,
    title: String,
    backEnabled: Boolean = false,
    @DrawableRes actionIcon: Int? = null,
    onBackClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    Surface(
        tonalElevation = 3.dp,
        shadowElevation = 3.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (backEnabled) {

                    Image(
                        painter = painterResource(
                            id = R.drawable.baseline_arrow_back_24
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(
                                onClick = onBackClick
                            )
                    )
                }
                Text(
                    text = title, modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.W600,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (actionIcon != null) {
                Image(
                    painter = painterResource(id = actionIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = onActionClick)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ToolbarWidgetPreview() {
    ToolbarWidget(
        title = "Chat",
        backEnabled = false,
        actionIcon = R.drawable.baseline_chat_24
    )
}