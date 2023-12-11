package com.example.domain.domain.repository

import com.example.domain.domain.entity.Item
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.Person
import com.example.domain.domain.entity.Season
import com.example.domain.domain.entity.StaffItem

interface Repository {
    suspend fun getPremiers(month: String, year: String, page: Int?): List<Movie>

    suspend fun getTop250(page: Int?): List<Movie>

    suspend fun getPopular(page: Int?): List<Movie>

    suspend fun getSelection(
        countries: Int,
        genres: Int,
        type: String,
        yearFrom: Int,
        page: Int?
    ): List<Movie>

    suspend fun getMovieDescription(id: Int): Movie

    suspend fun getStaff(filmId: Int): ArrayList<StaffItem>

    suspend fun getPictures(id: Int, page: Int?, type: String): List<Item>

    suspend fun getSimilars(id: Int): List<Movie>

    suspend fun getPerson(id: Int): Person

    suspend fun getSeries(id: Int): List<Season>

    suspend fun search(
        countries: Int?,
        genres: Int?,
        order: String,
        type: String,
        yearFrom: Int,
        yearTo: Int,
        ratingFrom: Int,
        ratingTo: Int,
        keyword: String,
        page: Int?
    ): List<Movie>

    suspend fun searchPersons(name: String, page: Int): List<StaffItem>
}