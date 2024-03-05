package com.fshou.githubuser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteUser(

    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    var avatarUrl: String? = null,

    )