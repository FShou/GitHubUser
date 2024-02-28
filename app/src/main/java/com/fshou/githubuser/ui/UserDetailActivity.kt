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

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs,viewPager){ tab,position ->
            tab.text = TAB_TITLE[position]
        }.attach()


        userDetailViewModel.userDetail.observe(this) {
           binding.apply {
                tvUserName.text = it.login
                tvFullName.text = it.name
                if (tvFullName.text == "") {
                    tvFullName.text = it.login
                }

                Glide.with(this@UserDetailActivity)
                    .load(it?.avatarUrl)
                    .circleCrop()
                    .into(imgAvatar)
            }
        }

        userDetailViewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

}