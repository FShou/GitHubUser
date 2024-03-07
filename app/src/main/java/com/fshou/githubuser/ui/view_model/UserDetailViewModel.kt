package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fshou.githubuser.data.AppRepository
import com.fshou.githubuser.data.local.entity.FavoriteUser
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.ui.view.UserDetailActivity
import kotlinx.coroutines.launch

class UserDetailViewModel(private val appRepository: AppRepository) :
    ViewModel() {

    lateinit var userDetail: LiveData<Result<UserDetailResponse>>

    private var _isFavorite = MutableLiveData<Boolean>()
    var isFavorite: LiveData<Boolean> = _isFavorite


    init {
        getUserDetail(UserDetailActivity.username)
        checkIsFavorit(UserDetailActivity.username)
    }

    fun addUser(user: FavoriteUser) = viewModelScope.launch {
        appRepository.addUser(user)
        checkIsFavorit(user.username)
    }


    fun deleteUser(user: FavoriteUser) = viewModelScope.launch{
        appRepository.deleteUser(user)
        checkIsFavorit(user.username)
    }

    private fun checkIsFavorit(username: String) = viewModelScope.launch {
            _isFavorite.value = appRepository.isFavoriteUser(username)
        }


    private fun getUserDetail(username: String) {
        userDetail = appRepository.getUserDetail(username)
    }

}