package com.fshou.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fshou.githubuser.data.response.ItemsItem
import com.fshou.githubuser.databinding.UserItemLayoutBinding

class ListUserAdatpter(private var listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdatpter.ListViewHolder>() {
    class ListViewHolder(var binding: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, avatarUrl) = listUser[position]

        holder.binding.tvUserName.text = login

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .circleCrop()
            .into(holder.binding.imgAvatar)
    }
}