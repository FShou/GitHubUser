package com.fshou.githubuser.data.retrofit


import com.fshou.githubuser.data.response.SearchUserResponse
import com.fshou.githubuser.data.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("search/users")
    fun getUsersByUsername(
        @Query("q") username: String
    ) : Call<SearchUserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ) : Call<UserDetailResponse>
}