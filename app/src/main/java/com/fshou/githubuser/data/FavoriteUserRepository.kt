package com.fshou.githubuser.data

import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.local.room.FavoriteUserDao
import com.fshou.githubuser.data.remote.retrofit.ApiService
import com.fshou.githubuser.ui.UserDetailActivity.Companion.username

class FavoriteUserRepository private constructor(
    private val  apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,

){
    companion object {
        @Volatile
        private var instance : FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao,
        ): FavoriteUserRepository =
            instance ?: synchronized(this){
                instance ?: FavoriteUserRepository(apiService,favoriteUserDao)
            }.also { instance = it }

    }

    suspend fun addUser(user: FavoriteUser) {
        favoriteUserDao.addUser(user)
    }

    suspend fun isFavoriteUser(username: String) = favoriteUserDao.isFavoriteUser(username)




}