package com.fshou.githubuser.data.retrofit


import com.fshou.githubuser.data.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ) : Call<SearchUserResponse>
}