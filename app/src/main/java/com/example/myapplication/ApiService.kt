package com.example.myapplication

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getposts():List<Post>
}