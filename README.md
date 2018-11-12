[github-url]:        https://github.com/michaelbel/moviemade
[paypal-url]:        https://paypal.me/michaelbel
[licence-url]:       http://www.apache.org/licenses/LICENSE-2.0
[googleplay-url]:    https://play.google.com/store/apps/details?id=org.michaelbel.moviemade
[config-properties]: https://github.com/michaelbel/Moviemade/blob/master/app/src/main/assets/config.properties
[tmdb-introduction]: https://developers.themoviedb.org/3/getting-started/introduction

[launcher-path]: ../master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png

[platform-badge]:   https://img.shields.io/badge/Platform-Android-F3745F.svg
[paypal-badge]:     https://img.shields.io/badge/Donate-Paypal-F3745F.svg
[license-badge]:    https://img.shields.io/badge/License-Apache_v2.0-F3745F.svg
[googleplay-badge]: https://img.shields.io/badge/Google_Play-Demo-F3745F.svg
[minsdk-badge]:     https://img.shields.io/badge/minSdkVersion-21-F3745F.svg

<!------------------------------------------------------------------------------------------------------------------------------------>

<p align="center">
  <img src="../master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png"/>
</p>

# Moviemade
[![Platform][platform-badge]][github-url]
[![MinSdk][minsdk-badge]][github-url]
[![License][license-badge]][licence-url]
[![Paypal][paypal-badge]][paypal-url]
[![GooglePlay][googleplay-badge]][googleplay-url]

Moviemade - quick and easy way to discover popular movies.

## Build
Take a look at <b>`local.properties`</b> and fill it with [your own][tmdb-introduction] <b>tmdb_api_key</b>.

## Screenshots
| Explore | Search | Movie | Trailers |
|:-:|:-:|:-:|:-:|
| ![Explore](/screenshots/explore.png?raw=true) | ![Search](/screenshots/search.png?raw=true) | ![Movie](/screenshots/movie.png?raw=true) | ![Trailers](/screenshots/trailers.png?raw=true) |

## Demo video
https://www.youtube.com/watch?v=H5auyhQU9fU

## Open-source libraries
* [**Moxy**](https://github.com/Arello-Mobile/Moxy) MVP-architecture
* [**Retrofit**](https://github.com/square/retrofit) for REST API communication
* [**RxJava2**](https://github.com/ReactiveX/RxJava) for Retrofit & background threads
* [**ButterKnife**](https://github.com/JakeWharton/butterknife) for view binding
* [**Gson**](https://github.com/google/gson) to convert Java Objects into JSON and back
* [**GestureViews**](https://github.com/alexvasilkov/GestureViews) for ImageView with gestures control
* [**Glide**](https://github.com/bumptech/glide) for image loading
* [**Dagger 2**](https://github.com/google/dagger) for dependency injection
* [**AndroidYouTubePlayer**](https://github.com/PierfrancescoSoffritti/android-youtube-player) for playing videos
* [**Room**](https://developer.android.com/topic/libraries/architecture/room.html) database
* [**Espresso**](https://google.github.io/android-testing-support-library/docs/espresso/) for UI tests

## License
    Copyright 2017 Michael Bely

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
