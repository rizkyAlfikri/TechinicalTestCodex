package com.dicoding.picodiploma.techinicaltestcodex.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.techinicaltestcodex.R
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.techinicaltestcodex.ui.main.MainFragment
import com.dicoding.picodiploma.techinicaltestcodex.utils.invisible
import com.dicoding.picodiploma.techinicaltestcodex.utils.visible
import com.dicoding.picodiploma.techinicaltestcodex.viewmodel.ViewModelFactory
import com.dicoding.picodiploma.techinicaltestcodex.vo.Status.*

class DetailActivity : AppCompatActivity() {

    private var userStoryEntity: UserStoryEntity? = null
    private lateinit var commentAdapter: UserCommentAdapter
    private var detailViewModel: DetailViewModel? = null
    private lateinit var binding: ActivityDetailBinding
    private var flagFavorite: Boolean? = false
    private var menuItem: Menu? = null

    companion object{
        const val EXTRA_STORY = "EXTRA_STORY"
        const val REQUEST_CODE = 101
        const val RESULT_ADD = 102

        fun initViewModel(activity: AppCompatActivity): DetailViewModel? {
            val factory = ViewModelFactory.getInstance(activity.application)
            return factory?.let { ViewModelProvider(activity, it).get(DetailViewModel::class.java) }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = initViewModel(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setFavorite()
        userStoryEntity =  intent.getParcelableExtra(EXTRA_STORY)
        flagFavorite = userStoryEntity?.favorite

        binding.secondary.userStoryEntity = userStoryEntity

        commentAdapter = UserCommentAdapter()

        binding.secondary.rvComments.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = commentAdapter
        }

        userStoryEntity?.kids?.let {
            detailViewModel?.setListComment(it) }

        populateData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menuItem = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.action_favorite -> {
                flagFavorite?.let {
                    if (!it) {
                        val intent = Intent()
                        intent.putExtra(MainFragment.EXTRA_TITLE, "-")
                        userStoryEntity?.favorite = false
                        setFavorite()
                        setResult(RESULT_ADD, intent)
                    } else {
                        val intent = Intent()
                        intent.putExtra(MainFragment.EXTRA_TITLE, userStoryEntity?.title)
                        userStoryEntity?.favorite = true
                        setFavorite()
                        setResult(RESULT_ADD, intent)
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        flagFavorite?.let {
            if (!it) {
                menuItem?.getItem(0)?.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            } else {
                menuItem?.getItem(0)?.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            }
        }

    }

    private fun populateData() {
        detailViewModel?.getCommentList()?.observe(this, Observer { resource ->

            when (resource.status) {
                LOADING -> binding.progressBar.visible()

                SUCCESS -> {
                    binding.progressBar.invisible()
                    resource.body?.let {
                        commentAdapter.setDataComment(it)
                    }
                }

                ERROR -> {
                    binding.progressBar.invisible()
                    Toast.makeText(this@DetailActivity, "${resource.message}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }


}
