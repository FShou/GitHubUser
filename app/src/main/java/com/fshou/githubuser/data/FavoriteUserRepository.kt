package com.fshou.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.local.room.FavoriteUserDao
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import com.fshou.githubuser.data.remote.retrofit.ApiService
import com.fshou.githubuser.utils.Event


class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,

    ) {
    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao,
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(apiService, favoriteUserDao)
            }.also { instance = it }

    }

    suspend fun addUser(user: FavoriteUser) {
        favoriteUserDao.addUser(user)
    }

    suspend fun deleteUser(user: FavoriteUser) {
        favoriteUserDao.deleteUser(user)
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = favoriteUserDao.getFavoriteUsers()
    fun isFavoriteUser(username: String): LiveData<Boolean> = liveData { emit(favoriteUserDao.isFavoriteUser(username)) }

    fun getUserDetail(username: String): LiveData<Result<UserDetailResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val userDetailResponse = apiService.getUserDetailNew(username)
                emit(Result.Success(userDetailResponse))
            } catch (e: Exception) {
                Log.d("getUserDetaili", e.message.toString())
                emit(Result.Error(Event(e.message.toString())))
            }
        }

    suspend fun getUserDetailNew(username: String) : UserDetailResponse = apiService.getUserDetailNew(username)

    fun getUsers(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val gitHubUserResponse = apiService.getUsersNew(username)
            val users = gitHubUserResponse.items
            emit(Result.Success(users as List<User>))
        } catch (e: Exception) {
            Log.d("getUsers[REPO)", e.message.toString())
            emit(Result.Error(Event(e.message.toString())))
        }
    }

    suspend fun getUsersNew(username: String) : List<User> {
        val gitHubUserResponse = apiService.getUsersNew(username)
        val users = gitHubUserResponse.items
        return users as List<User>
    }

    fun getFollowers(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val followers = apiService.getUserFollowerNew(username)
            Log.d("getFollowers[REPO)",followers.toString())
            emit(Result.Success(followers))
        } catch (e: Exception) {
            Log.d("getFollower[REPO)", e.message.toString())
            emit(Result.Error(Event(e.message.toString())))
        }
    }

    fun getFollowing(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val followers: List<User> = apiService.getUserFollowingNew(username)
            Log.d("getFollowing[REPO)",followers.toString())
            emit(Result.Success(followers))
        } catch (e: Exception) {
            Log.d("getFollowing[REPO)", e.message.toString())
            emit(Result.Error(Event(e.message.toString())))
        }
    }



}