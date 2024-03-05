package com.fshou.githubuser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import com.fshou.githubuser.data.Result

class UserDetailViewModelNew(private val favoriteUserRepository: FavoriteUserRepository) :
    ViewModel() {

        lateinit var userDetail:  LiveData<Result<UserDetailResponse>>

        init {
            getUserDetail(UserDetailActivity.username)
        }

    suspend fun addUser(user: FavoriteUser) = favoriteUserRepository.addUser(user)
    suspend fun deleteUser(user: FavoriteUser) = favoriteUserRepository.deleteUser(user)
    fun isFavoriteUser(username: String): LiveData<Boolean> =
        favoriteUserRepository.isFavoriteUser(username)

    private fun getUserDetail(username: String ){
        userDetail = favoriteUserRepository.getUserDetail(username)
    }

}