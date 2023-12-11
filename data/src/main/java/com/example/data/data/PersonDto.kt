package com.example.data.data

import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.Person

class PersonDto(
    override val age: Int,
    override val birthday: String,
    override val birthplace: String,
    override val death: Any,
    override val deathplace: Any,
    override val facts: List<Any>,
    override val films: List<Movie>,
    override val growth: Int,
    override val hasAwards: Int,
    override val nameEn: String,
    override val nameRu: String,
    override val personId: Int,
    override val posterUrl: String,
    override val profession: String,
    override val sex: String,
    override val spouses: List<Any>,
    override val webUrl: String
) : Person