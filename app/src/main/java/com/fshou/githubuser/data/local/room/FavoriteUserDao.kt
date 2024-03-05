package com.fshou.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fshou.githubuser.data.local.entity.FavoriteUser


@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: FavoriteUser)

    @Delete
    suspend fun deleteUser(user: FavoriteUser)

    @Query("SELECT * from FavoriteUser")
      fun getFavoriteUsers() : LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM favoriteuser WHERE username = :username )")
    suspend fun isFavoriteUser(username: String) : Boolean
}