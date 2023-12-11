package com.example.domain.domain.entity.dBCollection

import androidx.room.Entity

@Entity(primaryKeys = ["collectionId", "id"])
data class CollectionMovieCrossRef(
    var collectionId: Int,
    var id: Int
)