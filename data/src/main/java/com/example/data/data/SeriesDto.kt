package com.example.data.data

import com.example.domain.domain.entity.Season
import com.example.domain.domain.entity.Series

class SeriesDto(
    override val items: List<Season>,
    override val total: Int
) : Series