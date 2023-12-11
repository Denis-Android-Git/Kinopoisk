package com.example.kinopoisk.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.data.State
import com.example.kinopoisk.databinding.FragmentHomeBinding
import com.example.kinopoisk.ui.profile.ProfileViewModel
import com.example.kinopoisk.ui.profile.ProfileViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var bottomNavBarVisibilityListener: BottomNavBarVisibilityListener? = null

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val profileViewModel: ProfileViewModel by activityViewModels {
        ProfileViewModelFactory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavBarVisibilityListener = activity as? BottomNavBarVisibilityListener

        profileViewModel.collectionList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                val viewed = list.find {
                    it.collection.collectionName == "Viewed"
                }
                Log.d("viewed", "viewed----> ${viewed?.movies}")
                val verticalAdapter = VerticalAdapter(this@HomeFragment, viewed!!)

                binding.recyclerNewMovies.adapter = verticalAdapter

                homeViewModel.genresList.onEach {
                    verticalAdapter.setMovies(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                homeViewModel.state.collect {
                    when (it) {
                        State.Error -> {
                            binding.errorText.text = "Нет Интернета"
                            binding.loaderImage.visibility = View.GONE
                            binding.progressCircular.visibility = View.GONE
                            bottomNavBarVisibilityListener?.setBottomNavBarVisibility(true)
                        }

                        State.Success -> {
                            binding.errorText.text = ""
                            binding.loaderImage.visibility = View.GONE
                            binding.progressCircular.visibility = View.GONE
                            binding.recyclerNewMovies.visibility = View.VISIBLE
                            bottomNavBarVisibilityListener?.setBottomNavBarVisibility(true)
                        }

                        State.Loading -> {
                            binding.loaderImage.visibility = View.VISIBLE
                            binding.progressCircular.visibility = View.VISIBLE
                            binding.recyclerNewMovies.visibility = View.GONE
                            bottomNavBarVisibilityListener?.setBottomNavBarVisibility(false)
                        }
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface BottomNavBarVisibilityListener {
        fun setBottomNavBarVisibility(isVisible: Boolean)
    }
}