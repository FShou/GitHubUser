package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fshou.githubuser.data.FavoriteUserRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel(){
    fun getThemeSettings(): LiveData<Boolean> {
        return favoriteUserRepository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            favoriteUserRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}