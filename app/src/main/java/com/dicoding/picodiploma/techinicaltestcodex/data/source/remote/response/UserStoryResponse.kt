package com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class UserStoryResponse(
    @SerializedName("by")
    var `by`: String,
    @SerializedName("descendants")
    var descendants: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("kids")
    var kids: List<Int>,
    @SerializedName("score")
    var score: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("url")
    var url: String
)