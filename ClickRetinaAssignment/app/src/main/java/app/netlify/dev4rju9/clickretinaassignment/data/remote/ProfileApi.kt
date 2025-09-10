package app.netlify.dev4rju9.clickretinaassignment.data.remote

import app.netlify.dev4rju9.clickretinaassignment.domain.model.UserResponse
import retrofit2.http.GET

interface ProfileApi {

    @GET("/android-assesment/profile/refs/heads/main/data.json")
    suspend fun fetchUserProfile(): UserResponse

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
    }

}