package com.dicoding.picodiploma.techinicaltestcodex.data.source.local

import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.dao.UserStoryDao
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity

class LocalRepository private constructor(private val userStoryDao: UserStoryDao) {

    companion object{
        private var INSTANCE: LocalRepository? = null

        fun getInstance(userStoryDao: UserStoryDao): LocalRepository? {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(userStoryDao)
            }

            return INSTANCE
        }
    }

    fun getTopStoryByLocal(): LiveData<List<UserStoryEntity>> {
        return userStoryDao.getUserTopStory()
    }
}