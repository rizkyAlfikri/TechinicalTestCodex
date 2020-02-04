package com.dicoding.picodiploma.techinicaltestcodex.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.techinicaltestcodex.data.source.MainDataRepository
import com.dicoding.picodiploma.techinicaltestcodex.di.Injection
import com.dicoding.picodiploma.techinicaltestcodex.ui.MainViewModel

class ViewModelFactory private constructor(private val mainDataRepository: MainDataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                INSTANCE = Injection.provideService(application)?.let {
                    ViewModelFactory(it)
                }
            }

            return INSTANCE
        }
    }

    @Suppress("UNCHECKED_CAST")
    @NonNull
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                mainDataRepository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class ${modelClass.name}")
        }
    }


}