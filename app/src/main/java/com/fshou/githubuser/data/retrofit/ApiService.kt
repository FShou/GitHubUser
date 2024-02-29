package com.fshou.githubuser.data.retrofit


import com.fshou.githubuser.data.response.User
import com.fshou.githubuser.data.response.GitHubUserResponse
import com.fshou.githubuser.data.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("search/users")
    fun getUsersByUsername(
        @Query("q") username: String
    ) : Call<GitHubUserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ) : Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<List<User>>
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ) :  Call<List<User>>
}