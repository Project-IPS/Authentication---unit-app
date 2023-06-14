package com.example.jwttest.data.repository

import com.example.jwttest.data.network.AuthAPI
import com.example.jwttest.ui.base.BaseRepository

class UserRepository(
    private val api: AuthAPI,
): BaseRepository(){

//   suspend fun getUser() = safeApiCall {
//       api.getUser()
//   }
}