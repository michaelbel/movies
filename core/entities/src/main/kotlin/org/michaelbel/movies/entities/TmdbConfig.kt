package org.michaelbel.movies.entities

import java.util.Locale
import java.util.Locale.ENGLISH

val tmdbApiKey: String
    get() = BuildConfig.TMDB_API_KEY

val language: String
    get() = Locale.getDefault().language

const val TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/"

const val TMDB_AUTH_URL = "https://themoviedb.org/authenticate/%s?redirect_to=%s"

const val TMDB_MOVIE = "https://themoviedb.org/movie/%d"
const val TMDB_PERSON = "https://themoviedb.org/person/%d"
const val TMDB_MOVIE_POSTERS = "https://themoviedb.org/movie/%d/images/posters"
const val TMDB_MOVIE_BACKDROPS = "https://themoviedb.org/movie/%d/images/backdrops"

const val TMDB_IMAGE = "https://image.tmdb.org/t/p/%s/%s"
const val TMDB_LOGO = "https://themoviedb.org/assets/1/v4/logos/208x226-stacked-green-9484383bd9853615c113f020def5cbe27f6d08a84ff834f41371f223ebad4a3c.png"

const val TMDB_TERMS_OF_USE = "https://themoviedb.org/documentation/website/terms-of-use"
const val TMDB_PRIVACY_POLICY = "https://themoviedb.org/privacy-policy"

const val TMDB_REGISTER = "https://themoviedb.org/account/signup"
const val TMDB_RESET_PASSWORD = "https://themoviedb.org/account/reset-password"

const val REDIRECT_URL = "moviemade://main"

const val CONTENT_TYPE = "application/json;charset=utf-8"
const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

const val GRAVATAR_URL = "https://gravatar.com/avatar/%s"
const val GRAVATAR_URL_JPG = "https://gravatar.com/avatar/%s.jpg"

const val YOUTUBE_IMAGE = "https://img.youtube.com/vi/%s/0.jpg"

const val IMDB_MOVIE = "https://imdb.com/title/%s"
const val IMDB_PERSON = "https://imdb.com/name/%s"

fun image(path: String, size: String = "original") = String.format(ENGLISH, TMDB_IMAGE, size, path)