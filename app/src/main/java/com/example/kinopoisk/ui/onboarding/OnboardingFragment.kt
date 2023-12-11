package com.example.kinopoisk.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private val onboarding1 = mutableListOf(R.drawable.onboarding1, R.drawable.onboarding2, R.drawable.onboarding3, R.drawable.fake)
    private val onboarding2 = mutableListOf(R.drawable.onbtext1, R.drawable.onbtext2, R.drawable.onbtext3, R.drawable.fake)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        val root = binding.root

        val viewpager2 = binding.viewpager2

        viewpager2.adapter = ViewPagerAdapter(
            onboarding1,
            onboarding2
        )

        val indicator = binding.indicator

        indicator.setViewPager(viewpager2)

        binding.text.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboarding1.size - 1) {
                        findNavController().navigate(R.id.navigation_home)
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}