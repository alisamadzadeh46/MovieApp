package com.example.movie.network

import com.example.movie.model.Cast
import com.example.movie.model.Movie
import com.example.movie.model.Movies
import com.example.movie.model.Profile
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/all/day")
    fun getTrendingMovie(@Query("page") page: Int): Single<Movies>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovie(@Path("movieId") movieId: Long): Single<Movie>

    @GET("person/{id}")
    fun getCstDetails(@Path("id") castId: Long): Single<Cast>

    @GET("person/{id}/images")
    fun getPersonImages(@Path("id") castId: Long): Single<Profile>
}