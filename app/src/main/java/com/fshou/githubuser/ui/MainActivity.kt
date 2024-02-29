package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.response.User
import com.fshou.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.searchedUser.observe(this) {
            setUserList(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.textView.text = searchView.text
                    if (searchBar.textView.text.toString() == "") {
                        searchView.hide()
                        return@setOnEditorActionListener false
                    }
                    mainViewModel.searchUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }
    }

    private fun setUserList(userList: List<User>) {
        val userListAdapter = UserListAdapter(userList)
        binding.apply {
            rvUserList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUserList.adapter = userListAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}