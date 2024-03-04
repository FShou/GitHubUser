package com.fshou.githubuser.di

import android.content.Context
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.data.local.room.FavoriteUserRoomDatabase
import com.fshou.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
//        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(apiService, dao)
    }
}