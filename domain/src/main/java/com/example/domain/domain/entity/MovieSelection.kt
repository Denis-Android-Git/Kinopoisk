package com.example.domain.domain.entity

interface MovieSelection {
    val items: List<com.example.domain.domain.entity.Movie>
    val total: Int
    val totalPages: Int
}