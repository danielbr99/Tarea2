package com.example.tarea2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChampionAdapter (var championList: Champion): RecyclerView.Adapter<ChampionViewHolder>(){

    fun updateList(list: Champion) {
        championList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        return ChampionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.champion_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        holder.bind(championList.skins[position], championList)
    }
    override fun getItemCount(): Int = championList.skins.size
}