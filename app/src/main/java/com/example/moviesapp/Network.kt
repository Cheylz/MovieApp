package com.example.moviesapp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

@Serializable
data class Post(val id: Int, val title: String, val body: String)

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

object Network {
    private val json = Json { ignoreUnknownKeys = true }

    val postsApi: PostsApi = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(PostsApi::class.java)
}