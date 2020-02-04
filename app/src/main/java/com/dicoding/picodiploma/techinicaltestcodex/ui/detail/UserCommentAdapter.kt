package com.dicoding.picodiploma.techinicaltestcodex.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.techinicaltestcodex.R
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.entity.UserCommentEntity
import com.dicoding.picodiploma.techinicaltestcodex.databinding.ItemUserCommentBinding
import com.dicoding.picodiploma.techinicaltestcodex.ui.detail.UserCommentAdapter.UserCommentHolder

class UserCommentAdapter: RecyclerView.Adapter<UserCommentHolder>() {

    private val listComment = mutableListOf<UserCommentEntity>()

    fun setDataComment(list: List<UserCommentEntity>) {
        listComment.clear()
        listComment.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCommentHolder {
        val binding: ItemUserCommentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_comment,
            parent,
            false
        )

        return UserCommentHolder(binding)
    }

    override fun getItemCount(): Int {
        return listComment.size
    }

    override fun onBindViewHolder(holder: UserCommentHolder, position: Int) {
        holder.bindData(listComment[position])
    }

    inner class UserCommentHolder(private val binding: ItemUserCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(comment: UserCommentEntity) {
            binding.userComment = comment
        }
    }
}