package com.example.kinopoisk.ui.fullmovielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.data.data.State
import com.example.kinopoisk.databinding.FragmentFullMovieListBinding
import com.example.kinopoisk.ui.home.HomeFragment
import com.example.kinopoisk.ui.home.HomeViewModel
import com.example.kinopoisk.ui.home.MovieListAdapter
import com.example.kinopoisk.utils.onItemClick
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FullMovieListFragment : Fragment() {

    private var _binding: FragmentFullMovieListBinding? = null
    private val binding get() = _binding!!

    private val adapter = MovieListAdapter { movie ->
        onItemClick(movie, this)
    }

    private val pagedAdapter = FullMovieListAdapter { movie ->
        onItemClick(movie, this)
    }

    private var bottomNavBarVisibilityListener: HomeFragment.BottomNavBarVisibilityListener? = null

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFullMovieListBinding.inflate(inflater, container, false)

        bottomNavBarVisibilityListener = activity as? HomeFragment.BottomNavBarVisibilityListener

        bottomNavBarVisibilityListener?.setBottomNavBarVisibility(true)

        val argsList = arguments?.getStringArrayList("1")

        binding.recyclerFullMovieList.itemAnimator = SlideInLeftAnimator()

        when {
            argsList?.contains("premiers") == true -> {
                homeViewModel.getPremiers()
                bottomNavBarVisibilityListener?.setBottomNavBarVisibility(true)
                binding.recyclerFullMovieList.adapter = adapter
                homeViewModel.premiers.onEach {
                    adapter.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("250") == true -> {
                binding.recyclerFullMovieList.adapter = pagedAdapter
                homeViewModel.top250paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("popular") == true -> {
                binding.recyclerFullMovieList.adapter = pagedAdapter
                homeViewModel.popularPaged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("selection1") == true -> {
                binding.recyclerFullMovieList.adapter = pagedAdapter
                homeViewModel.selection1Paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("series") == true -> {
                binding.recyclerFullMovieList.adapter = pagedAdapter
                homeViewModel.seriesPaged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("selection2") == true -> {
                binding.recyclerFullMovieList.adapter = pagedAdapter
                homeViewModel.selection2Paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        pagedAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.swipeRefresh.setOnRefreshListener {
            pagedAdapter.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                homeViewModel.state.collect {
                    when (it) {
                        State.Error -> {
                            binding.errorText.text = "Нет Интернета"
                            binding.progressCircular.visibility = View.GONE
                        }

                        State.Success -> {
                            binding.errorText.text = ""
                            binding.progressCircular.visibility = View.GONE
                        }

                        State.Loading -> {
                            binding.progressCircular.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}