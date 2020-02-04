package com.dicoding.picodiploma.techinicaltestcodex.data.source

import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserCommentEntity
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.vo.Resource
import kotlinx.coroutines.Job

interface MainDataRepositoryImpl {

    fun getTopStoryData(job: Job): LiveData<Resource<List<UserStoryEntity>>>

    fun getCommentStoryData(list: List<Int>, job: Job): LiveData<Resource<List<UserCommentEntity>>>
}