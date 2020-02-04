package com.dicoding.picodiploma.techinicaltestcodex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.techinicaltestcodex.ui.main.MainViewModel
import com.dicoding.picodiploma.techinicaltestcodex.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel? = null

    companion object{
        private fun iniViewModel(activity: AppCompatActivity): MainViewModel? {
            val factory = ViewModelFactory.getInstance(activity.application)
            return factory?.let { ViewModelProvider(activity, it).get(MainViewModel::class.java) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mainViewModel = iniViewModel(this)
    }

}
