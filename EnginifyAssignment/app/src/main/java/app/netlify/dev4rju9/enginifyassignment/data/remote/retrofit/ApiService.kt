package app.netlify.dev4rju9.enginifyassignment.data.remote

import app.netlify.dev4rju9.enginifyassignment.data.local.PostEntity
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<PostEntity>
}