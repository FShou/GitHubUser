package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // setup observer
        mainViewModel.apply {
            searchedUser.observe(this@MainActivity) { setUserList(it) }
            isLoading.observe(this@MainActivity) { showLoading(it) }
            toastText.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { toastText ->
                    Toast.makeText(this@MainActivity,toastText, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // configure search bar & searchview
        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
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
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
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