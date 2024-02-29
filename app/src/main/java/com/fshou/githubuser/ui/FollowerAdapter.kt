package com.fshou.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fshou.githubuser.data.response.UserFollowersResponseItem
import com.fshou.githubuser.databinding.UserItemLayoutBinding

class FollowerAdapter(private val listFollower: List<UserFollowersResponseItem>) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {


    class ViewHolder(val binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = listFollower.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollower[position]

        holder.binding.apply {
            tvUserName.text = follower.login
            arrow.visibility = GONE

            Glide.with(holder.itemView.context)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(imgAvatar)
        }
//
//        val userDetailIntent = Intent(holder.itemView.context, UserDetailActivity::class.java)
//        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USERNAME, follower.login)
//
//        holder.itemView.setOnClickListener {
//            holder.itemView.context.startActivity(userDetailIntent)
//        }
    }

}