package app.netlify.dev4rju9.taskmanager.view

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.netlify.dev4rju9.taskmanager.view.screens.addtaskscreen.AddTaskScreen
import app.netlify.dev4rju9.taskmanager.view.screens.listscreen.ListScreen
import app.netlify.dev4rju9.taskmanager.view.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val permissions = this@MainActivity.registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted ->
        if (isGranted) {
            Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {

                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                LaunchedEffect(true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                            != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            permissions.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            title = {
                                Text(
                                    text = if (currentRoute == "list_screen") "Task Manager" else "Add Task",
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    },
                    floatingActionButton = {
                        if (currentRoute == "list_screen") {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Task",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(50.dp)
                                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                                    .clickable {
                                        navController.navigate("add_task_screen") {
                                            popUpTo("add_task_screen") {
                                                inclusive = true
                                            }
                                        }
                                    }
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "list_screen",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("list_screen") {
                            ListScreen {
                                navController.navigate("add_task_screen" + "?taskId=${it.date}") {
                                    popUpTo("add_task_screen") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        composable(
                            "add_task_screen" + "?taskId={taskId}",
                            arguments = listOf(
                                navArgument("taskId") {
                                    type = androidx.navigation.NavType.LongType
                                    defaultValue = -1L
                                }
                            )
                        ) {
                            AddTaskScreen {
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}