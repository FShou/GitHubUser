package com.fshou.githubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.di.Injection

class ViewModelFactory private constructor(private val favoriteUserRepository: FavoriteUserRepository ) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
//            return UserDetailViewModel(favoriteUserRepository) as T
//        }
        when {
            modelClass.isAssignableFrom(UserDetailViewModelNew::class.java) -> return UserDetailViewModelNew(favoriteUserRepository) as T
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> UserDetailViewModel() as T
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