package com.example.kinopoisk.ui.search

fun main() {
    val list1 = listOf(
        Movie(
            id = 1,
            name = "One"
        ),
        Movie(
            id = 2,
            name = "Two"
        )
    )
    val list2 = listOf(
        DbMovie(
            id = 2,
            name = "TTT"
        ),
        DbMovie(
            id = 3,
            name = "FFF"
        )
    )
    println(filter(list1, list2))
}


data class Movie(
    val id: Int,
    val name: String
)

data class DbMovie(
    val id: Int,
    val name: String
)

fun filter(list1: List<Movie>, list2: List<DbMovie>): List<Movie> {
    return list1.filter { movie ->
        list2.none {
            it.id == movie.id
        }
    }
}