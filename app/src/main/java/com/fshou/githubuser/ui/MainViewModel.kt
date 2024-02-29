package com.fshou.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fshou.githubuser.data.response.ItemsItem
import com.fshou.githubuser.data.response.SearchUserResponse
import com.fshou.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var _searchedUser = MutableLiveData<List<ItemsItem>>()
    var searchedUser: LiveData<List<ItemsItem>> = _searchedUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }


    fun searchUser(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getUsersByUsername(username)
            .enqueue(object : Callback<SearchUserResponse> {
                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    response: Response<SearchUserResponse>
                ) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        Log.e(TAG, "OnFailure: ${response.message()}")
                    }
                    _searchedUser.value = response.body()?.items as List<ItemsItem>?
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "OnFailure: ${t.cause}")
                }

            })

    }
}