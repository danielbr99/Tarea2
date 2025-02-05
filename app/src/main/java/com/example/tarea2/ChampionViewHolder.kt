package com.example.tarea2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea2.databinding.ChampionItemBinding
import com.squareup.picasso.Picasso

class ChampionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ChampionItemBinding.bind(view)

    fun bind(championSkinResponse: Skin, champion: Champion) {
        binding.tvChamName.text = champion.name
        binding.tvTitle.text = championSkinResponse.name
        binding.tvTags.text = champion.tags.toString()
        binding.tvPartype.text = champion.partype

        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+champion.name + "_" + championSkinResponse.num + ".jpg").resize(1000, 2000).centerCrop().into(binding.ivChampion)
    }
}
