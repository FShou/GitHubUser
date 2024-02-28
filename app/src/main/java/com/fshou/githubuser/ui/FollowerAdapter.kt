package com.fshou.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fshou.githubuser.data.response.UserFollowersResponseItem
import com.fshou.githubuser.databinding.UserItemLayoutBinding

class FollowerAdapter : ListAdapter<UserFollowersResponseItem, FollowerAdapter.MyViewHolder>(
    DIFF_CALLBACK) {
    class MyViewHolder(private val binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: UserFollowersResponseItem){
            binding.apply {
                tvUserName.text = follower.login
                Glide.with(binding.root.context)
                    .load(follower.avatarUrl)
                    .circleCrop()
                    .into(binding.imgAvatar)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserFollowersResponseItem,
                newItem: UserFollowersResponseItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: UserFollowersResponseItem,
                newItem: UserFollowersResponseItem
            ): Boolean = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}