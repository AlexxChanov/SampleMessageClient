package com.example.samplemessageclient.data


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessengerApi {
    companion object {
        const val HOST = "https://sample-message-server.herokuapp.com"
    }

    @POST("/register")
    fun register(@Body user: User): Call<ApiResponse>

    @GET("/users")
    fun getAllUsers(): Call<List<User>>

    @GET("/allMessages")
    fun getAllMessanges(): Call<List<Message>>

    @POST("/postMessage")
    fun postMessage(@Body messageRequest: MessageRequest): Call<ApiResponse>

    @GET("lastMessages")
    fun getLastMessages(@Query("sinceId") sinceId: Int): Call<List<Message>>
}