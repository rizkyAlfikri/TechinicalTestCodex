package com.dicoding.picodiploma.techinicaltestcodex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserCommentEntity
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.ApiResponse
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response.StoryCommentResponse
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response.UserStoryResponse
import com.dicoding.picodiploma.techinicaltestcodex.vo.Resource
import kotlinx.coroutines.Job

class MainDataRepository private constructor(
    private val remote: RemoteRepository
) : MainDataRepositoryImpl {

    companion object {
        @Volatile
        private var INSTANCE: MainDataRepository? = null

        fun getInstance(
            remoteRepository: RemoteRepository
        ): MainDataRepository? {
            if (INSTANCE == null) {
                INSTANCE =
                    MainDataRepository(
                        remoteRepository
                    )
            }

            return INSTANCE
        }
    }

    override fun getTopStoryData(job: Job): LiveData<Resource<List<UserStoryEntity>>> {
        return object : NetworkBounceResource<List<UserStoryEntity>, List<UserStoryResponse>>() {

            override fun loadFromDB(): LiveData<List<UserStoryEntity>> {
                val listDummy = MutableLiveData<List<UserStoryEntity>>()
                listDummy.value = null
                return listDummy
            }

            override fun shouldFetch(data: List<UserStoryEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<List<UserStoryResponse>>>? {
                return remote.getTopStoryRepo(job)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: List<UserStoryResponse>,
                dbData: List<UserStoryEntity>?
            ): List<UserStoryEntity>? {

                val listStory = mutableListOf<UserStoryEntity>()

                data.forEach {
                    listStory.add(
                        UserStoryEntity(
                            it.by,
                            "${it.descendants}",
                            it.id,
                            it.kids,
                            "${it.score}",
                            it.title,
                            false
                        )
                    )
                }

                return listStory
            }

            override fun saveCallResult(data: List<UserStoryResponse>) {
                // do nothing, because we are not going to save all data
            }
        }.asLiveData()

    }

    override fun getCommentStoryData(list: List<Int>, job: Job): LiveData<Resource<List<UserCommentEntity>>> {
        return object :
            NetworkBounceResource<List<UserCommentEntity>, List<StoryCommentResponse>>() {
            override fun loadFromDB(): LiveData<List<UserCommentEntity>> {
                val dummyDataFromDb = MutableLiveData<List<UserCommentEntity>>()
                dummyDataFromDb.value = null
                return  dummyDataFromDb
            }

            override fun shouldFetch(data: List<UserCommentEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<List<StoryCommentResponse>>>? {
                return remote.getStoryCommentRepo(list, job)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: List<StoryCommentResponse>,
                dbData: List<UserCommentEntity>?
            ): List<UserCommentEntity>? {
                val listStoryComment = mutableListOf<UserCommentEntity>()

                data.forEach {
                    listStoryComment.add(UserCommentEntity(
                        it.text
                    ))
                }

                return listStoryComment
            }

            override fun saveCallResult(data: List<StoryCommentResponse>) {
                // do nothing, because we are not going to save all data
            }
        }.asLiveData()
    }


}