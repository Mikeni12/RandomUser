package com.mikeni.randomuser.di

import com.mikeni.randomuser.data.local.CacheMapper
import com.mikeni.randomuser.data.local.UserDao
import com.mikeni.randomuser.data.remote.NetworkMapper
import com.mikeni.randomuser.data.remote.RandomUserService
import com.mikeni.randomuser.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        recordDao: UserDao,
        recordService: RandomUserService,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): UserRepository = UserRepository(recordDao, recordService, cacheMapper, networkMapper)
}