package com.example.data.data

import com.example.domain.domain.entity.SearchPersonList
import com.example.domain.domain.entity.StaffItem

class SearchPersonListDto(
    override val items: List<StaffItem>,
    override val total: Int
) : SearchPersonList
