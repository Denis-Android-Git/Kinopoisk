package com.example.data.data

import com.example.domain.domain.entity.Item
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.Season
import com.example.domain.domain.entity.StaffItem
import com.example.domain.domain.repository.Repository

class MovieListRepository : Repository {
    private val retrofitAndApi = RetrofitAndApi()
    override suspend fun getPremiers(month: String, year: String, page: Int?): List<Movie> {
        return retrofitAndApi.api.getPremiers(month, year, page).items
    }

    override suspend fun getTop250(page: Int?): List<Movie> {
        return retrofitAndApi.api.getTop250(page).films
    }

    override suspend fun getPopular(page: Int?): List<Movie> {
        return retrofitAndApi.api.getPopular(page).films
    }

    override suspend fun getSelection(
        countries: Int,
        genres: Int,
        type: String,
        yearFrom: Int,
        page: Int?
    ): List<Movie> {
        return retrofitAndApi.api.getSelection(countries, genres, type, yearFrom, page).items
    }

    override suspend fun getMovieDescription(id: Int): Movie {
        return retrofitAndApi.api.getMovieDescription(id)
    }

    override suspend fun getStaff(filmId: Int): ArrayList<StaffItem> {
        return retrofitAndApi.api.getStaff(filmId)
    }

    override suspend fun getPictures(id: Int, page: Int?, type: String): List<Item> {
        return retrofitAndApi.api.getPictures(id, page, type).items
    }

    override suspend fun getSimilars(id: Int): List<Movie> {
        return retrofitAndApi.api.getSimilars(id).items
    }

    override suspend fun getPerson(id: Int): PersonDto {
        return retrofitAndApi.api.getPersonDetails(id)
    }

    override suspend fun getSeries(id: Int): List<Season> {
        return retrofitAndApi.api.getSeries(id).items
    }

    override suspend fun search(
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
    ): List<Movie> {
        return retrofitAndApi.api.search(
            countries,
            genres,
            order,
            type,
            yearFrom,
            yearTo,
            ratingFrom,
            ratingTo,
            keyword,
            page
        ).items
    }

    override suspend fun searchPersons(name: String, page: Int): List<StaffItem> {
        return retrofitAndApi.api.searchPersons(name, page).items
    }
}