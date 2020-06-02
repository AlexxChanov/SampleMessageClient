package com.example.samplemessageclient.data

import java.util.*

data class User(
    val username: String,
    val firstName: String,
    val lastName: String
)

data class ApiResponse(
    val message: String,
    val success: Boolean
)

data class Message(
    val id: Int,
    val author: String,
    val text: String,
    val date: Date
)

data class MessageRequest(
    val author: String,
    val text: String
)