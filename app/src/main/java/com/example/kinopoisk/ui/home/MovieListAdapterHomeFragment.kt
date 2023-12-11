package com.example.kinopoisk.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.ItemBinding

class MovieListAdapterHomeFragment(
    private val collectionWithMovies: CollectionWithMovies,
    private val onClick: (Movie) -> Unit
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
            if (
                collectionWithMovies.movies.any { item.filmId == it.movieId } ||
                collectionWithMovies.movies.any { item.kinopoiskId == it.movieId }
            ) {
                iconViewed.visibility = View.VISIBLE
                imageView.foreground =
                    ContextCompat.getDrawable(root.context, R.drawable.gradient_item)
            } else {
                iconViewed.visibility = View.GONE
                imageView.foreground = null
            }
        }
    }
}