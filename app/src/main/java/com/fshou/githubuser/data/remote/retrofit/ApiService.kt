package com.fshou.githubuser.data.remote.retrofit


import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.data.remote.response.GitHubUserResponse
import com.fshou.githubuser.data.remote.response.UserDetailResponse
import retrofit2.http.*
interface ApiService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") username: String
    ): GitHubUserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ) : UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String
    ) : List<User>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ) :  List<User>
}