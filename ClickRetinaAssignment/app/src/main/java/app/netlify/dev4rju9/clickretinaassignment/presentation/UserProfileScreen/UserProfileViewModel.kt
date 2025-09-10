package app.netlify.dev4rju9.clickretinaassignment.presentation.UserProfileScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.clickretinaassignment.domain.model.UserResponse
import app.netlify.dev4rju9.clickretinaassignment.domain.user_case.GetUserProfile
import app.netlify.dev4rju9.clickretinaassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor (
    getUserProfile: GetUserProfile
) : ViewModel() {

    private val _state = mutableStateOf<UserResponse?>(null)
    val state = _state
    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading
    private val _error = mutableStateOf("")
    val error = _error

    init {
        getUserProfile().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _state.value = result.data
                    _error.value = ""
                    Log.d("x4rju9", "User: ${result.data}")
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _error.value = result.message ?: "An unexpected error occurred"
                    Log.d("x4rju9", "Error: ${result.message}")
                }
                is Resource.Loading -> {
                    _isLoading.value = true
                    Log.d("x4rju9", "Loading...")
                }
            }
        }.launchIn(viewModelScope)
    }

}