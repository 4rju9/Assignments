package app.netlify.dev4rju9.trigrexamassignment.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.netlify.dev4rju9.trigrexamassignment.ui.theme.TrigrexamAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrigrexamAssignmentTheme {
                MainScreen()
            }
        }
    }
}
