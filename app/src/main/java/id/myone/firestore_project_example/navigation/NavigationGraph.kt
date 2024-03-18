package id.myone.firestore_project_example.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.myone.firestore_project_example.models.session.CreateSessionInfo
import id.myone.firestore_project_example.ui.feature.dashboard.chanel.LoginChannelScreen
import id.myone.firestore_project_example.ui.feature.dashboard.session.ChatMasterListScreen
import id.myone.firestore_project_example.ui.feature.dashboard.chat.CreateNewChatScreen
import id.myone.firestore_project_example.ui.feature.dashboard.selectUser.ListUserScreen
import id.myone.firestore_project_example.viewmodels.ChatUserViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = startDestination) {

        composable(RouteName.LoginChannel) {
            LoginChannelScreen(
                navigateToChatSession = {
                    navController.navigate(RouteName.Chat) {
                        popUpTo(RouteName.LoginChannel) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(RouteName.Chat) {
            ChatMasterListScreen(
                navigateToCreateNewChat = { session ->

                    navController.currentBackStackEntry?.savedStateHandle?.set("session", session)
                    navController.navigate(RouteName.ChatRoom)
                },
                navigateToSelectUser = {
                    navController.navigate(RouteName.SelectUser)
                },
                navigateToProfile = {

                }
            )
        }

        composable(
            RouteName.ChatRoom,
        ) {


            val user =
                navController.previousBackStackEntry?.savedStateHandle?.get<CreateSessionInfo>("session")

            CreateNewChatScreen(
                viewModel = getViewModel<ChatUserViewModel>(
                    parameters = { parametersOf(user) },
                ),
                navigateToChat = { navController.popBackStack() }
            )
        }

        composable(RouteName.SelectUser) {
            ListUserScreen(
                navigateToChatMasterList = {
                    navController.popBackStack()
                },
                navigateToChatRoom = { user ->

                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "session", CreateSessionInfo(
                            sessionId = "", user = user
                        )
                    )

                    navController.navigate(RouteName.ChatRoom) {
                        popUpTo(RouteName.ChatRoom) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(RouteName.Profile) {
            Surface {

            }
        }
    }
}