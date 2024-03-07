package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fshou.githubuser.data.AppRepository
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    private var _searchedUser = MutableLiveData<Result<List<User>>>()
    var searchedUser: LiveData<Result<List<User>>> = _searchedUser

    init {
        searchUsers()
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return appRepository.getThemeSetting().asLiveData()
    }
     fun searchUsers(username: String = "the") {
        _searchedUser.value = Result.Loading
        try {
            viewModelScope.launch {
                _searchedUser.value = Result.Success(appRepository.getUsers(username))
            }
        }catch (e: Exception){
            _searchedUser.value = Result.Error(Event(e.message.toString()))
        }
    }

}