package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        mainViewModel.searchedUser.observe(this) {
            setUserList(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener {_,_,_->
                    searchBar.textView.text = searchView.text
                    if (searchBar.textView.text.toString() == ""){
                        return@setOnEditorActionListener false
                    }
                    mainViewModel.searchUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }
    }

    private fun setUserList(userList: List<ItemsItem>){
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        val  listUserAdatpter = ListUserAdatpter(userList)
        binding.rvUserList.adapter = listUserAdatpter
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}