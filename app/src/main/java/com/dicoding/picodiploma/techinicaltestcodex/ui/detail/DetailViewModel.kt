package com.dicoding.picodiploma.techinicaltestcodex.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.techinicaltestcodex.data.source.MainDataRepository
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserCommentEntity
import com.dicoding.picodiploma.techinicaltestcodex.vo.Resource

class DetailViewModel(mainDataRepository: MainDataRepository) : ViewModel() {

    private var listIdComment = mutableListOf<Int>()

    private val commentList = liveData {
        emitSource(mainDataRepository.getCommentStoryData(listIdComment))
    }

    fun setListComment(list: List<Int>) {
        listIdComment.addAll(list)
    }

    fun getCommentList(): LiveData<Resource<List<UserCommentEntity>>> {
        return commentList
    }

}