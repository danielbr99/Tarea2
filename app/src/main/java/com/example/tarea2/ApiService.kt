package com.example.tarea2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("search/{name}")
    suspend fun getSuperheroes (@Path("name") championName: String): Response<ChampionDataResponse>

    @GET("{id}")
    suspend fun getSuperheroDetail (@Path("id") championid: String): Response<ChampionDetailResponse>

}