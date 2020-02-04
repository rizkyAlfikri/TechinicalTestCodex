package com.dicoding.picodiploma.techinicaltestcodex.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.techinicaltestcodex.R
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserStoryEntity
import com.dicoding.picodiploma.techinicaltestcodex.databinding.ItemUserStoryBinding
import com.dicoding.picodiploma.techinicaltestcodex.ui.main.MainAdapter.MainViewHolder

class MainAdapter(private val listener: (View, UserStoryEntity) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {

    private val listUser = mutableListOf<UserStoryEntity>()

    fun setDataUser(list: List<UserStoryEntity>) {
        listUser.clear()
        listUser.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding: ItemUserStoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_story,
            parent,
            false
        )

        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(listUser[position], listener)
    }


    inner class MainViewHolder(private val binding: ItemUserStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(user: UserStoryEntity, listener: (View, UserStoryEntity) -> Unit) {
            binding.userStoryEntity = user
            binding.root.setOnClickListener { listener(binding.root, user)  }
        }
    }

}