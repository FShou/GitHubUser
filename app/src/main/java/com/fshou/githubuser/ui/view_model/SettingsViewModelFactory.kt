package com.fshou.githubuser.ui.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fshou.githubuser.data.local.datastore.SettingPreferences
import com.fshou.githubuser.data.local.datastore.dataStore

class SettingsViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                return SettingsViewModel(pref) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

    companion object {
        @Volatile
        private var instance : SettingsViewModelFactory? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: SettingsViewModelFactory(SettingPreferences.getInstance(context.dataStore))
        }.also { instance = it }
    }

}