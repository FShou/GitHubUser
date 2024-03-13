package com.fshou.githubuser.data.remote.response


import com.google.gson.annotations.SerializedName


data class GitHubUserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<User?>? = null
)


data class User(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

)
