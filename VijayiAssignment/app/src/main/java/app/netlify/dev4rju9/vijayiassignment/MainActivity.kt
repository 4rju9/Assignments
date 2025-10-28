package app.netlify.dev4rju9.vijayiassignment.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.netlify.dev4rju9.vijayiassignment.ui.theme.VijayiAssignmentTheme
import app.netlify.dev4rju9.vijayiassignment.view.details.DetailScreen
import app.netlify.dev4rju9.vijayiassignment.view.homescreen.HomeScreen
import app.netlify.dev4rju9.vijayiassignment.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VijayiAssignmentTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost () {

    val viewModel = koinViewModel<MainViewModel>()
    val navController = rememberNavController()

    NavHost (
        navController = navController,
        startDestination = "home_screen"
    ) {
        composable ("home_screen") {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = "detail/{name}/{description}/{imageUrl}",
        ) { backStack ->

            val name = URLDecoder.decode(backStack.arguments?.getString("name") ?: "", "UTF-8")
            val description = URLDecoder.decode(backStack.arguments?.getString("description") ?: "", "UTF-8")
            val imageUrl = URLDecoder.decode(backStack.arguments?.getString("imageUrl") ?: "", "UTF-8")

            DetailScreen(name, description, imageUrl)
        }

    }

}