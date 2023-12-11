package com.example.data.data

import androidx.room.TypeConverter

class MovieIdListConverter {
    @TypeConverter
    fun fromList(list: MutableList<Int>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toList(string: String): MutableList<Int> {
        return if (string.isNotEmpty()) {
            string.split(",").map { it.toInt() }.toMutableList()
        } else {
            mutableListOf()
        }
    }
}