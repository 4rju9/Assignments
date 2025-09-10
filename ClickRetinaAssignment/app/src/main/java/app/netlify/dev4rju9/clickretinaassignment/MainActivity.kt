package app.netlify.dev4rju9.clickretinaassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import app.netlify.dev4rju9.clickretinaassignment.presentation.UserProfileScreen.UserProfileScreen
import app.netlify.dev4rju9.clickretinaassignment.ui.theme.ClickRetinaAssignmentTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClickRetinaAssignmentTheme {

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = false

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }

                UserProfileScreen()
            }
        }
    }
}