package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val viewModel: FavoriteUserViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getFavoriteUsers().observe(this@FavoriteUserActivity) {
            Log.d("FAVORITEUSER", it.toString())
            setUserList(it)
        }
    }

    private fun setUserList(userList: List<FavoriteUser>) {
        val users = arrayListOf<User>()
        userList.forEach {
            val user = User(login = it.username , avatarUrl = it.avatarUrl)
            users.add(user)

        }

        Log.d("USERLIST", users.toString())
        val userListAdapter = UserListAdapter(users)
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            adapter = userListAdapter
        }
    }
}