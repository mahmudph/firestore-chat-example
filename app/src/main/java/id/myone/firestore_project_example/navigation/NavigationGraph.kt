package id.myone.firestore_project_example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.myone.firestore_project_example.ui.feature.dashboard.chanel.LoginChannelScreen
import id.myone.firestore_project_example.ui.feature.dashboard.chat.ChatMasterListScreen
import id.myone.firestore_project_example.ui.feature.dashboard.newchat.CreateNewChatScreen

@Composable
fun NavigationGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(RouteName.LoginChannel) {
            LoginChannelScreen(
                navigateToChatSession = {
                    navController.navigate(RouteName.Chat)
                }
            )
        }

        composable(RouteName.Chat) {
            ChatMasterListScreen(
                navigateToCreateNewChat = {
                    navController.navigate(RouteName.ChatRoom)
                }
            )
        }

        composable(RouteName.ChatRoom) {
            CreateNewChatScreen(
                navigateToChat = {
                    navController.popBackStack()
                }
            )
        }
    }
}