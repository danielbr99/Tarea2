package com.example.tarea2

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException


interface ApiService {
    @GET("/cdn/15.2.1/data/en_US/champion/{championName}.json")
    suspend fun getChampions (@Path("championName") championName: String): Response<ChampionItemResponse>

}



