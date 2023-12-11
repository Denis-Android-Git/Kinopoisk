package com.example.kinopoisk.ui.detail_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.kinopoisk.databinding.ItemStaffBinding
import com.example.domain.domain.entity.StaffItem

class ActorAdapter(
    private val onClick: (StaffItem, ImageView) -> Unit
) : ListAdapter<StaffItem, StaffViewHolder>(StaffDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        return StaffViewHolder(
            ItemStaffBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            imageView.load(item.posterUrl)
            actorName.text = item.nameRu
            profession.text = item.description ?: item.professionKey

            root.setOnClickListener {
                onClick.invoke(item, imageView)
            }
        }
    }
}