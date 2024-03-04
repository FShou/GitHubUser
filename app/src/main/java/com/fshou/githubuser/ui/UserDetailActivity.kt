package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.fshou.githubuser.R
import com.fshou.githubuser.data.response.UserDetailResponse
import com.fshou.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

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
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        val sectionsPagerAdapter = SectionPagerAdapter(this@UserDetailActivity)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLE[position])
            }.attach()
        }

        // setup observers
        userDetailViewModel.apply {
            userDetail.observe(this@UserDetailActivity) { showUserDetail(it) }
            isLoading.observe(this@UserDetailActivity) { showLoading(it) }
            toastText.observe(this@UserDetailActivity) {
                it.getContentIfNotHandled().let { toastText ->
                    Toast.makeText(this@UserDetailActivity,toastText,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showUserDetail(user: UserDetailResponse){
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}