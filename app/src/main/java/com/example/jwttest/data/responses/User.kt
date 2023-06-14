package com.example.jwttest.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val date_joined: String,
    val email: String,
    val id: Int,
    val is_active: Boolean,
    val last_login: Any,
    val username: String
)