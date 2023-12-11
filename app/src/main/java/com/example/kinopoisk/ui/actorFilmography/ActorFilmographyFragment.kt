package com.example.kinopoisk.ui.actorFilmography

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kinopoisk.databinding.FragmentActorFilmographyBinding
import com.example.kinopoisk.utils.onItemClick
import com.google.android.material.chip.Chip

class ActorFilmographyFragment : Fragment() {

    private var _binding: FragmentActorFilmographyBinding? = null
    private val binding get() = _binding!!

    private val adapter = ActorFilmographyAdapter { movie ->
        onItemClick(movie, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorFilmographyBinding.inflate(inflater, container, false)

        val filmography = when {
            SDK_INT >= 33 -> arguments?.getParcelableArrayList("filmography", com.example.domain.domain.entity.Movie::class.java)
            else -> @Suppress("DEPRECATION") arguments?.getParcelableArrayList("filmography")
        }

        val actor = filmography?.filter {
            it.professionKey == "ACTOR" &&
                    !it.description?.contains("озвучка")!!
        }

        val voice = filmography?.filter {
            it.description?.contains("озвучка") == true
        }

        val himself = filmography?.filter {
            it.description?.contains("самого себя") == true
        }

        val director = filmography?.filter {
            it.professionKey == "DIRECTOR"
        }

        val writer = filmography?.filter {
            it.professionKey == "WRITER"
        }

        val producer = filmography?.filter {
            it.professionKey == "PRODUCER"
        }

        if (
            filmography?.get(0)?.professionKey == "ACTOR" ||
            filmography?.get(0)?.professionKey == "HERSELF" ||
            filmography?.get(0)?.professionKey == "HIMSELF"
        ) {

            binding.chip1.isChecked = true

            binding.recyclerFilmography.adapter = adapter
            adapter.submitList(actor)

            binding.chip1.setOnClickListener {
                binding.recyclerFilmography.adapter = adapter
                adapter.submitList(actor)
                clearChipCheck(binding.chip2, binding.chip3)
                binding.chip1.isEnabled = false
                binding.chip2.isEnabled = true
                binding.chip3.isEnabled = true
            }
            binding.chip2.setOnClickListener {
                binding.recyclerFilmography.adapter = adapter
                adapter.submitList(voice)
                clearChipCheck(binding.chip1, binding.chip3)
                binding.chip2.isEnabled = false
                binding.chip1.isEnabled = true
                binding.chip3.isEnabled = true
            }
            binding.chip3.setOnClickListener {
                binding.recyclerFilmography.adapter = adapter
                adapter.submitList(himself)
                clearChipCheck(binding.chip1, binding.chip2)
                binding.chip3.isEnabled = false
                binding.chip1.isEnabled = true
                binding.chip2.isEnabled = true
            }
        } else {

            binding.chip1.text = "Режиссер"
            binding.chip2.text = "Сценарист"
            binding.chip3.text = "Продюсер"

            binding.chip1.isChecked = true

            binding.recyclerFilmography.adapter = adapter
            adapter.submitList(director)

            binding.chip1.setOnClickListener {
                binding.recyclerFilmography.adapter = adapter
                adapter.submitList(director)
                clearChipCheck(binding.chip2, binding.chip3)
                binding.chip1.isEnabled = false
                binding.chip2.isEnabled = true
                binding.chip3.isEnabled = true
            }
            binding.chip2.setOnClickListener {
                binding.recyclerFilmography.adapter = adapter
                adapter.submitList(writer)
                clearChipCheck(binding.chip1, binding.chip3)
                binding.chip2.isEnabled = false
                binding.chip1.isEnabled = true
                binding.chip3.isEnabled = true
            }
            binding.chip3.setOnClickListener {
                binding.recyclerFilmography.adapter = adapter
                adapter.submitList(producer)
                clearChipCheck(binding.chip1, binding.chip2)
                binding.chip3.isEnabled = false
                binding.chip1.isEnabled = true
                binding.chip2.isEnabled = true
            }
        }

        return binding.root
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