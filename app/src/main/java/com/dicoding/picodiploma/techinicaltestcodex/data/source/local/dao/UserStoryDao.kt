package com.dicoding.picodiploma.techinicaltestcodex.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity

@Dao
interface UserStoryDao {

    @Query("SELECT * FROM USER_STORY_ENTITY")
    fun getUserTopStory(): LiveData<List<UserStoryEntity>>

}