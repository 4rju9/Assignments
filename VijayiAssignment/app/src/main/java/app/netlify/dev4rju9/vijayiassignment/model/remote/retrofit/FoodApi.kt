package app.netlify.dev4rju9.vijayiassignment.model.remote.retrofit

import app.netlify.dev4rju9.vijayiassignment.model.DishList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface FoodApi {

    @GET("/assignment/trending")
    suspend fun getTrendingDishes(): DishList

    @GET("/assignment/butterchicken")
    fun getButterChickens(): Single<DishList>

    @GET("/assignment/burger")
    fun getBurgers(): Single<DishList>

    @GET("/assignment/pizza")
    fun getPizzas(): Single<DishList>

    @GET("/assignment/pasta")
    fun getPastas(): Single<DishList>

    @GET("/assignment/dosa")
    fun getDosas(): Single<DishList>

}