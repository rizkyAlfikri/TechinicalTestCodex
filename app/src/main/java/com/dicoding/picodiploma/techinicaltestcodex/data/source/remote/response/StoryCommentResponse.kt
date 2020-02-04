package com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class StoryCommentResponse(
    @SerializedName("text")
    var text: String
)