package com.fshou.githubuser.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.AppRepository
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.ui.view.UserDetailActivity

class FollowViewModel (private val appRepository: AppRepository): ViewModel() {
    lateinit var followerList : LiveData<Result<List<User>>>
    lateinit var followingList : LiveData<Result<List<User>>>

    init {
        getFollowerList(UserDetailActivity.username)
        getFollowingList(UserDetailActivity.username)
    }

    private fun getFollowerList(username: String) {
        followerList = appRepository.getFollowers(username)
    }

    private fun getFollowingList(username: String) {
        followingList = appRepository.getFollowing(username)
    }

}