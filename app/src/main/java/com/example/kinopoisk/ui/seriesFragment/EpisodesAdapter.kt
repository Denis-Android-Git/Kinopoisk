package com.example.kinopoisk.ui.seriesFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.databinding.ItemEpisodeBinding
import com.example.domain.domain.entity.Episode

class EpisodesAdapter(
    private val episodeList: List<Episode>
) : RecyclerView.Adapter<EpisodesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = episodeList.size

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val  item = episodeList[position]
        holder.binding.count.text = item.episodeNumber.toString()
        holder.binding.title.text = item.nameRu ?: item.nameEn
        holder.binding.date.text = item.releaseDate
    }
}

class EpisodesViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root)