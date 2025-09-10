package app.netlify.dev4rju9.clickretinaassignment.domain.user_case

import app.netlify.dev4rju9.clickretinaassignment.domain.model.UserResponse
import app.netlify.dev4rju9.clickretinaassignment.domain.repository.UserProfileRepository
import app.netlify.dev4rju9.clickretinaassignment.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetUserProfile (
    private val repository: UserProfileRepository
) {

    operator fun invoke () : Flow<Resource<UserResponse>> = repository.fetchUserProfile()

}