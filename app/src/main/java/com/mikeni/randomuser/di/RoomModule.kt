package com.mikeni.randomuser.di

import android.content.Context
import androidx.room.Room
import com.mikeni.randomuser.data.local.UserDao
import com.mikeni.randomuser.data.local.UserDatabase
import com.mikeni.randomuser.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRecordDb(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideRecordDAO(recordDatabase: UserDatabase): UserDao = recordDatabase.usersDao()
}