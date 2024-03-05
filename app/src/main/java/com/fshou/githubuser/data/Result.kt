package com.fshou.githubuser.data

import com.fshou.githubuser.utils.Event

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Event<String>) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}