package org.michaelbel.moviemade.app.data

import org.michaelbel.moviemade.app.data.model.Collection
import org.michaelbel.moviemade.app.data.model.Account
import org.michaelbel.moviemade.app.data.model.AccountStates
import org.michaelbel.moviemade.app.data.model.Company
import org.michaelbel.moviemade.app.data.model.CreditsResponse
import org.michaelbel.moviemade.app.data.model.DeletedSession
import org.michaelbel.moviemade.app.data.model.Fave
import org.michaelbel.moviemade.app.data.model.GenresResponse
import org.michaelbel.moviemade.app.data.model.GuestSession
import org.michaelbel.moviemade.app.data.model.ImagesResponse
import org.michaelbel.moviemade.app.data.model.Keyword
import org.michaelbel.moviemade.app.data.model.KeywordsResponse
import org.michaelbel.moviemade.app.data.model.Mark
import org.michaelbel.moviemade.app.data.model.Movie
import org.michaelbel.moviemade.app.data.model.MovieResponse
import org.michaelbel.moviemade.app.data.model.Network
import org.michaelbel.moviemade.app.data.model.Person
import org.michaelbel.moviemade.app.data.model.RequestToken
import org.michaelbel.moviemade.app.data.model.Result
import org.michaelbel.moviemade.app.data.model.Review
import org.michaelbel.moviemade.app.data.model.Session
import org.michaelbel.moviemade.app.data.model.SessionId
import org.michaelbel.moviemade.app.data.model.Token
import org.michaelbel.moviemade.app.data.model.Username
import org.michaelbel.moviemade.app.data.model.Video
import org.michaelbel.moviemade.app.data.model.Watch
import retrofit2.Response
import retrofit2.http.*

interface Api {

    //region Account

