package com.dicoding.picodiploma.techinicaltestcodex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.MainDataRepository
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserCommentEntity
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.vo.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

class MainViewModel(private val mainDataRepository: MainDataRepository) : ViewModel() {

    private val parentJob = Job()
    private val childCommentJob = SupervisorJob(parentJob)
    private val childStoryJob = SupervisorJob(parentJob)
    private val listStoryTop = mainDataRepository.getTopStoryData(childStoryJob)
    private var listIdComment = mutableListOf<Int>()
    private var userStoryTitle:  UserStoryEntity? = null



    fun getListStoryTop(): LiveData<Resource<List<UserStoryEntity>>> {
        return listStoryTop
    }

    private val commentList = liveData {
        emitSource(mainDataRepository.getCommentStoryData(listIdComment, childCommentJob))
    }

    fun setUserStoryLive(data: UserStoryEntity?) {
        userStoryTitle = data
    }

    fun getUserStoryLive(): UserStoryEntity? {
        return userStoryTitle
    }

    fun isDataExits(id: Int): Boolean {
        return userStoryTitle?.idStory == id
    }

    fun setListComment(list: List<Int>?) {
        listIdComment.clear()
        list?.let { listIdComment.addAll(it) }
    }

    fun getCommentList(): LiveData<Resource<List<UserCommentEntity>>> {
        return commentList
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
