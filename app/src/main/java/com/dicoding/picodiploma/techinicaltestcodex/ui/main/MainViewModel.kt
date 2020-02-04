package com.dicoding.picodiploma.techinicaltestcodex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.techinicaltestcodex.data.source.MainDataRepository
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.vo.Resource

class MainViewModel(private val mainDataRepository: MainDataRepository) : ViewModel() {

    private val listStoryTop = mainDataRepository.getTopStoryData()

    fun getListStoryTop(): LiveData<Resource<List<UserStoryEntity>>> {
        return listStoryTop
    }
}
