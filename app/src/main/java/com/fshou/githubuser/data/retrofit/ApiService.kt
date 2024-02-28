package com.fshou.githubuser.data.retrofit


import com.fshou.githubuser.data.response.SearchUserResponse
import com.fshou.githubuser.data.response.UserDetailResponse
import com.fshou.githubuser.data.response.UserFollowersResponse
import com.fshou.githubuser.data.response.UserFollowersResponseItem
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

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<List<UserFollowersResponseItem>>
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ) :  Call<List<UserFollowersResponseItem>>
}