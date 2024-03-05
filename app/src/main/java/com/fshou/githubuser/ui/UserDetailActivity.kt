package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.fshou.githubuser.R
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import com.fshou.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_USERNAME = "username"
        lateinit var username: String
        private val TAB_TITLE = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private lateinit var binding: ActivityUserDetailBinding
    private val user = FavoriteUser()
    private val viewModelNew: UserDetailViewModelNew by viewModels {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)

        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        viewModelNew.apply {
            userDetail.observe(this@UserDetailActivity) {
                handleUserDetail(it)
            }

            isFavoriteUser(username).observe(this@UserDetailActivity) { itFavorite ->
                setFabIcon(itFavorite)
            }
        }

        binding.fab.setOnClickListener {
            viewModelNew.isFavoriteUser(username).observe(this@UserDetailActivity) { isFavorite ->
                if (isFavorite) {
                    viewModelNew.viewModelScope.launch {
                        viewModelNew.deleteUser(user)
                    }
                } else {
                    viewModelNew.viewModelScope.launch {
                        viewModelNew.addUser(user)
                    }
                }
                setFabIcon(!isFavorite)
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this@UserDetailActivity)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLE[position])
            }.attach()
        }


        setContentView(binding.root)
    }

    private fun setFabIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fab.setImageResource(R.drawable.favorite)
        } else {
            binding.fab.setImageResource(R.drawable.favorite_border)
        }
    }


    private fun handleUserDetail(result: Result<UserDetailResponse>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                val message = result.error.getContentIfNotHandled()
                if (message != null) {
                    Toast.makeText(this@UserDetailActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                user.username = result.data.login
                user.avatarUrl = result.data.avatarUrl
                showUserDetail(result.data)
            }
        }
    }

    private fun showUserDetail(user: UserDetailResponse) {
        binding.apply {
            tvUserName.text = user.login
            tvFullName.text = user.name
            val formattedFollowers = String.format("%,d Followers", user.followers)
            val formattedFollowing = String.format("%,d Following", user.following)
            tvTotalFollowers.text = formattedFollowers
            tvTotalFollowing.text = formattedFollowing

            tvTotalFollowers.visibility = View.VISIBLE
            tvTotalFollowing.visibility = View.VISIBLE
            tvFullName.visibility = View.VISIBLE
            tvUserName.visibility = View.VISIBLE

            if (tvFullName.text == "") {
                tvFullName.text = user.login
            }
            Glide.with(this@UserDetailActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imgAvatar)
        }
    }

}