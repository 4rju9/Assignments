package app.netlify.dev4rju9.enginifyassignment.data.repository

import app.netlify.dev4rju9.enginifyassignment.data.local.PostDao
import app.netlify.dev4rju9.enginifyassignment.data.local.PostEntity
import app.netlify.dev4rju9.enginifyassignment.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao
) {

    val posts: Flow<List<PostEntity>> = postDao.getAllPosts()

    suspend fun fetchAndSavePosts() {
        val apiData = apiService.getPosts().map {
            PostEntity(userId = it.userId, id = it.id, title = it.title, body = it.body)
        }
        postDao.insertAll(apiData)
    }

    suspend fun delete(post: PostEntity) = postDao.deletePost(post)

    suspend fun update(post: PostEntity) = postDao.updatePost(post)

    suspend fun clearAll() = postDao.clearAll()

    fun search(query: String): Flow<List<PostEntity>> = postDao.searchPosts(query)
}