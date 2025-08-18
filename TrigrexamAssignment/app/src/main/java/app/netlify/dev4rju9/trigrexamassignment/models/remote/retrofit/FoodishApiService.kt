package app.netlify.dev4rju9.trigrexamassignment.models.remote.retrofit

import app.netlify.dev4rju9.trigrexamassignment.models.FoodImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodishApiService {

    @GET("api/")
    suspend fun getRandomFoodImage(): FoodImageResponse

    @GET("api/images/{category}")
    suspend fun getCategorisedFoodImage(
        @Path("category") category: String
    ): FoodImageResponse
}