package app.netlify.dev4rju9.clickretinaassignment.data.repository

import android.util.Log
import app.netlify.dev4rju9.clickretinaassignment.data.remote.ProfileApi
import app.netlify.dev4rju9.clickretinaassignment.domain.model.UserResponse
import app.netlify.dev4rju9.clickretinaassignment.domain.repository.UserProfileRepository
import app.netlify.dev4rju9.clickretinaassignment.utils.Resource
import coil.network.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UserProfileRepositoryImpl (
    private val api: ProfileApi
) : UserProfileRepository {

    override fun fetchUserProfile(): Flow<Resource<UserResponse>> = flow {
        emit(Resource.Loading())

        Log.d("x4rju9", "fetching profile")

        try {
            val userProfile = api.fetchUserProfile()
            emit(Resource.Success(userProfile))
            Log.d("x4rju9", "profile fetched, $userProfile")
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
            Log.d("x4rju9", "profile fetch failed")
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.!"
                )
            )
            Log.d("x4rju9", "profile fetch failed")
        }

    }

}