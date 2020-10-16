package com.mikeni.randomuser.data.remote

import retrofit2.http.GET

interface RandomUserService {
    @GET("api/")
    suspend fun getRandomUser(): ApiResponse
}