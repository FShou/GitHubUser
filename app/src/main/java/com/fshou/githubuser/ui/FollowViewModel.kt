package com.fshou.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.data.remote.retrofit.ApiConfig
import com.fshou.githubuser.utils.Event
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

    private var _followersIsLoading = MutableLiveData<Boolean>()
    val followerIsLoading: LiveData<Boolean> = _followersIsLoading


    private var _followingIsLoading = MutableLiveData<Boolean>()
    val followingIsLoading: LiveData<Boolean> = _followingIsLoading


    private val _followerToastText = MutableLiveData<Event<String>>()
    val followerToastText: LiveData<Event<String>> = _followerToastText

    private val _followingToastText = MutableLiveData<Event<String>>()
    val followingToastText: LiveData<Event<String>> = _followingToastText


    fun getFollowerByUserName(username: String) {
        _followersIsLoading.value = true
        ApiConfig.getApiService().getUserFollower(username)
            .enqueue(object : Callback<List<User>> {

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _followersIsLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, response.message())
                        return
                    }
                    _followerList.value = response.body()
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _followersIsLoading.value = false
                    _followerToastText.value = Event("Failed to Load Followers data")
                    Log.e(TAG, "OnFailure: FOLLOWER ${t.message}")
                }

            })

    }

    fun getFollowingByUserName(username: String) {
        _followingIsLoading.value = true
        ApiConfig.getApiService().getUserFollowing(username)
            .enqueue(object : Callback<List<User>> {

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _followingIsLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, response.message())
                        return
                    }
                    _followingList.value = response.body()
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _followingIsLoading.value = false
                    _followingToastText.value = Event("Failed to Load Following data")
                    Log.e(TAG, "OnFailure: FOLOWWING ${t.message}")
                }

            })

    }
}