package com.fshou.githubuser.data.remote.retrofit


import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.data.remote.response.GitHubUserResponse
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ) : Call<GitHubUserResponse>

    @GET("search/users")
    suspend fun getUsersNew(
        @Query("q") username: String
    ): GitHubUserResponse

    @GET("users/{username}")
    suspend fun getUserDetailNew(
        @Path("username") username: String
    ) : UserDetailResponse
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ) : Call<UserDetailResponse>


    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<List<User>>

    @GET("users/{username}/followers")
    suspend fun getUserFollowerNew(
        @Path("username") username: String
    ) : List<User>
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ) :  Call<List<User>>
    @GET("users/{username}/following")
    suspend fun getUserFollowingNew(
        @Path("username") username: String
    ) :  List<User>
}