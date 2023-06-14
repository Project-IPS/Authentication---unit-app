package com.example.jwttest.data.network

import com.example.jwttest.data.responses.LoginResponse
import com.example.jwttest.data.responses.SignUpResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//creating the Client side API interface

interface AuthAPI {

    @FormUrlEncoded
    @POST("login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register/")
    suspend fun signUp(
        @Field("username") user: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : SignUpResponse
}