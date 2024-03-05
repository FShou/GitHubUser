package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.ui.view.UserDetailActivity

class FollowViewModel (private val favoriteUserRepository: FavoriteUserRepository): ViewModel() {
    lateinit var followerList : LiveData<Result<List<User>>>
    lateinit var followingList : LiveData<Result<List<User>>>

    init {
        getFollowerList(UserDetailActivity.username)
        getFollowingList(UserDetailActivity.username)
    }


    fun getFollowerList(username: String) {
        followerList = favoriteUserRepository.getFollowers(username)
    }

    fun getFollowingList(username: String) {
        followingList = favoriteUserRepository.getFollowing(username)
    }

}