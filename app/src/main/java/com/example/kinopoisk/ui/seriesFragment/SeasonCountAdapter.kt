package com.example.kinopoisk.ui.seriesFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.databinding.ItemSeasonBinding
import com.example.domain.domain.entity.Episode
import com.example.domain.domain.entity.Season

class SeasonCountAdapter(
    private val seasonList: List<Season>
) : RecyclerView.Adapter<SeasonCountViewHolder>() {

    private var seasonClickListener: SeasonClickListener? = null
    var selectedSeasonNumber: Int? = null

    fun setSeasonClickListener(listener: SeasonClickListener) {
        this.seasonClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonCountViewHolder {
        return SeasonCountViewHolder(
            ItemSeasonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    override fun onBindViewHolder(holder: SeasonCountViewHolder, position: Int) {
        val item = seasonList[position]
        val seasonNumber = item.number

        holder.binding.chip.isChecked = seasonNumber == selectedSeasonNumber
        holder.binding.chip.isEnabled = seasonNumber != selectedSeasonNumber

        holder.binding.chip.text = (item.number + 1 - seasonList[0].number).toString()

        holder.binding.chip.setOnClickListener {
            seasonClickListener?.onSeasonClicked(item.number, item.episodes!!)
        }
    }

    interface SeasonClickListener {
        fun onSeasonClicked(seasonNumber: Int, episodes: List<Episode>)
    }

}

class SeasonCountViewHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root)