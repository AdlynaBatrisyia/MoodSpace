package com.example.a216765_wan_lab1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class QuoteResponse(
    val id: Int = 0,
    val quote: String = "",
    val author: String = ""
)

interface QuoteApiService {
    @GET("quotes/random")
    suspend fun getRandomQuote(): QuoteResponse
}

object QuoteApi {
    private const val BASE_URL = "https://dummyjson.com/"

    val service: QuoteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApiService::class.java)
    }
}