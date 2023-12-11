package com.example.kinopoisk.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.ItemHorizontalRecyclerBinding
import com.example.kinopoisk.utils.onItemClick

class VerticalAdapter(
    private val fragment: HomeFragment,
    private val collectionWithMovies: CollectionWithMovies
    ) :
    RecyclerView.Adapter<VerticalViewHolder>() {

    private var movieList: List<List<Movie>> = emptyList()

    fun setMovies(movies: List<List<Movie>>) {
        movieList = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        return VerticalViewHolder(
            ItemHorizontalRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val horizontalAdapter = MovieListAdapterHomeFragment(collectionWithMovies) { movie ->
            onItemClick(movie, fragment)
        }
        holder.binding.itemHorizontalRecycler.adapter = horizontalAdapter
        horizontalAdapter.submitList(movieList[position].take(20))

        val bundle = Bundle()
        val argsList = arrayListOf<String>()

        if (movieList[position].isNotEmpty()) {
            when (movieList[position]) {
                movieList[0] -> {
                    holder.binding.seeAll.setOnClickListener {
                        argsList.add("premiers")
                        bundle.putStringArrayList("1", argsList)
                        fragment.findNavController().navigate(R.id.fullMovieListFragment, bundle)
                    }
                }

                movieList[1] -> {
                    holder.binding.newMovies.text =
                        holder.binding.root.context.getString(R.string.top_250)
                    holder.binding.seeAll.setOnClickListener {
                        argsList.add("250")
                        bundle.putStringArrayList("1", argsList)
                        fragment.findNavController().navigate(R.id.fullMovieListFragment, bundle)
                    }
                }

                movieList[2] -> {
                    holder.binding.newMovies.text =
                        holder.binding.root.context.getString(R.string.popular)
                    holder.binding.seeAll.setOnClickListener {
                        argsList.add("popular")
                        bundle.putStringArrayList("1", argsList)
                        fragment.findNavController().navigate(R.id.fullMovieListFragment, bundle)
                    }
                }

                movieList[3] -> {
                    holder.binding.newMovies.text = "${
                        movieList[3].first().genres?.get(0)?.genre?.replaceFirstChar { it.uppercase() }
                    } ${
                        movieList[3].first().countries?.get(0)?.country
                    }"
                    holder.binding.seeAll.setOnClickListener {
                        argsList.add("selection1")
                        bundle.putStringArrayList("1", argsList)
                        fragment.findNavController().navigate(R.id.fullMovieListFragment, bundle)
                    }
                }

                movieList[4] -> {
                    holder.binding.newMovies.text =
                        holder.binding.root.context.getString(R.string.series)
                    holder.binding.seeAll.setOnClickListener {
                        argsList.add("series")
                        bundle.putStringArrayList("1", argsList)
                        fragment.findNavController().navigate(R.id.fullMovieListFragment, bundle)
                    }
                }

                movieList[5] -> {
                    holder.binding.newMovies.text = "${
                        movieList[5].first().genres?.get(0)?.genre?.replaceFirstChar { it.uppercase() }
                    } ${
                        movieList[5].first().countries?.get(0)?.country
                    }"
                    holder.binding.seeAll.setOnClickListener {
                        argsList.add("selection2")
                        bundle.putStringArrayList("1", argsList)
                        fragment.findNavController().navigate(R.id.fullMovieListFragment, bundle)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = movieList.size
}

class VerticalViewHolder(val binding: ItemHorizontalRecyclerBinding) :
    RecyclerView.ViewHolder(binding.root)