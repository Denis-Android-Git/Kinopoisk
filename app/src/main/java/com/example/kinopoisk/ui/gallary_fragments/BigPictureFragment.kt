package com.example.kinopoisk.ui.gallary_fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.kinopoisk.databinding.FragmentBigPictureBinding

class BigPictureFragment : Fragment() {

    private var _binding: FragmentBigPictureBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBigPictureBinding.inflate(inflater, container, false)

        val picture = arguments?.getString("pictureUrl")

        binding.bigPicture.load(picture)

        return binding.root
    }

}