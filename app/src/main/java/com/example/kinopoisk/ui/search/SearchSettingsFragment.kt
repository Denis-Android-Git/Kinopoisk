package com.example.kinopoisk.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentSearchSettingsBinding
import kotlinx.coroutines.launch

class SearchSettingsFragment : Fragment() {

    private var _binding: FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerCountry.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.Countries_key)
        )

        binding.spinnerGenre.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.Genre_key)
        )

        binding.spinnerCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCountry =
                        resources.getIntArray(R.array.Countries_int_value)[position]

                    if (selectedCountry == 0) {
                        viewModel.changeCountry(null)
                    } else {
                        viewModel.changeCountry(selectedCountry)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.spinnerGenre.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedGenre =
                        resources.getIntArray(R.array.Genre_int_value)[position]

                    if (selectedGenre == 0) {
                        viewModel.changeGenre(null)
                    } else {
                        viewModel.changeGenre(selectedGenre)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchSettings.collect { state ->

                    val genre = if (state.selectedGenre != null) {
                        resources.getIntArray(R.array.Genre_int_value)
                            .indexOf(state.selectedGenre!!)
                    } else {
                        0
                    }

                    val country = if (state.selectedCountry == null) {
                        0
                    } else {
                        resources.getIntArray(R.array.Countries_int_value)
                            .indexOf(state.selectedCountry!!)
                    }

                    binding.spinnerGenre.setSelection(genre)
                    binding.spinnerCountry.setSelection(country)

                    binding.yearsSeekBar.addOnChangeListener { slider, _, _ ->
                        val minValue = slider.values.first().toInt()
                        val maxValue = slider.values.last().toInt()

                        if (minValue == 1950 && maxValue == 2023) {
                            binding.textViewYearCurrent.text = "Любой"
                        } else {
                            binding.textViewYearCurrent.text = buildString {
                                append("С ")
                                append(minValue)
                                append(" - до ")
                                append(maxValue)
                                append(" гг")
                            }
                        }
                        viewModel.changeYearFrom(minValue)
                        viewModel.changeYearTo(maxValue)
                    }

                    binding.ratingSeekBar.addOnChangeListener { slider, _, _ ->
                        val minValue = slider.values.first().toInt()
                        val maxValue = slider.values.last().toInt()

                        if (minValue == 0 && maxValue == 10) {
                            binding.textViewRatingCurrent.text = "Любой"
                        } else {
                            binding.textViewRatingCurrent.text = buildString {
                                append(minValue)
                                append(" - ")
                                append(maxValue)
                            }
                        }
                        viewModel.changeRatingFrom(minValue)
                        viewModel.changeRatingTo(maxValue)
                    }

                    binding.yearsSeekBar.setValues(state.yearFrom.toFloat(), state.yearTo.toFloat())
                    binding.ratingSeekBar.setValues(
                        state.ratingFrom.toFloat(),
                        state.ratingTo.toFloat()
                    )
                    binding.typeButtonGroup.check(
                        when (state.type) {
                            "ALL" -> R.id.type_left_button
                            "FILM" -> R.id.type_center_button
                            "TV_SERIES" -> R.id.type_right_button
                            else -> -1
                        }
                    )
                    binding.sortButtonGroup.check(
                        when (state.selectedOrder) {
                            "YEAR" -> R.id.sort_left_button
                            "NUM_VOTE" -> R.id.sort_center_button
                            "RATING" -> R.id.sort_right_button
                            else -> -1
                        }
                    )
                }
            }
        }

        binding.typeLeftButton.setOnClickListener {
            val typeList = resources.getStringArray(R.array.type_movie)
            viewModel.changeType(typeList[0])
        }

        binding.typeCenterButton.setOnClickListener {
            val typeList = resources.getStringArray(R.array.type_movie)
            viewModel.changeType(typeList[1])
        }

        binding.typeRightButton.setOnClickListener {
            val typeList = resources.getStringArray(R.array.type_movie)
            viewModel.changeType(typeList[2])
        }

        binding.sortLeftButton.setOnClickListener {
            val typeList = resources.getStringArray(R.array.order)
            viewModel.changeOrder(typeList[0])
        }

        binding.sortCenterButton.setOnClickListener {
            val typeList = resources.getStringArray(R.array.order)
            viewModel.changeOrder(typeList[1])
        }

        binding.sortRightButton.setOnClickListener {
            val typeList = resources.getStringArray(R.array.order)
            viewModel.changeOrder(typeList[2])
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}