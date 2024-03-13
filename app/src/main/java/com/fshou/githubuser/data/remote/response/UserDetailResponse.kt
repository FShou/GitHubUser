package com.fshou.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("login")
	val login: String = "",

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

)
