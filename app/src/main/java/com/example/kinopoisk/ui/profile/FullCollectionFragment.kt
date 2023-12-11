package com.example.kinopoisk.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kinopoisk.databinding.FragmentFullCollectionBinding
import com.example.kinopoisk.ui.home.MovieListAdapter
import com.example.kinopoisk.utils.onItemClick
import kotlinx.coroutines.launch

class FullCollectionFragment : Fragment() {

    private var _binding: FragmentFullCollectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieListAdapter {
            onItemClick(it, this)
        }
        binding.recyclerFullMovieList.adapter = adapter
        val collectionName = arguments?.getString("collectionName")

        viewModel.collectionList.observe(viewLifecycleOwner) { collection ->
            if (collection.isNotEmpty()) {
                val findCollection = collection.find {
                    it.collection.collectionName == collectionName
                }
                findCollection?.collection?.collectionName?.let { Log.d("tag", it) }

                findCollection?.movies?.let { viewModel.getOtherCollection(it) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.otherCollection.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}