package app.netlify.dev4rju9.clickretinaassignment.domain.repository

import app.netlify.dev4rju9.clickretinaassignment.domain.model.UserResponse
import app.netlify.dev4rju9.clickretinaassignment.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    fun fetchUserProfile () : Flow<Resource<UserResponse>>

}