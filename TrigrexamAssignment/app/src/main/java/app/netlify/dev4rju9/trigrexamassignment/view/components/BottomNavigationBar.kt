package app.netlify.dev4rju9.trigrexamassignment.view.components

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Default.Home)
    object Food : BottomNavItem("Food", Icons.Default.Fastfood)
    object Orders : BottomNavItem("Orders", Icons.Default.List)
    object Settings : BottomNavItem("Settings", Icons.Default.Settings)
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Food,
        BottomNavItem.Orders,
        BottomNavItem.Settings
    )

    val context = LocalContext.current

    NavigationBar(
        tonalElevation = 6.dp,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            NavigationBarItem (
                selected = item == BottomNavItem.Home,
                onClick = {
                    if (item != BottomNavItem.Home) Toast.makeText(context, "${item.title} is not implemented", Toast.LENGTH_SHORT).show()
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}