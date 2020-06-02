package com.example.samplemessageclient.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MessengerRepository : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val messengerApi = Retrofit.Builder()
        .baseUrl(MessengerApi.HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MessengerApi::class.java)

    fun register(username: String, firstname: String, lastname: String) = async {
        val user = User(username, firstname, lastname)
        val response = messengerApi.register(user).execute()
       response.body()
    }

    fun getAllUsers() = async {
        messengerApi.getAllUsers().execute().body()
    }
    fun getAllMessages() = async {
        messengerApi.getAllMessanges().execute().body()
    }

    fun postMessage(author: String, text: String) = async {
        val messageRequest = MessageRequest(author,text)
        messengerApi.postMessage(messageRequest).execute().body()
    }

    fun getLastMessages(sinceId: Int) = async {
        messengerApi.getLastMessages(sinceId).execute().body()
    }
}