package com.example.kinopoisk.utils

import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.domain.domain.entity.Item
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.StaffItem
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import com.example.kinopoisk.R

fun onItemClick(item: Movie, fragment: Fragment) {
    val bundle = Bundle()
    val filmId = item.filmId
    val kinopoiskId = item.kinopoiskId

    bundle.putInt("filmId", filmId)
    bundle.putInt("kinopoiskId", kinopoiskId)

    fragment.findNavController().navigate(R.id.detailFragment, bundle)
}

fun onPictureClick(item: Item, imageView: ImageView, fragment: Fragment) {
    val bundle = Bundle()
    val pictureUrl = item.imageUrl
    bundle.putString("pictureUrl", pictureUrl)

    val extras = FragmentNavigatorExtras(imageView to "picture")

    fragment.findNavController().navigate(R.id.bigPictureFragment, bundle, null, extras)
}

fun onPersonClick(item: StaffItem, imageView: ImageView, fragment: Fragment) {
    val bundle = Bundle()
    val personId = item.staffId
    bundle.putInt("personId", personId)
    fragment.findNavController().navigate(R.id.actorFragment, bundle)
}

fun onPersonSearchClick(item: StaffItem, fragment: Fragment) {
    val bundle = Bundle()
    val personId = item.kinopoiskId
    bundle.putInt("personId", personId)
    fragment.findNavController().navigate(R.id.actorFragment, bundle)
}

fun onCollectionClick(collection: CollectionWithMovies, fragment: Fragment) {
    val bundle = Bundle()
    val collectionName = collection.collection.collectionName
    bundle.putString("collectionName", collectionName)
    fragment.findNavController().navigate(R.id.fullCollectionFragment, bundle)
}