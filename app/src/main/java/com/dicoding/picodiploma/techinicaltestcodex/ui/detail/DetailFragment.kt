package com.dicoding.picodiploma.techinicaltestcodex.ui.detail


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
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.techinicaltestcodex.R
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.databinding.FragmentDetailBinding
import com.dicoding.picodiploma.techinicaltestcodex.ui.MainViewModel
import com.dicoding.picodiploma.techinicaltestcodex.utils.invisible
import com.dicoding.picodiploma.techinicaltestcodex.utils.visible
import com.dicoding.picodiploma.techinicaltestcodex.viewmodel.ViewModelFactory
import com.dicoding.picodiploma.techinicaltestcodex.vo.Status


class DetailFragment : Fragment() {


    private lateinit var commentAdapter: UserCommentAdapter
    private var mainViewModel: MainViewModel? = null
    private lateinit var binding: FragmentDetailBinding
    private var userStoryEntity: UserStoryEntity? = null
    private var isFavorite: Boolean? = false

    companion object{
        fun initViewModel(activity: FragmentActivity): MainViewModel? {
            val factory = ViewModelFactory.getInstance(activity.application)
            return factory?.let { ViewModelProvider(activity, it).get(MainViewModel::class.java) }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel =
            initViewModel(
                requireActivity()
            )

        userStoryEntity = DetailFragmentArgs.fromBundle(
            arguments as Bundle
        ).userStory

        userStoryEntity?.let {
            binding.userStoryEntity = it
            binding.tvKids.text = "${it.kids?.size}"
            mainViewModel?.setListComment(it.kids)
            isFavorite = mainViewModel?.isDataExits(it.idStory)
        }
        commentAdapter =
            UserCommentAdapter()

        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = commentAdapter
        }

        populateCommentData()
        buttonFavorite()
        isFavorite?.let { setFavorite(it) }
    }

    private fun buttonFavorite() {
        binding.imgFavorite.setOnClickListener {
            isFavorite?.let {
                if (!it) {
                    userStoryEntity?.favorite = true
                    mainViewModel?.setUserStoryLive(userStoryEntity)
                    isFavorite = true
                } else {
                    userStoryEntity?.favorite = false
                    isFavorite = false
                }
            }
            isFavorite?.let { setFavorite(it) }
        }
    }

    private fun populateCommentData() {
        mainViewModel?.getCommentList()?.observe(viewLifecycleOwner, Observer { resource ->

            when (resource.status) {
                Status.LOADING -> binding.progressBar.visible()

                Status.SUCCESS -> {
                    binding.progressBar.invisible()
                    resource.body?.let {
                        commentAdapter.setDataComment(it)
                    }
                }

                Status.ERROR -> {
                    binding.progressBar.invisible()
                    Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setFavorite(state: Boolean) {
        if (!state) {
            Glide.with(binding.root).load(R.drawable.ic_favorite_border).into(binding.imgFavorite)
        } else {
            Glide.with(binding.root).load(R.drawable.ic_favorite).into(binding.imgFavorite)
        }
    }

}
