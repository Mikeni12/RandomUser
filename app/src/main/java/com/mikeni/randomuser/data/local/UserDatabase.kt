package com.mikeni.randomuser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserCacheEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun usersDao(): UserDao
}