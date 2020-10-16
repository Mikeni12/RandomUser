package com.mikeni.randomuser.data.repository

import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.data.local.CacheMapper
import com.mikeni.randomuser.data.local.UserDao
import com.mikeni.randomuser.data.remote.NetworkMapper
import com.mikeni.randomuser.data.remote.RandomUserService
import com.mikeni.randomuser.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class UserRepository(
    private val userDao: UserDao,
    private val userService: RandomUserService,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getRandomUser(): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            val response = userService.getRandomUser()
            val users = networkMapper.mapFromEntityList(response.results)
            userDao.insertAll(users.map { cacheMapper.mapToEntity(it) })
            val cachedUser = userDao.getUser(users.first().cell)
            emit(DataState.Success(cacheMapper.mapFromEntity(cachedUser)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> {
                    emit(DataState.Loading)
                    try {
                        val cachedUsers = userDao.getAllUsers().shuffled().first()
                        emit(DataState.Success(cacheMapper.mapFromEntity(cachedUsers)))
                    } catch (e: Exception) {
                        emit(DataState.Error(e))
                    }
                }
                else -> emit(DataState.Error(e))
            }
        }
    }

    suspend fun getRecentRandomUsers(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        try {
            val cachedUsers = userDao.getAllUsers()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedUsers)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}