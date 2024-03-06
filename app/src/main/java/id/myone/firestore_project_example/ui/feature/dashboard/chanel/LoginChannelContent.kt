package id.myone.firestore_project_example.ui.feature.dashboard.chanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme


@Composable
fun LoginChannelContent(
    modifier: Modifier = Modifier,
    createNewUserChannel: (String) -> Unit,
) {

    var userName by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = userName,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your username") },
                onValueChange = {
                    userName = it
                }
            )

            ElevatedButton(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    createNewUserChannel(userName)
                }

            ) {
                Text(
                    text = "Login Chat Channel",
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun LoginChannelContentPreview() {
    FirestoreprojectexampleTheme {
        LoginChannelContent(
            createNewUserChannel = {

            }
        )
    }
}