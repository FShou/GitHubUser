package com.fshou.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fshou.githubuser.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase(){
    abstract fun favoriteUserDao() : FavoriteUserDao

   companion object{
       @Volatile
       private var instance : FavoriteUserRoomDatabase? = null
       fun getDatabase(context: Context):  FavoriteUserRoomDatabase =
           instance ?: synchronized(this) {
               instance ?: Room.databaseBuilder(
                   context.applicationContext,
                   FavoriteUserRoomDatabase::class.java, "FavoriteUser.db"
               ).build()
           }
   }

}