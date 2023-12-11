package com.example.kinopoisk.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.domain.entity.Movie
import com.example.kinopoisk.databinding.ItemBinding

class MovieListAdapter(
    private val onClick: (Movie) -> Unit,
) : ListAdapter<Movie, MovieListViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            if (item.ratingKinopoisk != 0.0) {
                rating.visibility = View.VISIBLE
                rating.text = item.ratingKinopoisk.toString()
            } else if (!item.rating.isNullOrEmpty()) {
                rating.visibility = View.VISIBLE
                rating.text = item.rating
            } else {
                rating.visibility = View.GONE
            }
            movieName.text = item?.nameRu ?: ""
            movieGenre.text = item?.genres?.joinToString(", ") {
                it.genre.toString()
            }
            imageView.load(item?.posterUrlPreview)

            root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }

}

class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId || oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}

class MovieListViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)