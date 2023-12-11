package com.example.kinopoisk.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R

class ViewPagerAdapter(
    private var onboardingPicture: List<Int>,
    private var onboardingText: List<Int>,
) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemOnboardingPicture: ImageView = itemView.findViewById(R.id.onboarding_picture)
        val itemOnboardingText: ImageView = itemView.findViewById(R.id.onboarding_text_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.viewpager_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return onboardingPicture.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.itemOnboardingPicture.setImageResource(onboardingPicture[position])
        holder.itemOnboardingText.setImageResource(onboardingText[position])
        if (onboardingPicture[position] == onboardingPicture[3]) {
            holder.itemOnboardingPicture.layoutParams.height = 1
            holder.itemOnboardingPicture.layoutParams.width = 1
        }
        if (onboardingText[position] == onboardingText[3]) {
            holder.itemOnboardingText.layoutParams.height = 1
            holder.itemOnboardingText.layoutParams.width = 1
        }
    }
}
