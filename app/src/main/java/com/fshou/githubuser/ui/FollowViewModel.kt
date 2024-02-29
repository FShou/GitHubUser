package com.fshou.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.response.User
import com.fshou.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowerViewModel"
    }

    private var _followerList = MutableLiveData<List<User>>()
    val followerList: LiveData<List<User>> = _followerList

    private var _followingList = MutableLiveData<List<User>>()
    val followingList: LiveData<List<User>> = _followingList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getFollowerByUserName(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollower(username)
            .enqueue(object : Callback<List<User>> {

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, response.message())
                        return
                    }
                    _followerList.value = response.body()
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e(TAG, "OnFailure: FOLLOWER ${t.message}")
                }

            })

    }

    fun getFollowingByUserName(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowing(username)
            .enqueue(object : Callback<List<User>> {

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, response.message())
                        return
                    }
                    _followingList.value = response.body()
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e(TAG, "OnFailure: FOLOWWING ${t.message}")
                }

            })

    }
}