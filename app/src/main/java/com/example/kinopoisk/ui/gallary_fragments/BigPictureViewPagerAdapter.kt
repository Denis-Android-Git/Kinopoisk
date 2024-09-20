package com.example.kinopoisk.ui.gallary_fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.domain.entity.Item
import com.example.kinopoisk.databinding.ItemBigPictureBinding

class BigPictureViewPagerAdapter :
    PagingDataAdapter<Item, BigPicturesViewHolder>(PicturesDiffUtilCallback()) {

    override fun onBindViewHolder(holder: BigPicturesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            idIVImage.load(item?.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigPicturesViewHolder {
        return BigPicturesViewHolder(
            ItemBigPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class BigPicturesViewHolder(val binding: ItemBigPictureBinding) :
    RecyclerView.ViewHolder(binding.root)
