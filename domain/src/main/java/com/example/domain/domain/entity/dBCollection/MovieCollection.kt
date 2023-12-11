package com.example.domain.domain.entity.dBCollection

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieCollection(
    @PrimaryKey
    @ColumnInfo(name = "collectionName")
    val collectionName: String,

    @ColumnInfo(name = "movieIdList")
    val movieIdList: MutableList<Int> = mutableListOf()
)
