package com.fshou.githubuser.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fshou.githubuser.data.AppRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val appRepository: AppRepository) : ViewModel(){
    fun getThemeSettings(): LiveData<Boolean> {
        return appRepository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            appRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}