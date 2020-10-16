package com.mikeni.randomuser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserCacheEntity>

    @Query("SELECT * FROM users WHERE cell = :cell")
    suspend fun getUser(cell: String): UserCacheEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(user: List<UserCacheEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserCacheEntity)
}