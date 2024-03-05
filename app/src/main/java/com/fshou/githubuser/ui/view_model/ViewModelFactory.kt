package com.fshou.githubuser.ui.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.di.Injection

class ViewModelFactory private constructor(private val favoriteUserRepository: FavoriteUserRepository ) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> return UserDetailViewModel(favoriteUserRepository) as T
            modelClass.isAssignableFrom(FavoriteUserViewModel::class.java) -> return FavoriteUserViewModel(favoriteUserRepository) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(favoriteUserRepository) as T
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> return FollowViewModel(favoriteUserRepository) as T
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