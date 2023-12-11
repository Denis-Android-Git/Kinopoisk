package com.example.kinopoisk.ui.fullmovielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinopoisk.databinding.ItemBinding
import com.example.domain.domain.entity.Movie
import com.example.kinopoisk.ui.home.DiffUtilCallback

class FullMovieListAdapter(
    private val onClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, FullListViewHolder>(DiffUtilCallback()) {
    override fun onBindViewHolder(holder: FullListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            if (!item?.rating.isNullOrEmpty()) {
                rating.visibility = View.VISIBLE
                rating.text = item?.rating
            } else {
                rating.visibility = View.GONE
            }
            movieName.text = item?.nameRu ?: ""
            movieGenre.text = item?.genres?.joinToString(", ") {
                it.genre.toString()
            }
            imageView.load(item?.posterUrlPreview)

            root.setOnClickListener {
                onClick.invoke(item!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullListViewHolder {
        return FullListViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class FullListViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
