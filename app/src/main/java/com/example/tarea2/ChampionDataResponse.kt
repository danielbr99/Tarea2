package com.example.tarea2

import com.google.gson.annotations.SerializedName


data class ChampionItemResponse(
    @SerializedName("type") val type: String,
    @SerializedName("format") val format: String,
    @SerializedName("version") val version: String,
    @SerializedName("data") val championDataList: Map<String, Champion>,

    )

data class Champion(
    @SerializedName("name") val name: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val championImage: ChampionImageResponse,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("partype") val partype: String,
    @SerializedName("skins") val skins: List<Skin>,
)

data class Skin(
    @SerializedName("id") val id: String,
    @SerializedName("num") val num: Int,
    @SerializedName("name") val name: String,
    @SerializedName("chromas") val chromas: Boolean,
)


data class ChampionImageResponse(
    @SerializedName("full") val full: String,
    @SerializedName("sprite") val sprite: String,
    @SerializedName("group") val group: String,
    @SerializedName("x") val x: Int,
    @SerializedName("y") val y: Int,
    @SerializedName("w") val w: Int,
    @SerializedName("h") val h: Int,
)