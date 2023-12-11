package com.example.domain.domain.entity.dBCollection

import androidx.room.Embedded
import androidx.room.Relation

data class CollectionWithMovies(
    @Embedded val collection: com.example.domain.domain.entity.dBCollection.Collection,
    @Relation(
        parentColumn = "collectionId",
        entityColumn = "collectionId"
    )
    val movies: List<com.example.domain.domain.entity.dBCollection.MovieId>
)
