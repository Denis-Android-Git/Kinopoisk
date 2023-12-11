package com.example.data.data

import com.example.domain.domain.entity.Item
import com.example.domain.domain.entity.Pictures

class PicturesDto(
    override val items: List<Item>,
    override val total: Int,
    override val totalPages: Int
) : Pictures