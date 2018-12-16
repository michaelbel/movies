package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.*
import retrofit2.http.GET
import retrofit2.http.Query

interface SEARCH {

    @GET("search/company?")
    fun searchCompanies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<CompaniesResponse>

    @GET("search/collection?")
    fun searchCollections(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<CollectionsResponse>

    @GET("search/keyword?")
    fun searchKeywords(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<SearchKeywordsResponse>

    @GET("search/movie?")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("region") region: String
        //@Query("year") int year,
        //@Query("primary_release_year") int primaryReleaseYear
    ): Observable<MoviesResponse>

    @GET("search/multi?")
    fun searchMulti(): Observable<*>

    @GET("search/person?")
    fun searchPeople(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("region") region: String
    ): Observable<PersonsResponse>

    @GET("search/tv?")
    fun searchTvShows(): Observable<*>
}