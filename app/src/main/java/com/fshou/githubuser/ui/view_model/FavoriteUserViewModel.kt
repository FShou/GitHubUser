package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.data.local.entity.FavoriteUser

class FavoriteUserViewModel(private val favoriteUserRepository: FavoriteUserRepository): ViewModel() {
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = favoriteUserRepository.getFavoriteUsers()
}