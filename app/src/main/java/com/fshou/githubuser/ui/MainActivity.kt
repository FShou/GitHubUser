package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.response.ItemsItem
import com.fshou.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
//        mainViewModel.searchUser("sidiq")
        mainViewModel.searchedUser.observe(this) {
            setUserList(it)
        }
    }

    private fun setUserList(userList: List<ItemsItem>){
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        val  listUserAdatpter = ListUserAdatpter(userList)
        binding.rvUserList.adapter = listUserAdatpter
    }
}