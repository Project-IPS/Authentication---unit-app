package com.example.jwttest.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val access: String,
    val refresh: String,
    val user: User
)