package com.example.kinopoisk.ui.gallary_fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.domain.entity.Item
import com.example.kinopoisk.databinding.ItemFullGalleryBinding

class PicturesAdapter(
    private val onClick: (Item, Int, ImageView) -> Unit
) : PagingDataAdapter<Item, PicturesViewHolder>(PicturesDiffUtilCallback()) {
    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {

            idIVImage.load(item?.previewUrl)

            root.setOnClickListener {
                idIVImage.transitionName = "picture"
                onClick.invoke(item!!, position, idIVImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        return PicturesViewHolder(
            ItemFullGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class PicturesDiffUtilCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}

class PicturesViewHolder(val binding: ItemFullGalleryBinding) :
    RecyclerView.ViewHolder(binding.root)