package com.fshou.githubuser.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fshou.githubuser.data.AppRepository
import com.fshou.githubuser.di.Injection

class ViewModelFactory private constructor(private val appRepository: AppRepository ) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> return UserDetailViewModel(appRepository) as T
            modelClass.isAssignableFrom(FavoriteUserViewModel::class.java) -> return FavoriteUserViewModel(appRepository) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(appRepository) as T
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> return FollowViewModel(appRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> return SettingsViewModel(appRepository) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}