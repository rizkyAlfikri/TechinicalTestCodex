package com.dicoding.picodiploma.techinicaltestcodex.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response.StoryCommentResponse
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.response.UserStoryResponse
import com.dicoding.picodiploma.techinicaltestcodex.rest.ApiEndpoint
import kotlinx.coroutines.*

class RemoteRepository private constructor( private val apiEndpoint: ApiEndpoint) {

    private val exception = CoroutineExceptionHandler { _, throwable ->
        Log.e(RemoteRepository::class.java.simpleName, "${throwable.message}")
    }

    companion object{
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(apiEndpoint: ApiEndpoint): RemoteRepository? {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(apiEndpoint)
            }

            return INSTANCE
        }
    }

    fun getTopStoryRepo(job: Job): LiveData<ApiResponse<List<UserStoryResponse>>> {

        val storyListLive = MutableLiveData<ApiResponse<List<UserStoryResponse>>>()
        val storyList = mutableListOf<UserStoryResponse>()
        CoroutineScope(Dispatchers.IO + exception + job).launch{
            val listIdStory = async { apiEndpoint.getTopStoryIdByApi() }
            for (i in listIdStory.await().indices) {
                storyList.add(apiEndpoint.getStoryByApi(listIdStory.await()[i]))
                if (i >= 20) break
            }
            try {
                storyListLive.postValue(ApiResponse.success(storyList))
            } catch (e: NullPointerException) {
                storyListLive.postValue(ApiResponse.empty("${e.message}", storyList))
            } catch (e: Exception) {
                storyListLive.postValue(ApiResponse.error("${e.message}", storyList))
            }
        }

        return storyListLive
    }

    fun getStoryCommentRepo(list: List<Int>, job: Job): LiveData<ApiResponse<List<StoryCommentResponse>>> {
        val commentListLive = MutableLiveData<ApiResponse<List<StoryCommentResponse>>>()
        val listComment = mutableListOf<StoryCommentResponse>()
        CoroutineScope(Dispatchers.IO + exception + job).launch {
            for (i in list.indices) {
                listComment.add(apiEndpoint.getStoryComment(list[i]))
                if (i >= 5) break
            }

            try {
                commentListLive.postValue(ApiResponse.success(listComment))
            } catch (e: NullPointerException) {
                commentListLive.postValue(ApiResponse.empty("${e.message}", listComment))
            } catch (e: Exception) {
                commentListLive.postValue(ApiResponse.error("${e.message}", listComment))
            }

        }

        return commentListLive
    }
}