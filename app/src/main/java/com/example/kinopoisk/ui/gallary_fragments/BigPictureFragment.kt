package com.example.kinopoisk.ui.gallary_fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinopoisk.databinding.FragmentBigPictureBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("movieId")
        val index = arguments?.getInt("index")
        val category = arguments?.getString("category")

        val picturesViewModel =
            ViewModelProvider(this, PicturesViewModelFactory(id))[PicturesViewModel::class.java]

        category?.let {
            picturesViewModel.getPictures(it)
        }

        val adapter by lazy {
            BigPictureViewPagerAdapter()
        }
        binding.ViewPager2.adapter = adapter

        picturesViewModel.pictures?.onEach {
            adapter.submitData(it)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached) {
                if (index != null) {
                    binding.ViewPager2.visibility = View.VISIBLE
                    binding.ViewPager2.setCurrentItem(index, false)
                }
            }
        }


//        binding.ViewPager2.postDelayed(
//            {
//                if (index != null) {
//                    binding.ViewPager2.currentItem = index
//                    Log.d("index", "index======$index")
//                }
//            },
//            1800
//        )

//        binding.ViewPager2.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
//            if (index != null) {
//                binding.ViewPager2.post {
//                    binding.ViewPager2.setCurrentItem(index, true)
//                }
//                //binding.ViewPager2.currentItem = index
//                Log.d("index", "index======$index")
//            }
//        }
//        binding.ViewPager2.registerOnPageChangeCallback(object :
//            ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if (index != null) {
//                    binding.ViewPager2.currentItem = index
//                    Log.d("index", "index======$index")
//                    binding.ViewPager2.unregisterOnPageChangeCallback(this)
//                }
//            }
//        })
    }
}