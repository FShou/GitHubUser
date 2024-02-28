package com.fshou.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fshou.githubuser.R
import com.fshou.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var username: String

    companion object {
        const val EXTRA_USERNAME = "username"
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

        userDetailViewModel.getUserDetail(username)

        userDetailViewModel.userDetail.observe(this) {
            with(binding) {
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