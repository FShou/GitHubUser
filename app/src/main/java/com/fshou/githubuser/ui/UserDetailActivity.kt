package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.fshou.githubuser.R
import com.fshou.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_USERNAME = "username"
        lateinit var username: String
        private val TAB_TITLE = arrayOf(
            "Followers",
            "Following"
        )
    }

    private lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra(EXTRA_USERNAME)!!

        val userDetailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserDetailViewModel::class.java]

        val sectionsPagerAdapter = SectionPagerAdapter(this@UserDetailActivity)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = TAB_TITLE[position]
        }.attach()


        // setup observers
        userDetailViewModel.apply {
            userDetail.observe(this@UserDetailActivity) {
                // set up ui
              binding.apply {
                    tvUserName.text = it.login
                    tvUserName.visibility = View.VISIBLE
                    tvFullName.text = it.name
                    tvFullName.visibility = View.VISIBLE
                    if (tvFullName.text == "") {
                        tvFullName.text = it.login
                    }
                    Glide.with(this@UserDetailActivity)
                        .load(it?.avatarUrl)
                        .circleCrop()
                        .into(imgAvatar)
                }
            }

            isLoading.observe(this@UserDetailActivity) {
                showLoading(it)
            }
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