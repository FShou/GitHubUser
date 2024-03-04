package com.fshou.githubuser.ui

import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.data.local.entity.FavoriteUser

class UserDetailViewModelNew (private val favoriteUserRepository: FavoriteUserRepository): ViewModel() {
     suspend fun addUser(user: FavoriteUser) = favoriteUserRepository.addUser(user)
     suspend fun isFavoriteUSer(username: String) = favoriteUserRepository.isFavoriteUser(username)

}