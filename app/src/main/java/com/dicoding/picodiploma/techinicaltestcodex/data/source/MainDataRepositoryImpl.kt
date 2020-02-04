package com.dicoding.picodiploma.techinicaltestcodex.data.source

import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserCommentEntity
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.vo.Resource

interface MainDataRepositoryImpl {

    fun getTopStoryData(): LiveData<Resource<List<UserStoryEntity>>>

    fun getCommentStoryData(list: List<Int>): LiveData<Resource<List<UserCommentEntity>>>
}