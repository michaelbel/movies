package org.michaelbel.data.remote

import kotlinx.coroutines.Deferred
import org.michaelbel.data.remote.model.*
import org.michaelbel.data.remote.model.Collection
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response
import retrofit2.http.*

interface Api {

    //region Account

    @GET("account")
    fun details(
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String
    ): Deferred<Response<Account>>

    @GET("account/{account_id}/favorite/movies")
    fun moviesFavorite(
            @Path("account_id") accountId: Long,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") lang: String,
            @Query("sort_by") sort: String,
            @Query("page") page: Int
    ): Deferred<Response<Result<Movie>>>

    @POST("account/{account_id}/favorite")
    fun markAsFavorite(
            @Header("Content-Type") contentType: String,
            @Path("account_id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Body fave: Fave
    ): Deferred<Response<Mark>>

    @GET("account/{account_id}/watchlist/movies")
    fun moviesWatchlist(
            @Path("account_id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String,
            @Query("page") page: Int
    ): Deferred<Response<Result<Movie>>>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(
            @Header("Content-Type") contentType: String,
            @Path("account_id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Body watch: Watch
    ): Deferred<Response<Mark>>

    @GET("account/{account_id}/favorite/tv")
    fun favoriteShows(
            @Path("account_id") accountId: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") lang: String,
            @Query("sort_by") sort: String
    ): Deferred<Response<Result<Movie>>>

    @GET("account/{account_id}/lists")
    fun createdLists(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String
    ): Deferred<Response<*>>

    @GET("account/{account_id}/rated/parts")
    fun ratedMovies(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Deferred<Response<*>>

    @GET("account/{account_id}/rated/tv")
    fun ratedShows(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Deferred<Response<*>>

    @GET("account/{account_id}/rated/tv/episodes")
    fun ratedEpisodes(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Deferred<Response<*>>

    @GET("account/{account_id}/watching/tv")
    fun showsWatchlist(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Deferred<Response<Result<Movie>>>

    //endregion

    //region Auth

    @GET("authentication/guest_session/new?")
    fun createGuestSession(
            @Query("api_key") apiKey: String
    ): Deferred<Response<GuestSession>>

    @GET("authentication/token/new?")
    fun createRequestToken(@Query("api_key") apiKey: String): Deferred<Response<Token>>

    @POST("authentication/token/validate_with_login?")
    fun createSessionWithLogin(
            @Query("api_key") apiKey: String,
            @Body username: Username
    ): Deferred<Response<Token>>

    @POST("authentication/session/new?")
    fun createSession(
            @Query("api_key") apiKey: String,
            @Body authToken: RequestToken
    ): Deferred<Response<Session>>

    // createSession (from api v4)

    @HTTP(method = "DELETE", path = "authentication/session?", hasBody = true)
    fun deleteSession(
            @Query("api_key") apiKey: String,
            @Body sessionId: SessionId
    ): Deferred<Response<DeletedSession>>

    //endregion

    //region Certifications

    @GET("certification/movie/list?")
    fun movieCertifications(@Query("api_key") apiKey: String): Deferred<Response<*>>

    @GET("certification/tv/list?")
    fun showCertifications(@Query("api_key") apiKey: String): Deferred<Response<*>>

    //endregion

    //region Changes

    @GET("movie/changes?")
    fun movieChangeList(
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Deferred<Response<*>>

    @GET("tv/changes?")
    fun showsChangeList(
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Deferred<*>

    @GET("person/changes?")
    fun personChangeList(
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Deferred<*>

    //endregion

    //region Collections

    @GET("collection/{collection_id}?")
    fun collection(
            @Path("collection_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<Collection>

    @GET("collection/{collection_id}/images?")
    fun collectionImages(
            @Path("collection_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<ImagesResponse>

    // getTranslations

    //endregion

    //region Companies

    @GET("company/{company_id}?")
    fun company(
            @Path("company_id") id: Int,
            @Query("api_key") apiKey: String
    ): Deferred<Company>

    // getAlternativeNames

    // getImages

    //endregion

    //region Configurations

    @GET("configuration?")
    fun apiConfiguration(): Deferred<*>

    @GET("configuration/countries?")
    fun countries(): Deferred<*>

    @GET("configuration/jobs?")
    fun jobs(): Deferred<*>

    @GET("configuration/languages?")
    fun languages(): Deferred<*>

    @GET("configuration/primary_translations?")
    fun primaryTranslations(): Deferred<*>

    @GET("configuration/timezones?")
    fun timezones(): Deferred<*>

    //endregion

    //region Credits

    @GET("credit/{credit_id}?")
    fun credit(
            @Path("credit_id") id: String,
            @Query("api_key") apiKey: String
    ): Deferred<*>

    //endregion

    //region Discover

    @GET("discover/movie?")
    fun movieDiscover(): Deferred<*>

    @GET("discover/tv?")
    fun tvDiscover(): Deferred<*>

    //endregion

    //region GuestSessions

    @GET("guest_session/{guest_session_id}/rated/parts?")
    fun ratedMovies(): Deferred<*>

    @GET("guest_session/{guest_session_id}/rated/tv?")
    fun ratedShows(): Deferred<*>

    @GET("guest_session/{guest_session_id}/rated/tv/episodes?")
    fun ratedEpisodes(): Deferred<*>

    //endregion

    //region Lists

    // methods from v4
    // getDetails
    // checkItemStatus
    // createList
    // addMovie
    // removeMovie
    // clearList
    // deleteList

    //endregion

    //region Genres

    @GET("genre/movie/list?")
    fun movieList(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<GenresResponse>

    @GET("genre/tv/list?")
    fun tvList(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<GenresResponse>

    //endregion

    //region Find

    @GET("find/{external_id}?")
    fun findById(
            @Path("external_id") id: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("external_source") ex_source: String
    ): Deferred<*>

    //endregion

    //region Keywords

    @GET("keyword/{keyword_id}?")
    fun keyword(
            @Path("keyword_id") id: Int,
            @Query("api_key") apiKey: String
    ): Deferred<Keyword>

    @GET("keyword/{keyword_id}/movies")
    fun moviesByKeyword(
            @Path("keyword_id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("include_adult") adult: Boolean,
            @Query("page") page: Int
    ): Deferred<Response<Result<Movie>>>

    //endregion

    //region Movies

    @GET("movie/{movie_id}")
    fun movie(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String
    ): Deferred<Response<Movie>>

    @GET("movie/{movie_id}/account_states")
    fun accountStates(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String
    ): Deferred<Response<AccountStates>>

    @GET("movie/{movie_id}/changes")
    fun changes(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("page") page: Int
    ): Deferred<*>

    @GET("movie/{movie_id}/alternative_titles")
    fun alternativeTitles(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("country") country: String
    ): Deferred<*>

    @GET("movie/{movie_id}/credits")
    fun movieCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Deferred<CreditsResponse>

    @GET("movie/{movie_id}/images")
    fun movieImages(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("include_image_language") lang: String
    ): Deferred<ImagesResponse>

    @GET("movie/{movie_id}/keywords")
    fun keywords(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String
    ): Deferred<Response<KeywordsResponse>>

    @GET("movie/{movie_id}/release_dates")
    fun releaseDates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Deferred<*>

    @GET("movie/{movie_id}/videos")
    fun trailers(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String
        //@Query("language") lang: String
    ): Deferred<Response<Result<Video>>>

    @GET("movie/{movie_id}/translations")
    fun translations(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Deferred<Response<*>>

    /**
     * similar
     * recommendations
     */
    @GET("movie/{movie_id}/{list}")
    fun moviesById(
            @Path("movie_id") id: Long,
            @Path("list") list: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Deferred<Response<Result<Movie>>>

    @GET("movie/{movie_id}/reviews")
    fun reviews(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        //@Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<Result<Review>>>

    @GET("movie/{movie_id}/lists")
    fun movieLists(
        @Path("movie_id") param: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<Result<Movie>>>

    // rateMovie

    // deleteRating

    @GET("movie/latest")
    fun movieLatest(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Deferred<Response<*>>

    /**
     * now_playing
     * popular
     * top_rated
     * upcoming
     */
    @GET("movie/{list}")
    fun movies(
            @Path("list") list: String,
            @Query("api_key") apiKey: String,
            @Query("language") lang: String,
            @Query("page") page: Int
    ): Deferred<Response<Result<Movie>>>

    //endregion

    //region Networks

    @GET("network/{network_id}?")
    fun networkDetails(
            @Path("network_id") id: Int,
            @Query("api_key") apiKey: String
    ): Deferred<Network>

    // getAlternativeNames

    // getImages

    //endregion

    //region People

    @GET("person/{person_id}?")
    fun personDetails(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("append_to_response") response: String
    ): Deferred<Person>

    @GET("person/{person_id}/changes?")
    fun personChanges(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Deferred<*>

    @GET("person/{person_id}/movie_credits?")
    fun movieCredits(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<CreditsResponse>

    @GET("person/{person_id}/tv_credits?")
    fun tvCredits(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<*>

    @GET("person/{person_id}/combined_credits?")
    fun combinedCredits(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<*>

    @GET("person/{person_id}/external_ids?")
    fun externalIDs(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<*>

    @GET("person/{person_id}/images?")
    fun personImages(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String
    ): Deferred<*>

    @GET("person/{person_id}/tagged_images?")
    fun taggedImages(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Deferred<*>

    // getTranslations

    @GET("person/latest?")
    fun personLatest(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<*>

    @GET("person/popular?")
    fun personPopular(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Deferred<Result<Person>>

    //endregion

    //region Reviews

    @GET("review/{review_id}?")
    fun reviewDetails(
            @Path("review_id") id: String,
            @Query("api_key") apiKey: String
    ): Deferred<Review>

    //endregion

    //region Search

    @GET("search/company?")
    fun searchCompanies(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ): Deferred<Result<Company>>

    @GET("search/collection?")
    fun searchCollections(
            @Query("api_key") apiKey: String,
            @Query("language") lang: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ): Deferred<Result<Collection>>

    @GET("search/keyword?")
    fun searchKeywords(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ): Deferred<Result<Keyword>>

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
    ): Deferred<Response<Result<Movie>>>

    @GET("search/multi?")
    fun searchMulti(): Deferred<*>

    @GET("search/person?")
    fun searchPeople(
            @Query("api_key") apiKey: String,
            @Query("language") lang: String,
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("include_adult") adult: Boolean,
            @Query("region") region: String
    ): Deferred<Result<Person>>

    @GET("search/tv?")
    fun searchTvShows(): Deferred<*>

    //endregion

    //region Trending

    // getTrending

    //endregion

    //region TvEpisodeGroups

    // getDetails

    //endregion

    //region TvEpisodes

    // getDetails

    // getChanges

    // getAccountStates

    // getCredits

    // getTVEpisodeExternalIDs

    // getImages

    // rateTVEpisode

    // deleteRating

    // trailers

    //endregion

    //region TvSeasons

    // getDetails

    // getChanges

    // getAccountStates

    // getCredits

    // getExternalIDs

    // getImages

    // trailers

    //endregion

    //region TV

    // getDetails
    // getAccountStates
    // getAlternativeTitles
    // getChanges
    // getContentRatings
    // getCredits
    // getExternalIDs
    // getImages
    // getList
    // getRecommendations
    // getScreenedTheatrically
    // getSimilarTVShows
    // getTranslations
    // trailers
    // rateTVShow
    // deleteRating
    // getLatest
    // getAiringToday
    // getTVOnTheAir
    // getPopular
    // getTopRated

    //endregion
}