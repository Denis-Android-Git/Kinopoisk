package com.example.domain.domain.entity

interface Pictures {
    val items: List<com.example.domain.domain.entity.Item>
    val total: Int
    val totalPages: Int
}