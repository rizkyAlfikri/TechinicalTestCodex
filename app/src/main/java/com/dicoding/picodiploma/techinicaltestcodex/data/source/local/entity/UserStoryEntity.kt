package com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserStoryEntity(
    var author: String?,
    var descendants: String?,
    var idStory: Int,
    var kids: List<Int>?,
    var score: String?,
    var title: String?,
    var favorite: Boolean
) : Parcelable