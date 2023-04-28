Movies
=

[![check-pr-badge](https://github.com/michaelbel/movies/actions/workflows/check_pr.yml/badge.svg?branch=develop)](https://github.com/michaelbel/movies/actions/workflows/check_pr.yml)
[![paypal-badge](https://img.shields.io/badge/Donate-Paypal-FF5252.svg)](https://paypal.me/michaelbel)
[![last-commit-badge](https://img.shields.io/github/last-commit/michaelbel/moviemade?color=FF5252)](https://github.com/michaelbel/moviemade/commits)

Movies - easy way to discover popular movies. This is a simple TMDb client for Android

<div style="dispaly:flex">
    <img src="config/screenshots/screen1.png" width="32%">
    <img src="config/screenshots/screen2.png" width="32%">
    <img src="config/screenshots/screen3.png" width="32%">
</div>

## Build

Take a look at <b>`local.properties`</b> and fill it with [your own](https://developers.themoviedb.org/3/getting-started/introduction) <b>tmdb_api_key</b> like this:
```gradle
TMDB_API_KEY=your_own_tmdb_api_key
```

## Download

[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" alt="" height="80">](https://play.google.com/store/apps/details?id=org.michaelbel.moviemade)
[<img src="config/screenshots/direct-apk.png" alt="" height="80">](https://github.com/michaelbel/movies/releases/download/1.4.6/Movies-v1.4.6.1196.-release.apk)

## Technologies

- [x] [Modularization](https://d.android.com/topic/modularization)
- [x] MVVM
- [x] [Clean Architecture](https://d.android.com/topic/architecture)
- [x] [TMDB API](https://developers.themoviedb.org/3/getting-started)
- [x] [KTS Gradle Files](https://d.android.com/studio/build/migrate-to-kts)
- [x] [Kotlin Symbol Processing API](https://d.android.com/studio/build/migrate-to-ksp) 1.8.20-1.0.11
- [x] [Gradle Plugin](https://d.android.com/studio/releases/gradle-plugin) 8.0.0
- [x] Gradle Version Catalog
- [x] MinSDK 21
- [x] TargetSDK 33
- [x] CompileSDK 33
- [x] [Material3](https://m3.material.io)
- [x] [Dark Theme](https://d.android.com/develop/ui/views/theming/darktheme)
- [x] [Dynamic Colors](https://d.android.com/develop/ui/views/theming/dynamic-colors)
- [x] [Themed App Icon](https://d.android.com/develop/ui/views/launch/icon_design_adaptive)
- [x] 100% [Kotlin](https://d.android.com/kotlin) 1.8.20
- [x] 100% [Jetpack Compose](https://d.android.com/jetpack/compose) 1.4.6
- [x] [Accompanist](https://github.com/google/accompanist) 0.30.1
- [x] [Compose PreviewParameterProvider](https://d.android.com/jetpack/compose/tooling#previewparameter)
- [x] [Downloadable Fonts](https://d.android.com/develop/ui/views/text-and-emoji/downloadable-fonts)
- [x] [KotlinX Coroutines](https://github.com/Kotlin/kotlinx.coroutines) 1.6.4
- [x] [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) 1.5.0
- [x] [Appcompat](https://d.android.com/jetpack/androidx/releases/appcompat) 1.6.0-rc01
- [x] [Dagger Hilt](https://github.com/google/dagger) 2.45
- [x] [ViewModel](https://d.android.com/topic/libraries/architecture/viewmodel)
- [x] [Lifecycle](https://d.android.com/topic/libraries/architecture/lifecycle) 2.6.0-alpha03
- [x] [Room](https://d.android.com/training/data-storage/room) 2.5.1
- [x] [WorkManager](https://d.android.com/topic/libraries/architecture/workmanager) 2.8.1
- [x] [DataStore](https://d.android.com/datastore) 1.0.0
- [x] [Startup](https://d.android.com/jetpack/androidx/releases/startup) 1.1.1
- [x] [Navigation](https://d.android.com/guide/navigation) 2.5.3
- [x] [Paging](https://d.android.com/topic/libraries/architecture/paging/v3-overview) (RemoteMediator & PagingSource) 1.0.0-alpha18
- [x] [ConstraintLayout](https://d.android.com/develop/ui/views/layout/constraint-layout)
- [x] [Browser](https://d.android.com/jetpack/androidx/releases/browser) 1.5.0
- [x] [OkHttp](https://github.com/square/okhttp) 4.11.0
- [x] [Retrofit](https://github.com/square/retrofit) 2.9.0
- [x] [Retrofit Kotlinx Converter Serialization](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter) 0.8.0
- [x] [Chucker](https://github.com/ChuckerTeam/chucker) 3.5.2
- [x] [Coil](https://github.com/coil-kt/coil) 2.3.0
- [x] [Timber](https://github.com/JakeWharton/timber) 5.0.1
- [x] [Firebase Crashlytics](https://firebase.google.com/products/crashlytics) 18.3.6
- [x] [Firebase App Distribution](https://firebase.google.com/products/app-distribution) 3.2.0
- [x] [Firebase Remote Config](https://firebase.google.com/products/remote-config) 21.3.0
- [x] [Google Analytics for Firebase](https://firebase.google.com/products/analytics) 21.2.2
- [x] [In-App Reviews](https://d.android.com/guide/playcore/in-app-review)
- [x] [App Shortcuts](https://d.android.com/develop/ui/views/launch/shortcuts)
- [x] [Dependabot](https://github.com/dependabot)
- [x] [Github Actions](https://github.com/michaelbel/movies/tree/develop/.github/workflows) CI/CD
- [x] [Github Releases](https://github.com/michaelbel/movies/releases)
- [x] [Lint](https://d.android.com/studio/write/lint)
- [x] [Detekt](https://github.com/detekt/detekt) 1.22.0
- [x] [Spotless](https://github.com/diffplug/spotless) 6.18.0
- [x] [Distribute App via Telegram Bot](https://github.com/appleboy/telegram-action)
- [x] [Non-Transitive R classes](https://d.android.com/studio/build/optimize-your-build#use-non-transitive-r-classes)
- [x] [SplashScreen API](https://d.android.com/develop/ui/views/launch/splash-screen) 1.0.1
- [x] [Per-App Language Preferences](https://d.android.com/guide/topics/resources/app-languages)
- [x] [Settings Panel](https://d.android.com/reference/android/provider/Settings.Panel)
- [x] [Benchmark](https://d.android.com/topic/performance/benchmarking/benchmarking-overview)
- [x] [Support Localization](https://d.android.com/guide/topics/resources/localization)
- [ ] [Deep Links](https://d.android.com/training/app-links/deep-linking)
- [ ] [Unit Tests](https://d.android.com/training/testing/local-tests)
- [ ] [UI Tests](https://d.android.com/training/testing/instrumented-tests/ui-tests)
- [ ] [In-App Updates](https://d.android.com/guide/playcore/in-app-updates)
- [ ] [Google Admob](https://developers.google.com/admob)
- [ ] [KtLint](https://github.com/pinterest/ktlint)
- [ ] [Support Display Cutouts](https://d.android.com/develop/ui/views/layout/display-cutout)
- [ ] [Baseline Profiles](https://d.android.com/topic/performance/baselineprofiles/overview)
- [ ] [Tablet and large screen support](https://d.android.com/about/versions/13/features/large-screens)
- [ ] OAuth
- [ ] [Animations](https://d.android.com/develop/ui/views/animations)
- [ ] Landscape Orientation
- [ ] [Support layout mirroring](https://d.android.com/training/basics/supporting-devices/languages#SupportLayoutMirroring)
- [ ] Upload Bundle to Google Play Console
- [ ] [ExoPlayer](https://d.android.com/guide/topics/media/exoplayer)

## Issues
If you find any problems or would like to suggest a feature, please feel free to file an [issue](https://github.com/michaelbel/moviemade/issues).

## License
<a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">
  <img alt="Apache License 2.0" src="config/screenshots/apache.png" height="110"/>
</a>

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
