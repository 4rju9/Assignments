package app.netlify.dev4rju9.enginifyassignment

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import app.netlify.dev4rju9.enginifyassignment.ui.theme.EnginifyAssignmentTheme
import app.netlify.dev4rju9.enginifyassignment.view.components.MainScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PostsApp() {
    val darkTheme = isSystemInDarkTheme()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
    }

    EnginifyAssignmentTheme(darkTheme = darkTheme) {
        MainScreen()
    }
}
