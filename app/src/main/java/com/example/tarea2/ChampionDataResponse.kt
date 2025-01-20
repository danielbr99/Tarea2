package com.example.tarea2

import com.google.gson.annotations.SerializedName

data class ChampionDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<ChampionItemResponse>
)

data class ChampionItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superheroImage: ChampionImageResponse
)

data class ChampionImageResponse(@SerializedName("url") val url: String)