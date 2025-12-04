package com.example.seguridadapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface Esp32Api {
    @GET("/toggle")
    suspend fun toggleDoor(): DoorResponse

    @GET("/historial")
    suspend fun getHistory(): List<String>
}


data class DoorResponse(
    val status: String,
    val mensaje: String
)

// Configuración del cliente Retrofit
object RetrofitClient {

    private const val BASE_URL = "http://192.168.137.176/"

    val api: Esp32Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Esp32Api::class.java)
    }
}