package com.fshou.githubuser.di

import android.content.Context
import com.fshou.githubuser.data.AppRepository
import com.fshou.githubuser.data.local.datastore.SettingPreferences
import com.fshou.githubuser.data.local.datastore.dataStore
import com.fshou.githubuser.data.local.room.FavoriteUserRoomDatabase
import com.fshou.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): AppRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val settingPreferences = SettingPreferences.getInstance(context.dataStore)
        return AppRepository.getInstance(apiService, dao, settingPreferences)
    }
}