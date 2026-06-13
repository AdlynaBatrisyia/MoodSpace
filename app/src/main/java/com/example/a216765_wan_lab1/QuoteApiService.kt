package com.example.a216765_wan_lab1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class QuoteResponse(
    val _id: String = "",
    val content: String = "",
    val author: String = ""
)

interface QuoteApiService {
    @GET("quotes/random")
    suspend fun getRandomQuote(
        @Query("tags") tags: String = "happiness|inspirational|life"
    ): QuoteResponse
}

object QuoteApi {
    private const val BASE_URL = "https://api.quotable.io/"

    val service: QuoteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApiService::class.java)
    }
}