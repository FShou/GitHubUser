package com.fshou.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.local.room.FavoriteUserDao
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import com.fshou.githubuser.data.remote.retrofit.ApiService
import com.fshou.githubuser.utils.Event


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
    suspend fun deleteUser(user: FavoriteUser) {
        favoriteUserDao.deleteUser(user)
    }

    fun getUserDetail(username: String): LiveData<Result<UserDetailResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val userDetailResponse = apiService.getUserDetailNew(username)
                emit(Result.Success(userDetailResponse))
            }catch (e: Exception){
                Log.d("getUserDetaili", e.message.toString())
                emit(Result.Error(Event(e.message.toString())))
            }
        }


    fun isFavoriteUser(username: String): LiveData<Boolean> = liveData { emit(favoriteUserDao.isFavoriteUser(username)) }




}