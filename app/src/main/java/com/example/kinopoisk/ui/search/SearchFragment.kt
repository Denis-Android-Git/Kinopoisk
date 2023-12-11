package com.example.kinopoisk.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentSearchBinding
import com.example.kinopoisk.utils.onItemClick
import com.example.kinopoisk.utils.onPersonSearchClick
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personAdapter by lazy {
            PersonDelegateAdapter {
                onPersonSearchClick(it, this)
            }
        }
        val movieAdapter by lazy {
            MovieDelegateAdapter {
                onItemClick(it, this)
            }
        }

        binding.recyclerSearch.adapter = movieAdapter
        binding.recyclerPerson.adapter = personAdapter

        binding.searchBar.setOnMenuItemClickListener {
            findNavController().navigate(R.id.searchSettingsFragment)
            true
        }

        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            binding.searchBar.setText(binding.searchView.text)
            binding.searchView.hide()
            false
        }

        binding.searchView.editText.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val keyword = s.toString()
                    dashboardViewModel.getMovies(keyword).onEach {
                        movieAdapter.submitData(it)
                        movieAdapter.addLoadStateListener { loadState ->
                            if (loadState.append.endOfPaginationReached) {
                                if (movieAdapter.itemCount < 1) {
                                    binding.errortextmovie.visibility = View.VISIBLE
                                }
                            }
                            if (movieAdapter.itemCount > 1) {
                                binding.errortextmovie.visibility = View.GONE
                            }
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)

                    dashboardViewModel.getPersons(keyword).onEach {
                        personAdapter.submitData(it)
                        personAdapter.addLoadStateListener { loadState ->
                            if (loadState.append.endOfPaginationReached) {
                                if (personAdapter.itemCount < 1) {
                                    binding.errortextperson.visibility = View.VISIBLE
                                }
                            }
                            if (personAdapter.itemCount > 1) {
                                binding.errortextperson.visibility = View.GONE
                            }
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}