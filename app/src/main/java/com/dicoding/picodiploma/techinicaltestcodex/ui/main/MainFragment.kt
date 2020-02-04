package com.dicoding.picodiploma.techinicaltestcodex.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.techinicaltestcodex.R
import com.dicoding.picodiploma.techinicaltestcodex.databinding.MainFragmentBinding
import com.dicoding.picodiploma.techinicaltestcodex.ui.detail.DetailActivity
import com.dicoding.picodiploma.techinicaltestcodex.utils.invisible
import com.dicoding.picodiploma.techinicaltestcodex.utils.visible
import com.dicoding.picodiploma.techinicaltestcodex.viewmodel.ViewModelFactory
import com.dicoding.picodiploma.techinicaltestcodex.vo.Status.*

class MainFragment : Fragment() {

    private var mainViewModel: MainViewModel? = null
    private lateinit var binding: MainFragmentBinding
    private lateinit var mainAdapter: MainAdapter


    companion object {

        const val EXTRA_TITLE = "EXTRA_TITLE"

        fun initViewModel(activity: FragmentActivity): MainViewModel? {
            val factory = ViewModelFactory.getInstance(activity.application)
            return factory?.let { ViewModelProvider(activity, it).get(MainViewModel::class.java) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = initViewModel(requireActivity())

        mainAdapter = MainAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_STORY, it)
            startActivityForResult(intent, DetailActivity.REQUEST_CODE)
        }

        binding.rvUserStory.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainAdapter
        }

        populateData()
    }

    private fun populateData() {
        mainViewModel?.getListStoryTop()?.observe(viewLifecycleOwner, Observer { resource ->

            when (resource.status) {

                LOADING -> binding.progressBar.visible()

                SUCCESS -> {
                    binding.progressBar.invisible()

                    resource.body?.let {
                        mainAdapter.setDataUser(it)
                    }
                }

                ERROR -> {
                    binding.progressBar.invisible()
                    Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailActivity.REQUEST_CODE) {
            if (resultCode == DetailActivity.RESULT_ADD) {
                val title = data?.getStringExtra(EXTRA_TITLE)
                title?.let { binding.tvTitleFavorite.text = it }
            }
        }
    }
}
