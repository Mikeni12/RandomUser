package com.mikeni.randomuser.di

import com.mikeni.randomuser.data.remote.RandomUserService
import com.mikeni.randomuser.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.API)
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Singleton
    @Provides
    fun provideRecordService(retrofit: Retrofit.Builder): RandomUserService = retrofit
        .build()
        .create(RandomUserService::class.java)
}