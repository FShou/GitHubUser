package com.fshou.githubuser.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.R
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.databinding.ActivityMainBinding
import com.fshou.githubuser.ui.view_model.MainViewModel
import com.fshou.githubuser.ui.adapter.UserListAdapter
import com.fshou.githubuser.ui.view_model.SettingsViewModel
import com.fshou.githubuser.ui.view_model.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private val settingsViewModel by viewModels<SettingsViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        viewModel.searchedUser.observe(this@MainActivity) {
            resultHandle(it)
        }
        settingsViewModel.getThemeSettings().observe(this) {
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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
               viewModel.searchUsers(searchView.text.toString())
                searchView.hide()
                false
            }
            searchBar.inflateMenu(R.menu.favorite_menu)
            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favorite_user -> {
                        val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.settings -> {
                        val intent = Intent(this@MainActivity,SettingsActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }
    }


    private fun resultHandle(result: Result<List<User>>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                val message = result.error.getContentIfNotHandled()
                if (message != null) {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                setUserList(result.data)
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
}