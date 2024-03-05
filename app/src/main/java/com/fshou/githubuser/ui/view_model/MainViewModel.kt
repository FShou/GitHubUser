package com.fshou.githubuser.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fshou.githubuser.data.FavoriteUserRepository
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.data.Result
import com.fshou.githubuser.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {

    private var _searchedUser = MutableLiveData<Result<List<User>>>()
    var searchedUser: LiveData<Result<List<User>>> = _searchedUser

    init {
        searchUsers()
    }

     fun searchUsers(username: String = "the") {
        _searchedUser.value = Result.Loading
        try {
            viewModelScope.launch {
                _searchedUser.value = Result.Success(favoriteUserRepository.getUsersNew(username))
            }
        }catch (e: Exception){
            _searchedUser.value = Result.Error(Event(e.message.toString()))
        }
    }

}