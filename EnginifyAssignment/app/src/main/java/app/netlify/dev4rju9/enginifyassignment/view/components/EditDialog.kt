package app.netlify.dev4rju9.enginifyassignment.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.netlify.dev4rju9.enginifyassignment.data.local.PostEntity

@Composable
fun EditDialog(
    post: PostEntity,
    onDismiss: () -> Unit,
    onSave: (PostEntity) -> Unit
) {
    var title by remember { mutableStateOf(post.title) }
    var body by remember { mutableStateOf(post.body) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onSave(post.copy(title = title, body = body))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Edit Post") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("Body") })
            }
        }
    )
}