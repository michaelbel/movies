package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.*
import org.michaelbel.moviemade.core.entity.Collection
import retrofit2.http.*

interface Api {

    //region AccountService

    @GET("account")
    fun getDetails(
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String
    ): Observable<Account>

    @GET("account/{account_id}/favorite/movies")
    fun moviesFavorite(
            @Path("account_id") accountId: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") lang: String,
            @Query("sort_by") sort: String,
            @Query("page") page: Int
    ): Observable<MoviesResponse>

    @POST("account/{account_id}/favorite")
    fun markAsFavorite(
            @Header("Content-Type") contentType: String,
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Body fave: Fave
    ): Observable<Mark>

    @GET("account/{account_id}/watchlist/movies")
    fun moviesWatchlist(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String,
            @Query("page") page: Int
    ): Observable<MoviesResponse>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(
            @Header("Content-Type") contentType: String,
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Body watch: Watch
    ): Observable<Mark>

    @GET("account/{account_id}/favorite/tv")
    fun getFavoriteTVShows(
            @Path("account_id") accountId: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") lang: String,
            @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    @GET("account/{account_id}/lists")
    fun getCreatedLists(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String
    ): Observable<*>

    @GET("account/{account_id}/rated/parts")
    fun getRatedMovies(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Observable<*>

    @GET("account/{account_id}/rated/tv")
    fun getRatedTVShows(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Observable<*>

    @GET("account/{account_id}/rated/tv/episodes")
    fun getRatedTVEpisodes(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Observable<*>

    @GET("account/{account_id}/watching/tv")
    fun getTVShowsWatchlist(
            @Path("account_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("session_id") sessionId: String,
            @Query("language") language: String,
            @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    //endregion

    //region AuthService

    @GET("authentication/guest_session/new?")
    fun createGuestSession(@Query("api_key") apiKey: String): Observable<GuestSession>

    @GET("authentication/token/new?")
    fun createRequestToken(@Query("api_key") apiKey: String): Observable<Token>

    @POST("authentication/token/validate_with_login?")
    fun createSessionWithLogin(
            @Query("api_key") apiKey: String,
            @Body username: Username
    ): Observable<Token>

    @POST("authentication/session/new?")
    fun createSession(
            @Query("api_key") apiKey: String,
            @Body authToken: RequestToken
    ): Observable<Session>

    // createSession (from api v4)

    @HTTP(method = "DELETE", path = "authentication/session?", hasBody = true)
    fun deleteSession(
            @Query("api_key") apiKey: String,
            @Body sessionId: SessionId
    ): Observable<DeletedSession>

    //endregion

    //region CertificationsService

    @GET("certification/movie/list?")
    fun getMovieCertifications(@Query("api_key") apiKey: String): Observable<*>

    @GET("certification/tv/list?")
    fun getTVCertifications(@Query("api_key") apiKey: String): Observable<*>

    //endregion

    //region ChangesService

    @GET("movie/changes?")
    fun getMovieChangeList(
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Observable<*>

    @GET("tv/changes?")
    fun getTVChangeList(
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Observable<*>

    @GET("person/changes?")
    fun getPersonChangeList(
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Observable<*>

    //endregion

    //region CollectionsService

    @GET("collection/{collection_id}?")
    fun getDetails(
            @Path("collection_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<Collection>

    @GET("collection/{collection_id}/images?")
    fun collectionImages(
            @Path("collection_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<ImagesResponse>

    // getTranslations

    //endregion

    //region CompaniesService

    @GET("company/{company_id}?")
    fun companyDetails(
            @Path("company_id") id: Int,
            @Query("api_key") apiKey: String
    ): Observable<Company>

    // getAlternativeNames

    // getImages

    //endregion

    //region ConfigurationsService

    @GET("configuration?")
    fun apiConfiguration(): Observable<*>

    @GET("configuration/countries?")
    fun countries(): Observable<*>

    @GET("configuration/jobs?")
    fun jobs(): Observable<*>

    @GET("configuration/languages?")
    fun languages(): Observable<*>

    @GET("configuration/primary_translations?")
    fun primaryTranslations(): Observable<*>

    @GET("configuration/timezones?")
    fun timezones(): Observable<*>

    //endregion

    //region CreditsService

    @GET("credit/{credit_id}?")
    fun creditDetails(
            @Path("credit_id") id: String,
            @Query("api_key") apiKey: String
    ): Observable<*>

    //endregion

    //region DiscoverService

    @GET("discover/movie?")
    fun movieDiscover(): Observable<*>

    @GET("discover/tv?")
    fun tvDiscover(): Observable<*>

    //endregion

    //region GuestSessionsService

    @GET("guest_session/{guest_session_id}/rated/parts?")
    fun getRatedMovies(): Observable<*>

    @GET("guest_session/{guest_session_id}/rated/tv?")
    fun getRatedTVShows(): Observable<*>

    @GET("guest_session/{guest_session_id}/rated/tv/episodes?")
    fun getRatedTVEpisodes(): Observable<*>

    //endregion

    //region ListsService

    // methods from v4
    // getDetails
    // checkItemStatus
    // createList
    // addMovie
    // removeMovie
    // clearList
    // deleteList

    //endregion

    //region GenresService

    @GET("genre/movie/list?")
    fun getMovieList(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<GenresResponse>

    @GET("genre/tv/list?")
    fun getTVList(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<GenresResponse>

    //endregion

    //region FindService

    @GET("find/{external_id}?")
    fun findById(
            @Path("external_id") id: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("external_source") ex_source: String
    ): Observable<*>

    //endregion

    //region KeywordsService

    @GET("keyword/{keyword_id}?")
    fun getDetails(
            @Path("keyword_id") id: Int,
            @Query("api_key") apiKey: String
    ): Observable<Keyword>

    @GET("keyword/{keyword_id}/movies")
    fun moviesByKeyword(
            @Path("keyword_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("include_adult") adult: Boolean,
            @Query("page") page: Int
    ): Observable<MoviesResponse>

    //endregion

    //region MoviesService

    @GET("movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String
    ): Observable<Movie>

    @GET("movie/{movie_id}/account_states")
    fun getAccountStates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String
    ): Observable<AccountStates>

    @GET("movie/{movie_id}/changes")
    fun getChanges(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("page") page: Int
    ): Observable<*>

    @GET("movie/{movie_id}/alternative_titles")
    fun getAlternativeTitles(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("country") country: String
    ): Observable<*>

    @GET("movie/{movie_id}/credits")
    fun getCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<CreditsResponse>

    @GET("movie/{movie_id}/images")
    fun getImages(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("include_image_language") lang: String
    ): Observable<ImagesResponse>

    @GET("movie/{movie_id}/keywords")
    fun getKeywords(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<KeywordsResponse>

    @GET("movie/{movie_id}/release_dates")
    fun getReleaseDates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<*>

    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Observable<VideosResponse>

    @GET("movie/{movie_id}/translations")
    fun getTranslations(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Observable<*>

    /**
     * similar
     * recommendations
     */
    @GET("movie/{movie_id}/{list}")
    fun moviesById(
            @Path("movie_id") id: Int,
            @Path("list") list: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Observable<MoviesResponse>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<ReviewsResponse>

    @GET("movie/{movie_id}/lists")
    fun getLists(
        @Path("movie_id") param: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    // rateMovie

    // deleteRating

    @GET("movie/latest")
    fun getLatest(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Observable<*>

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
    ): Observable<MoviesResponse>

    //endregion

    //region NetworksService

    @GET("network/{network_id}?")
    fun networkDetails(
            @Path("network_id") id: Int,
            @Query("api_key") apiKey: String
    ): Observable<Network>

    // getAlternativeNames

    // getImages

    //endregion

    //region PeopleService

    @GET("person/{person_id}?")
    fun personDetails(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("append_to_response") response: String
    ): Observable<Person>

    @GET("person/{person_id}/changes?")
    fun personChanges(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("end_date") endDate: String,
            @Query("start_date") startDate: String,
            @Query("page") page: Int
    ): Observable<*>

    @GET("person/{person_id}/movie_credits?")
    fun getMovieCredits(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<CreditsResponse>

    @GET("person/{person_id}/tv_credits?")
    fun getTVCredits(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<*>

    @GET("person/{person_id}/combined_credits?")
    fun getCombinedCredits(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<*>

    @GET("person/{person_id}/external_ids?")
    fun getExternalIDs(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<*>

    @GET("person/{person_id}/images?")
    fun getImages(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String
    ): Observable<*>

    @GET("person/{person_id}/tagged_images?")
    fun getTaggedImages(
            @Path("person_id") id: Int,
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Observable<*>

    // getTranslations

    @GET("person/latest?")
    fun personLatest(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<*>

    @GET("person/popular?")
    fun getPopular(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Observable<PersonsResponse>

    //endregion

    //region ReviewsService

    @GET("review/{review_id}?")
    fun reviewDetails(
            @Path("review_id") id: String,
            @Query("api_key") apiKey: String
    ): Observable<Review>

    //endregion

    //region SearchService

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

    //endregion

    //region TrendingService

    // getTrending

    //endregion

    //region TvEpisodeGroupsService

    // getDetails

    //endregion

    //region TvEpisodesService

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

    //region TvSeasonsService

    // getDetails

    // getChanges

    // getAccountStates

    // getCredits

    // getExternalIDs

    // getImages

    // trailers

    //endregion

    //region TvService

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