package id.myone.firestore_project_example.ui.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.myone.firestore_project_example.navigation.NavigationGraph
import id.myone.firestore_project_example.navigation.RouteName
import id.myone.firestore_project_example.ui.theme.FirestoreprojectexampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            FirestoreprojectexampleTheme {
                NavigationGraph(startDestination = RouteName.LoginChannel)
            }
        }
    }
}

