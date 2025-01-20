package com.example.tarea2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChampionAdapter (var championList: List<String> = emptyList()): RecyclerView.Adapter<ChampionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        return ChampionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.champion_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        holder.bind(championList[position])
    }
    override fun getItemCount() = championList.size
}