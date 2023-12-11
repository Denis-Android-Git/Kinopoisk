package com.example.kinopoisk.ui.actorFilmography

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.databinding.ItemFilmographyBinding
import com.example.domain.domain.entity.Movie
import com.example.kinopoisk.ui.home.DiffUtilCallback

class ActorFilmographyAdapter(
    private val onClick: (Movie) -> Unit
) : ListAdapter<Movie, FilmographyViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyViewHolder {
        return FilmographyViewHolder(
            ItemFilmographyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            filmName.text = item.nameRu ?: item.nameEn
            genres.text = item.description
            root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }
}

class FilmographyViewHolder(val binding: ItemFilmographyBinding) :
    RecyclerView.ViewHolder(binding.root)
