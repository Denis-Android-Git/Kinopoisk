package com.example.domain.domain.entity

interface Person {
    val age: Int
    val birthday: String
    val birthplace: String
    val death: Any
    val deathplace: Any
    val facts: List<Any>
    val films: List<com.example.domain.domain.entity.Movie>
    val growth: Int
    val hasAwards: Int
    val nameEn: String
    val nameRu: String
    val personId: Int
    val posterUrl: String
    val profession: String
    val sex: String
    val spouses: List<Any>
    val webUrl: String
}