    @GET("account")
    suspend fun details(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Response<Account>

    @GET("account/{account_id}/favorite/movies")
    suspend fun moviesFavorite(
        @Path("account_id") accountId: Long,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") lang: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Response<Result<Movie>>

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Header("Content-Type") contentType: String,
        @Path("account_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body fave: Fave
    ): Response<Mark>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun moviesWatchlist(
        @Path("account_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Response<Result<Movie>>

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Header("Content-Type") contentType: String,
        @Path("account_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body watch: Watch
    ): Response<Mark>

    @GET("account/{account_id}/favorite/tv")
    suspend fun favoriteShows(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") lang: String,
        @Query("sort_by") sort: String
    ): Response<Result<Movie>>

    @GET("account/{account_id}/lists")
    suspend fun createdLists(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String
    ): Response<*>

    @GET("account/{account_id}/rated/parts")
    suspend fun ratedMovies(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Response<*>

    @GET("account/{account_id}/rated/tv")
    suspend fun ratedShows(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Response<*>

    @GET("account/{account_id}/rated/tv/episodes")
    suspend fun ratedEpisodes(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Response<*>

    @GET("account/{account_id}/watching/tv")
    suspend fun showsWatchlist(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Response<Result<Movie>>

    //endregion

    //region Auth

    @GET("authentication/guest_session/new?")
    suspend fun createGuestSession(
        @Query("api_key") apiKey: String
    ): Response<GuestSession>

    @GET("authentication/token/new?")
    suspend fun createRequestToken(@Query("api_key") apiKey: String): Response<Token>

    @POST("authentication/token/validate_with_login?")
    suspend fun createSessionWithLogin(
        @Query("api_key") apiKey: String,
        @Body username: Username
    ): Response<Token>

    @POST("authentication/session/new?")
    suspend fun createSession(
        @Query("api_key") apiKey: String,
        @Body authToken: RequestToken
    ): Response<Session>

    // createSession (from api v4)

    @HTTP(method = "DELETE", path = "authentication/session?", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String,
        @Body sessionId: SessionId
    ): Response<DeletedSession>

    //endregion

    //region Certifications

    @GET("certification/movie/list?")
    suspend fun movieCertifications(@Query("api_key") apiKey: String): Response<*>

    @GET("certification/tv/list?")
    suspend fun showCertifications(@Query("api_key") apiKey: String): Response<*>

    //endregion

    //region Changes

    @GET("movie/changes?")
    suspend fun movieChangeList(
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Response<*>

    @GET("tv/changes?")
    suspend fun showsChangeList(
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Response<*>

    @GET("person/changes?")
    suspend fun personChangeList(
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Response<*>

    //endregion

    //region Collections

    @GET("collection/{collection_id}?")
    suspend fun collection(
        @Path("collection_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Collection

    @GET("collection/{collection_id}/images?")
    suspend fun collectionImages(
        @Path("collection_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): ImagesResponse

    // getTranslations

    //endregion

    //region Companies

    @GET("company/{company_id}?")
    suspend fun company(
        @Path("company_id") id: Int,
        @Query("api_key") apiKey: String
    ): Company

    // getAlternativeNames

    // getImages

    //endregion

    //region Configurations

    @GET("configuration?")
    suspend fun apiConfiguration(): Response<*>

    @GET("configuration/countries?")
    suspend fun countries(): Response<*>

    @GET("configuration/jobs?")
    suspend fun jobs(): Response<*>

    @GET("configuration/languages?")
    suspend fun languages(): Response<*>

    @GET("configuration/primary_translations?")
    suspend fun primaryTranslations(): Response<*>

    @GET("configuration/timezones?")
    suspend fun timezones(): Response<*>

    //endregion

    //region Credits

    @GET("credit/{credit_id}?")
    suspend fun credit(
        @Path("credit_id") id: String,
        @Query("api_key") apiKey: String
    ): Response<*>

    //endregion

    //region Discover

    @GET("discover/movie?")
    suspend fun movieDiscover(): Response<*>

    @GET("discover/tv?")
    suspend fun tvDiscover(): Response<*>

    //endregion

    //region GuestSessions

    @GET("guest_session/{guest_session_id}/rated/parts?")
    suspend fun ratedMovies(): Response<*>

    @GET("guest_session/{guest_session_id}/rated/tv?")
    suspend fun ratedShows(): Response<*>

    @GET("guest_session/{guest_session_id}/rated/tv/episodes?")
    suspend fun ratedEpisodes(): Response<*>

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
    suspend fun movieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): GenresResponse

    @GET("genre/tv/list?")
    suspend fun tvList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): GenresResponse

    //endregion

    //region Find

    @GET("find/{external_id}?")
    suspend fun findById(
        @Path("external_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("external_source") ex_source: String
    ): Response<*>

    //endregion

    //region Keywords

    @GET("keyword/{keyword_id}?")
    suspend fun keyword(
        @Path("keyword_id") id: Int,
        @Query("api_key") apiKey: String
    ): Keyword

    @GET("keyword/{keyword_id}/movies")
    suspend fun moviesByKeyword(
        @Path("keyword_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("include_adult") adult: Boolean,
        @Query("page") page: Int
    ): Response<Result<Movie>>

    //endregion

    //region Movies

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String
    ): Response<Movie>

    @GET("movie/{movie_id}/account_states")
    suspend fun accountStates(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String
    ): Response<AccountStates>

    @GET("movie/{movie_id}/changes")
    suspend fun changes(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("page") page: Int
    ): Response<*>

    @GET("movie/{movie_id}/alternative_titles")
    suspend fun alternativeTitles(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("country") country: String
    ): Response<*>

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): CreditsResponse

    @GET("movie/{movie_id}/images")
    suspend fun movieImages(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("include_image_language") lang: String
    ): ImagesResponse

    @GET("movie/{movie_id}/keywords")
    suspend fun keywords(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String
    ): Response<KeywordsResponse>

    @GET("movie/{movie_id}/release_dates")
    suspend fun releaseDates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<*>

    @GET("movie/{movie_id}/videos")
    suspend fun trailers(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String
        //@Query("language") lang: String
    ): Response<Result<Video>>

    @GET("movie/{movie_id}/translations")
    suspend fun translations(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Response<*>

    /**
     * similar
     * recommendations
     */
    @GET("movie/{movie_id}/{list}")
    suspend fun moviesById(
        @Path("movie_id") id: Long,
        @Path("list") list: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Result<Movie>>

    @GET("movie/{movie_id}/reviews")
    suspend fun reviews(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String,
        //@Query("language") language: String,
        @Query("page") page: Int
    ): Response<Result<Review>>

    @GET("movie/{movie_id}/lists")
    suspend fun movieLists(
        @Path("movie_id") param: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Result<Movie>>

    // rateMovie

    // deleteRating

    @GET("movie/latest")
    suspend fun movieLatest(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Response<*>

    /**
     * now_playing
     * popular
     * top_rated
     * upcoming
     */
    @GET("movie/{list}")
    suspend fun movies(
        @Path("list") list: String,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Result<MovieResponse>

    //endregion

    //region Networks

    @GET("network/{network_id}?")
    suspend fun networkDetails(
        @Path("network_id") id: Int,
        @Query("api_key") apiKey: String
    ): Network

    // getAlternativeNames

    // getImages

    //endregion

    //region People

    @GET("person/{person_id}?")
    suspend fun personDetails(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") response: String
    ): Person

    @GET("person/{person_id}/changes?")
    suspend fun personChanges(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Nothing

    @GET("person/{person_id}/movie_credits?")
    suspend fun movieCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): CreditsResponse

    @GET("person/{person_id}/tv_credits?")
    suspend fun tvCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Nothing

    @GET("person/{person_id}/combined_credits?")
    suspend fun combinedCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Nothing

    @GET("person/{person_id}/external_ids?")
    suspend fun externalIDs(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Nothing

    @GET("person/{person_id}/images?")
    suspend fun personImages(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String
    ): Nothing

    @GET("person/{person_id}/tagged_images?")
    suspend fun taggedImages(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Nothing

    // getTranslations

    @GET("person/latest?")
    suspend fun personLatest(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Nothing

    @GET("person/popular?")
    suspend fun personPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result<Person>

    //endregion

    //region Reviews

    @GET("review/{review_id}?")
    suspend fun reviewDetails(
        @Path("review_id") id: String,
        @Query("api_key") apiKey: String
    ): Review

    //endregion

    //region Search

    @GET("search/company?")
    suspend fun searchCompanies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Result<Company>

    @GET("search/collection?")
    suspend fun searchCollections(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Result<Collection>

    @GET("search/keyword?")
    suspend fun searchKeywords(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Result<Keyword>

    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("region") region: String
        //@Query("year") int year,
        //@Query("primary_release_year") int primaryReleaseYear
    ): Response<Result<Movie>>

    @GET("search/multi?")
    suspend fun searchMulti(): Response<*>

    @GET("search/person?")
    suspend fun searchPeople(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("region") region: String
    ): Result<Person>

    @GET("search/tv?")
    suspend fun searchTvShows(): Response<*>

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