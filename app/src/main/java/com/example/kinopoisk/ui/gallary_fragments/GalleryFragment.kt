package com.example.kinopoisk.ui.gallary_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kinopoisk.databinding.FragmentGallaryBinding
import com.example.kinopoisk.utils.onPictureClick
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryFragment : Fragment() {

    private var _binding: FragmentGallaryBinding? = null
    private val binding get() = _binding!!

    private val picturesAdapter = PicturesAdapter { picture, imageView ->
        onPictureClick(picture, imageView, this)
    }

    private var currentCategory: String = "STILL"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGallaryBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("id")

        val picturesViewModel =
            ViewModelProvider(this, PicturesViewModelFactory(id))[PicturesViewModel::class.java]

        binding.chip1.isChecked = true

        binding.chip1.setOnClickListener {
            currentCategory = "STILL"
            onChipClicked(currentCategory, picturesViewModel)
            clearChipCheck(binding.chip2, binding.chip3)
            binding.chip1.isEnabled = false
            binding.chip2.isEnabled = true
            binding.chip3.isEnabled = true
        }
        binding.chip2.setOnClickListener {
            currentCategory = "SHOOTING"
            onChipClicked(currentCategory, picturesViewModel)
            clearChipCheck(binding.chip1, binding.chip3)
            binding.chip2.isEnabled = false
            binding.chip1.isEnabled = true
            binding.chip3.isEnabled = true
        }
        binding.chip3.setOnClickListener {
            currentCategory = "POSTER"
            onChipClicked(currentCategory, picturesViewModel)
            clearChipCheck(binding.chip1, binding.chip2)
            binding.chip3.isEnabled = false
            binding.chip1.isEnabled = true
            binding.chip2.isEnabled = true
        }

        onChipClicked(currentCategory, picturesViewModel)

        return binding.root
    }

    private fun onChipClicked(category: String, viewModel: PicturesViewModel) {
        binding.recyclerGallery.apply {
            layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
            adapter = picturesAdapter
        }

        viewModel.getPictures(category)
        viewModel.pictures?.onEach {
            picturesAdapter.submitData(it)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun clearChipCheck(chip1: Chip, chip2: Chip) {
        chip1.isChecked = false
        chip2.isChecked = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
