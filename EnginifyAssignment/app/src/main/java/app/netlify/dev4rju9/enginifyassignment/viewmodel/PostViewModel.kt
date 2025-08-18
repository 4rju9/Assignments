package app.netlify.dev4rju9.enginifyassignment.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.enginifyassignment.data.local.PostEntity
import app.netlify.dev4rju9.enginifyassignment.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var isError by mutableStateOf(false)
    var searchQuery by mutableStateOf("")

    private val _posts = MutableStateFlow<List<PostEntity>>(emptyList())
    val posts: StateFlow<List<PostEntity>> = _posts

    init {
        getPosts()
    }

    fun getPosts(forceRefresh: Boolean = false) = viewModelScope.launch {
        try {
            isLoading = true
            isError = false

            val hasLocalData = repository.posts.first().isNotEmpty()

            if (!hasLocalData || forceRefresh) {
                repository.clearAll()
                repository.fetchAndSavePosts()
            }

            repository.posts.collect { list ->
                _posts.value = list
                isLoading = false
            }

        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    fun deletePost(post: PostEntity) = viewModelScope.launch {
        repository.delete(post)
    }

    fun updatePost(post: PostEntity) = viewModelScope.launch {
        repository.update(post)
    }

    fun searchPosts(query: String) {
        searchQuery = query
        viewModelScope.launch {
            if (query.isBlank()) {
                repository.posts.collect { _posts.value = it }
            } else {
                repository.search(query).collect { _posts.value = it }
            }
        }
    }
}