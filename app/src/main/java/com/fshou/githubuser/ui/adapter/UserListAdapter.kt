package com.fshou.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.databinding.UserItemLayoutBinding
import com.fshou.githubuser.ui.view.UserDetailActivity

class UserListAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    var showArrow = true
    var addIntent = true

    class ListViewHolder(var binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = userList[position]

        holder.binding.apply {
            tvUserName.text = user.login
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imgAvatar)
            if (!showArrow) {
                arrow.visibility = View.GONE
            }
            if (!addIntent) {
                return
            }
        }

        val userDetailIntent = Intent(holder.itemView.context, UserDetailActivity::class.java)
        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USERNAME, user.login)

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(userDetailIntent)

        }
        holder.binding.arrow.setOnClickListener {
            holder.itemView.context.startActivity(userDetailIntent)
        }
    }
}