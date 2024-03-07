package com.fshou.githubuser.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.databinding.ActivityFavoriteUserBinding
import com.fshou.githubuser.ui.view_model.FavoriteUserViewModel
import com.fshou.githubuser.ui.adapter.UserListAdapter
import com.fshou.githubuser.ui.view_model.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val viewModel: FavoriteUserViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        viewModel.getFavoriteUsers().observe(this@FavoriteUserActivity) {
            Log.d("FAVORITEUSER", it.toString())
            setUserList(it)
        }

        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setUserList(userList: List<FavoriteUser>) {
        if (userList.isEmpty()){
            binding.nothing.visibility = View.VISIBLE
            binding.rvUserList.visibility = View.GONE
            return
        }
        binding.rvUserList.visibility = View.VISIBLE
        binding.nothing.visibility = View.GONE
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