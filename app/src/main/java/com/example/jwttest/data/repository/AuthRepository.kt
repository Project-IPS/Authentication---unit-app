package com.example.jwttest.data.repository

import com.example.jwttest.data.UserPreferences
import com.example.jwttest.data.network.AuthAPI
import com.example.jwttest.ui.base.BaseRepository
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val api: AuthAPI,
    private val preferences: UserPreferences
): BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email,password)
    }

    suspend fun signUp(
        user: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.signUp(user, email, password)
    }

    suspend fun saveLoginAuthToken(token: String){
        preferences.saveLoginAuthToken(token)
    }

    fun getLoginToken(): Flow<String?> {
        return preferences.authLoginToken
    }

    suspend fun saveRegisterAuthToken(token: String){
        preferences.saveRegisterAuthToken(token)
    }

    fun getRegisterToken(): Flow<String?> {
        return preferences.authRegisterToken
    }
}