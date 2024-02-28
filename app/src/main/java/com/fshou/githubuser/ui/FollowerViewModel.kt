package com.fshou.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.response.UserFollowersResponseItem
import com.fshou.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowerViewModel"
    }

    private var _followerList = MutableLiveData<List<UserFollowersResponseItem>>()
    val followerList: LiveData<List<UserFollowersResponseItem>> = _followerList

    private var _followingList = MutableLiveData<List<UserFollowersResponseItem>>()
    val followingList: LiveData<List<UserFollowersResponseItem>> = _followingList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getFollowerByUserName(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollower(username).apply {
            enqueue(object : Callback<List<UserFollowersResponseItem>> {
                override fun onResponse(
                    call: Call<List<UserFollowersResponseItem>>,
                    response: Response<List<UserFollowersResponseItem>>
                ) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, response.message())
                        return
                    }
                    _followerList.value = response.body()
                    Log.d("FOLLOWER", followerList.value.toString())
                }

                override fun onFailure(call: Call<List<UserFollowersResponseItem>>, t: Throwable) {
                    Log.e(TAG, "OnFailure: Folower ${t.message}")
                }

            })
        }
    }

    fun getFollowingByUserName(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowing(username).apply {
            enqueue(object : Callback<List<UserFollowersResponseItem>> {
                override fun onResponse(
                    call: Call<List<UserFollowersResponseItem>>,
                    response: Response<List<UserFollowersResponseItem>>
                ) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, response.message())
                        return
                    }
                    _followingList.value = response.body()
                    Log.d("FOLLOWING", _followingList.value.toString())
                }

                override fun onFailure(call: Call<List<UserFollowersResponseItem>>, t: Throwable) {
                    Log.e(TAG, "OnFailure: FOLOWING ${t.message}")
                }

            })
        }
    }


}