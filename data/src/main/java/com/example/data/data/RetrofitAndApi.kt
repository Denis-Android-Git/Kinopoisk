package com.example.data.data

import com.example.domain.domain.entity.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY4

class RetrofitAndApi {

    val api: PremiersApi by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .build()
            .create(PremiersApi::class.java)
    }

    interface PremiersApi {

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/premieres?")
        suspend fun getPremiers(
            @Query("month") month: String,
            @Query("year") year: String,
            @Query("page") page: Int?
        ): MovieListDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
        suspend fun getTop250(
            @Query("page") page: Int?
        ): PagedMoviesDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
        suspend fun getPopular(
            @Query("page") page: Int?
        ): PagedMoviesDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films?order=RATING&ratingFrom=8&ratingTo=10&yearTo=3000")
        suspend fun getSelection(
            @Query("countries") countries: Int,
            @Query("genres") genres: Int,
            @Query("type") type: String,
            @Query("yearFrom") yearFrom: Int,
            @Query("page") page: Int?
        ): MovieSelectionDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/{id}")
        suspend fun getMovieDescription(
            @Path("id") id: Int
        ): Movie

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/{id}/images")
        suspend fun getPictures(
            @Path("id") id: Int,
            @Query("page") page: Int?,
            @Query("type") type: String
        ): PicturesDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/{id}/similars")
        suspend fun getSimilars(
            @Path("id") id: Int,
        ): MovieListDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v1/staff?")
        suspend fun getStaff(
            @Query("filmId") filmId: Int
        ): StaffDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v1/staff/{id}")
        suspend fun getPersonDetails(
            @Path("id") id: Int?
        ): PersonDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/{id}/seasons")
        suspend fun getSeries(
            @Path("id") id: Int
        ): SeriesDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films?")
        suspend fun search(
            @Query("countries") countries: Int?,
            @Query("genres") genres: Int?,
            @Query("order") order: String,
            @Query("type") type: String,
            @Query("yearFrom") yearFrom: Int,
            @Query("yearTo") yearTo: Int,
            @Query("ratingFrom") ratingFrom: Int,
            @Query("ratingTo") ratingTo: Int,
            @Query("keyword") keyword: String,
            @Query("page") page: Int?
        ): MovieSelectionDto

        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v1/persons?")
        suspend fun searchPersons(
            @Query("name") name: String,
            @Query("page") page: Int
        ): SearchPersonListDto
    }
}