package app.netlify.dev4rju9.enginifyassignment.view.components

import android.graphics.Outline
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import app.netlify.dev4rju9.enginifyassignment.data.local.PostEntity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import app.netlify.dev4rju9.enginifyassignment.viewmodel.PostViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(viewModel: PostViewModel = hiltViewModel()) {

    val posts by viewModel.posts.collectAsState()
    val isLoading = viewModel.isLoading
    val swipeState = rememberSwipeRefreshState(isLoading)

    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<PostEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            value = viewModel.searchQuery,
            onValueChange = { viewModel.searchPosts(it) },
            label = { Text("Search...", color = MaterialTheme.colorScheme.primary) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { viewModel.searchPosts(viewModel.searchQuery) }
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        SwipeRefresh(
            state = swipeState,
            onRefresh = { viewModel.getPosts(forceRefresh = true) }) {

            if (viewModel.isError) {
                Text("Error Loading Data", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(posts) { post ->
                        PostItem(
                            post = post,
                            onDelete = { viewModel.deletePost(post) },
                            onEdit = {
                                selectedPost = post
                                isDialogOpen = true
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    if (isDialogOpen) {
        EditDialog(
            post = selectedPost!!,
            onDismiss = { isDialogOpen = false },
            onSave = {
                viewModel.updatePost(it)
                isDialogOpen = false
            }
        )
    }
}