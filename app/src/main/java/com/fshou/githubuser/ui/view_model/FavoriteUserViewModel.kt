package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.AppRepository
import com.fshou.githubuser.data.local.entity.FavoriteUser

class FavoriteUserViewModel(private val appRepository: AppRepository): ViewModel() {
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = appRepository.getFavoriteUsers()
}