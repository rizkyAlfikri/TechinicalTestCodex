package com.dicoding.picodiploma.techinicaltestcodex.rest

import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response.StoryCommentResponse
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response.UserStoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndpoint {

    @GET("topstories.json?print=pretty")
    suspend fun getTopStoryIdByApi(): List<Int>

    @GET("item/{storyId}.json?print=pretty")
    suspend fun getStoryByApi(@Path("storyId") storyId: Int): UserStoryResponse

    @GET("item/{commentId}.json?print=pretty")
    suspend fun getStoryComment(@Path("commentId") commentId: Int): StoryCommentResponse

}