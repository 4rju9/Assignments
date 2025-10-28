package app.netlify.dev4rju9.vijayiassignment.model.repository

import app.netlify.dev4rju9.vijayiassignment.model.remote.retrofit.FoodApi

class MainRepository (
    private val api: FoodApi
) {

    suspend fun getTrending () = api.getTrendingDishes()
    fun getButterChickens () = api.getButterChickens()
    fun getBurgers() = api.getBurgers()
    fun getPizzas () = api.getPizzas()
    fun getPastas () = api.getPastas()
    fun getDosas () = api.getDosas()

}