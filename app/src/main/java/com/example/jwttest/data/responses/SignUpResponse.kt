package com.example.jwttest.data.responses

data class SignUpResponse(
    val refresh: String,
    val token: String,
    val user: UserX
)