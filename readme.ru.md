[<img align="left" src="config/images/play_store_app_icon_512x512_rounded.png" width="150" height="150">]()
<div align="right">
    <a href="https://github.com/michaelbel/movies/blob/develop/readme.md">en</a>
    <a href="https://github.com/michaelbel/movies/blob/develop/readme.ru.md"><b>ru</b></a>
</div>

<br/>
<br/>
<br/>
<br/>

Movies
=

[![](https://img.shields.io/badge/Android-000000.svg?style=for-the-badge&logo=Android)](https://github.com/michaelbel/movies)
[![](https://img.shields.io/badge/macOS-000000.svg?style=for-the-badge&logo=macos)](https://github.com/michaelbel/movies)
<!-- [![](https://img.shields.io/badge/Automotive-000000.svg?style=for-the-badge&logo=android-auto)](https://github.com/michaelbel/movies)
[![](https://img.shields.io/badge/WearOS-000000.svg?style=for-the-badge&logo=wearOS)](https://github.com/michaelbel/movies)
[![](https://img.shields.io/badge/iOS-000000.svg?style=for-the-badge&logo=apple)](https://github.com/michaelbel/movies)
[![](https://img.shields.io/badge/Windows-000000.svg?style=for-the-badge&logo=windows)](https://github.com/michaelbel/movies)
[![](https://img.shields.io/badge/Linux-000000.svg?style=for-the-badge&logo=linux)](https://github.com/michaelbel/movies)
[![](https://img.shields.io/badge/Web-000000.svg?style=for-the-badge&logo=google-chrome)](https://github.com/michaelbel/movies) -->

[![Check PR](https://img.shields.io/github/actions/workflow/status/michaelbel/movies/check_pr.yml?style=for-the-badge&logo=github&label=Check%20PR&labelColor=3F464F
)](https://github.com/michaelbel/movies/actions/workflows/check_pr.yml)
[![Last Commit](https://img.shields.io/github/last-commit/michaelbel/movies?style=for-the-badge&logo=github&labelColor=3F464F
)](https://github.com/michaelbel/movies/commits)

Movies - простой способ найти популярные фильмы. Это легковесный TMDb-клиент.

Цель этого проекта - реализовать приложение с единообразным пользовательским интерфейсом для Android-смартфонов, Android-планшетов, Android Auto, Android Wear, Android TV, iOS, Desktop (MacOS, Windows, Linux) и Web. Используя Material3, Compose, Kotlin и Multiplatform.

Проект находится в активной разработке. Приложение для Android уже доступно.


## Скриншоты

**Android**
<p align="center">
    <img src="config/images/android/android1.png" width="24%">
    <img src="config/images/android/android2.png" width="24%">
    <img src="config/images/android/android3.png" width="24%">
    <img src="config/images/android/android4.png" width="24%">
    <img src="config/images/android/android7.gif" width="24%">
    <img src="config/images/android/android8.gif" width="24%">
    <img src="config/images/android/android9.gif" width="24%">
    <img src="config/images/android/android10.gif" width="24%">
    <img src="config/images/android/android5.png" width="49%">
    <img src="config/images/android/android6.png" width="49%">
</p>

**macOS**
<p align="center">
    <img src="config/images/desktop/macos1.png" width="49%">
    <img src="config/images/desktop/macos2.png" width="49%">
    <img src="config/images/desktop/macos3.png" width="49%">
    <img src="config/images/desktop/macos4.png" width="49%">
</p>


## Требования
Перейди в <b>`local.properties`</b> и укажи [свой собственный](https://developers.themoviedb.org/3/getting-started/introduction) <b>TMDB API key</b> как здесь:
```gradle
TMDB_API_KEY=your_own_tmdb_api_key
 ```
Приложение можно использовать без ключа API, но его функциональность будет сильно ограничена.


## Сборка
- Клонируй репозиторий используя [последнюю версию](https://d.android.com/studio) Android Studio
- Запусти приложение на Android девайсе или эмуляторе
    - 🤖 **Android** с Google Mobile Services:
      ```gradle
      ./gradlew :androidApp:installGmsDebug
      ```
    - 🤖 **Android** с Huawei Mobile Services:
      ```gradle
      ./gradlew :androidApp:installHmsDebug
      ```
    - 🤖 **Android** Free and Open Source Software:
      ```gradle
      ./gradlew :androidApp:installFossDebug
      ```
- Запусти приложение на компе с macOS
    ```gradle
      ./gradlew :desktopApp:run
    ```


## Загрузить
[![](https://PlayBadges.pavi2410.me/badge/downloads?id=org.michaelbel.moviemade)](https://play.google.com/store/apps/details?id=org.michaelbel.moviemade)
[![](https://img.shields.io/github/downloads/michaelbel/movies/total?logo=github&label=Downloads&labelColor=212133&color=34CC2C
)](https://github.com/michaelbel/movies/releases)

[<img src="config/images/badges/badge-googleplay.svg" alt="" height="80">](https://play.google.com/store/apps/details?id=org.michaelbel.moviemade)
[<img src="config/images/badges/badge-appgallery.svg" alt="" height="80">](https://appgallery.cloud.huawei.com/ag/n/app/C109677247)
[<img src="config/images/badges/badge-github.svg" alt="" height="80">](https://github.com/michaelbel/movies/releases/download/2.0.0/Movies-v2.0.0.1699.-gms-release.apk)
[<img src="config/images/badges/badge-obtainium.svg" alt="" height="80">](https://apps.obtainium.imranr.dev/redirect?r=obtainium://add/https://github.com/michaelbel/movies)


## Технологии
[![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com)
[![Kotlin](https://img.shields.io/badge/kotlin-7f52ff.svg?style=for-the-badge&logo=kotlin&logoColor=white)](https://d.android.com/kotlin)
[![Ktor](https://img.shields.io/badge/Ktor-7f52ff?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMjggMTI4Ij4KICA8ZGVmcyBmaWxsPSIjN2Y1MmZmIj4KICAgIDxsaW5lYXJHcmFkaWVudCBpZD0iYSIgeDE9IjI0Ljk0MSIgeDI9IjUyLjMwNiIgeTE9IjI0Ljk0MSIgeTI9IjUyLjMwNiIgZ3JhZGllbnRUcmFuc2Zvcm09InJvdGF0ZSguMTA0KSBzY2FsZSgxLjIxOTA1KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiIGZpbGw9IiM3ZjUyZmYiPgogICAgICA8c3RvcCBvZmZzZXQ9Ii4yOTYiIHN0b3AtY29sb3I9IiNmZmZmZmYiIGZpbGw9IiM3ZjUyZmYiLz4KICAgICAgPHN0b3Agb2Zmc2V0PSIuNjk0IiBzdG9wLWNvbG9yPSIjZmZmZmZmIiBmaWxsPSIjN2Y1MmZmIi8+CiAgICAgIDxzdG9wIG9mZnNldD0iMSIgc3RvcC1jb2xvcj0iI2ZmZmZmZiIgZmlsbD0iIzdmNTJmZiIvPgogICAgPC9saW5lYXJHcmFkaWVudD4KICAgIDxsaW5lYXJHcmFkaWVudCBpZD0iYiIgeDE9IjUzLjE1MSIgeDI9Ijc5LjAyMyIgeTE9IjUzLjE1MSIgeTI9Ijc5LjAyMyIgZ3JhZGllbnRUcmFuc2Zvcm09InJvdGF0ZSguMTA0KSBzY2FsZSgxLjIxOTA1KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiIGZpbGw9IiM3ZjUyZmYiPgogICAgICA8c3RvcCBvZmZzZXQ9Ii4xMDgiIHN0b3AtY29sb3I9IiNmZmZmZmYiIGZpbGw9IiM3ZjUyZmYiLz4KICAgICAgPHN0b3Agb2Zmc2V0PSIuMTczIiBzdG9wLWNvbG9yPSIjZmZmZmZmIiBmaWxsPSIjN2Y1MmZmIi8+CiAgICAgIDxzdG9wIG9mZnNldD0iLjQ5MiIgc3RvcC1jb2xvcj0iI2ZmZmZmZiIgZmlsbD0iIzdmNTJmZiIvPgogICAgICA8c3RvcCBvZmZzZXQ9Ii43MTYiIHN0b3AtY29sb3I9IiNmZmZmZmYiIGZpbGw9IiM3ZjUyZmYiLz4KICAgICAgPHN0b3Agb2Zmc2V0PSIuODIzIiBzdG9wLWNvbG9yPSIjZmZmZmZmIiBmaWxsPSIjN2Y1MmZmIi8+CiAgICA8L2xpbmVhckdyYWRpZW50PgogIDwvZGVmcz4KICA8cGF0aCBmaWxsPSJ1cmwoI2EpIiBkPSJNODAuNDU3IDQ3LjU0MyA0Ny41NDMgMTQuNjI5IDE0LjYyOSA0Ny41NDNsMzIuOTE0IDMyLjkxNFptMCAwIi8+CiAgPHBhdGggZmlsbD0idXJsKCNiKSIgZD0ibTQ3LjU0MyA4MC40NTcgMzIuOTE0IDMyLjkxNCAzMi45MTQtMzIuOTE0LTMyLjkxNC0zMi45MTRabTAgMCIvPgogIDxwYXRoIGQ9Ik04MC40NTcgNDcuNTQzSDQ3LjU0M3YzMi45MTRoMzIuOTE0Wm0wIDAiIGZpbGw9IiM3ZjUyZmYiLz4KPC9zdmc+&logoColor=white)](https://ktor.io)
[![Multiplatform](https://img.shields.io/badge/Multiplatform-AD4DB1?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNzUiIGhlaWdodD0iNzUiIHZpZXdCb3g9IjAgMCA3NSA3NSIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGcgY2xpcC1wYXRoPSJ1cmwoI2NsaXAwXzM0XzQ2KSI+CjxwYXRoIGQ9Ik03NSA3NUgwVjBINzVMMzcuMzI0MiAzNy41TDc1IDc1WiIgZmlsbD0iI0U2RTZFNiIvPgo8cGF0aCBkPSJNNDMuMDA3OCA1NC40MzM2SDEwLjU0NjlWMzguMTQ0NUMxMC41NDY5IDI5LjEyMTEgMTcuODEyNSAyMS44NTU1IDI2Ljc3NzMgMjEuODU1NUMzNS43NDIyIDIxLjg1NTUgNDMuMDA3OCAyOS4xNzk3IDQzLjAwNzggMzguMTQ0NVY1NC40MzM2WiIgZmlsbD0iIzNEREI4NCIvPgo8cGF0aCBkPSJNNDUuMjM0NCA1NC42NjhDMzYuMjY5NSA1NC42NjggMjkuMDAzOSA0Ny4zNDM4IDI5LjAwMzkgMzguMzc4OUMyOS4wMDM5IDI5LjM1NTUgMzYuMjY5NSAyMi4wODk4IDQ1LjIzNDQgMjIuMDg5OEM1NC4xOTkyIDIyLjA4OTggNjEuNDY0OCAyOS40MTQxIDYxLjQ2NDggMzguMzc4OUM2MS40MDYyIDQ3LjM0MzggNTQuMTQwNiA1NC42NjggNDUuMjM0NCA1NC42NjhaIiBmaWxsPSIjNEQ0RDREIi8+CjxwYXRoIGQ9Ik01NC45NjA5IDkuNDkyMTlDNDkuMTYwMiA5LjQ5MjE5IDQ0LjQxNDEgMTMuODg2NyA0NC40MTQxIDE5LjI3NzNDNDQuNDE0MSAxOS4zOTQ1IDQ0LjQxNDEgMTkuNTExNyA0NC40MTQxIDE5LjYyODlDNDQuNTMxMiAxOS42Mjg5IDQ0LjY0ODQgMTkuNjI4OSA0NC43NjU2IDE5LjYyODlDNTAuNTY2NCAxOS42Mjg5IDU1LjMxMjUgMTUuMjM0NCA1NS4zMTI1IDkuODQzNzVDNTUuMzEyNSA5LjcyNjU2IDU1LjMxMjUgOS42MDkzOCA1NS4zMTI1IDkuNDkyMTlDNTUuMTk1MyA5LjQ5MjE5IDU1LjA3ODEgOS40OTIxOSA1NC45NjA5IDkuNDkyMTlaIiBmaWxsPSIjNEQ0RDREIi8+CjwvZz4KPGRlZnM+CjxjbGlwUGF0aCBpZD0iY2xpcDBfMzRfNDYiPgo8cmVjdCB3aWR0aD0iNzUiIGhlaWdodD0iNzUiIGZpbGw9IndoaXRlIi8+CjwvY2xpcFBhdGg+CjwvZGVmcz4KPC9zdmc+Cg==&logoColor=white)](https://www.jetbrains.com/kotlin-multiplatform)
[![Compose](https://img.shields.io/badge/compose-blue.svg?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://d.android.com/jetpack/compose)
[![Material3](https://img.shields.io/badge/Material3-004A76?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSI4MDBweCIgaGVpZ2h0PSI4MDBweCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICA8dGl0bGU+bWF0ZXJpYWxfZGVzaWduPC90aXRsZT4KICA8cmVjdCB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIGZpbGw9Im5vbmUiLz4KICA8cGF0aCBkPSJNMjEsMTJhOSw5LDAsMCwwLTItNS42MlYxNy42M0E4Ljc4LDguNzgsMCwwLDAsMjEsMTJtLTMuMzcsN0g2LjM4YTkuNSw5LjUsMCwwLDAsMi42NywxLjQxQTguOTEsOC45MSwwLDAsMCwxMiwyMSw4Ljg2LDguODYsMCwwLDAsMTUsMjAuNDEsOS43Miw5LjcyLDAsMCwwLDE3LjYzLDE5TTExLDE3LDcsOXY4aDRtNi04LTQsOGg0VjltLTUsNS41M0wxNS43NSw3SDguMjVMMTIsMTQuNTNNMTcuNjMsNUE4LjkxLDguOTEsMCwwLDAsNi4zOCw1SDE3LjYzTTUsMTcuNjNWNi4zOEE5LDksMCwwLDAsMywxMmE4Ljc4LDguNzgsMCwwLDAsMiw1LjYzTTIzLDEyYTEwLjU3LDEwLjU3LDAsMCwxLTMuMjIsNy43OEExMC41NywxMC41NywwLDAsMSwxMiwyM2ExMC41OSwxMC41OSwwLDAsMS03Ljc4LTMuMjJBMTAuNTcsMTAuNTcsMCwwLDEsMSwxMiwxMC41OSwxMC41OSwwLDAsMSw0LjIyLDQuMjIsMTAuNTksMTAuNTksMCwwLDEsMTIsMWExMC41NywxMC41NywwLDAsMSw3Ljc4LDMuMjJBMTAuNTksMTAuNTksMCwwLDEsMjMsMTJaIiBmaWxsPSIjZmZmZmZmIi8+Cjwvc3ZnPg==&logoColor=white)](https://m3.material.io)
[![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)](https://d.android.com/studio/releases/gradle-plugin)
[![Android](https://img.shields.io/badge/Android%20SDK-50AE55?style=for-the-badge&logo=android&logoColor=F6F6F6)](https://d.android.com)
[![Room](https://img.shields.io/badge/Room_Multiplatform-50AE55?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTEyLDNDNy41OCwzIDQsNC43OSA0LDdDNCw5LjIxIDcuNTgsMTEgMTIsMTFDMTYuNDIsMTEgMjAsOS4yMSAyMCw3QzIwLDQuNzkgMTYuNDIsMyAxMiwzTTQsOVYxMkM0LDE0LjIxIDcuNTgsMTYgMTIsMTZDMTYuNDIsMTYgMjAsMTQuMjEgMjAsMTJWOUMyMCwxMS4yMSAxNi40MiwxMyAxMiwxM0M3LjU4LDEzIDQsMTEuMjEgNCw5TTQsMTRWMTdDNCwxOS4yMSA3LjU4LDIxIDEyLDIxQzE2LjQyLDIxIDIwLDE5LjIxIDIwLDE3VjE0QzIwLDE2LjIxIDE2LjQyLDE4IDEyLDE4QzcuNTgsMTggNCwxNi4yMSA0LDE0WiIgZmlsbD0iI2ZmZmZmZiIvPjwvc3ZnPg==&logoColor=white)](https://d.android.com/kotlin/multiplatform/room)
[![Firebase](https://img.shields.io/badge/Firebase-E5B82C?style=for-the-badge&logo=Firebase&logoColor=white)](https://firebase.google.com)
[![Coil](https://img.shields.io/badge/Coil-3B6BB4?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgaWQ9IkxheWVyXzEiIHg9IjBweCIgeT0iMHB4IiB3aWR0aD0iMjAwcHgiIGhlaWdodD0iMjAwcHgiIHZpZXdCb3g9IjMzMi41IDIwMCAxNDAgMTQwIiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPGc+Cgk8Zz4KCQk8cGF0aCBmaWxsPSIjZmZmZmZmIiBkPSJNMzkyLjcyOCwzMDMuMzgybC0zMi4zODQtMjQuMjQxbC0zLjI0MywyLjQzMWMtNS41MjMsNC4xMzEtNS41MjMsMTAuODM3LDAsMTQuOTdsMzUuNjI3LDI2LjY3MiAgICBjNS41MjUsNC4xMzIsMTQuNDc4LDQuMTMyLDIwLjAwMiwwbDM1LjYyNy0yNi42NzJjNS41MjYtNC4xMzMsNS41MjYtMTAuODM5LDAtMTQuOTdsLTMuMjQ2LTIuNDMxbC0zMi4zODEsMjQuMjQxICAgIEM0MDcuMjA2LDMwNy41MTUsMzk4LjI1MywzMDcuNTE1LDM5Mi43MjgsMzAzLjM4MnoiLz4KCQk8cGF0aCBmaWxsPSIjZmZmZmZmIiBkPSJNNDQ4LjM1NywyNjEuNzQybC0zLjI0Ni0yLjQzbC0zMi4zODEsMjQuMjM4Yy01LjUyNSw0LjEzMy0xNC40NzgsNC4xMzMtMjAuMDAyLDBsLTMyLjM4NC0yNC4yMzggICAgbC0zLjI0MywyLjQzYy01LjUyMyw0LjEzMS01LjUyMywxMC44MzUsMCwxNC45NzFsMy4yNDMsMi40MjhsMzIuMzg0LDI0LjI0MWM1LjUyNSw0LjEzMywxNC40NzgsNC4xMzMsMjAuMDAyLDBsMzIuMzgxLTI0LjI0MSAgICBsMy4yNDYtMi40MjhDNDUzLjg4NCwyNzIuNTc3LDQ1My44ODQsMjY1Ljg3Myw0NDguMzU3LDI2MS43NDJ6Ii8+CgkJPHBhdGggZmlsbD0iI2ZmZmZmZiIgZD0iTTQxMi43MywyODMuNTUxbDMyLjM4MS0yNC4yMzhsMy4yNDYtMi40MzJjNS41MjYtNC4xMzEsNS41MjYtMTAuODM3LDAtMTQuOTcxbC0yLjk4My0yLjIzMiAgICBsLTM3LjMzOCwyNy44NDNjLTIuOTMyLDIuMTk2LTcuNjg1LDIuMTk2LTEwLjYxNSwwbC0xOC45MTEtMTQuMTVjLTIuOTMxLTIuMTk1LTIuOTMxLTUuNzUyLDAtNy45NDhsMzcuOTEtMjcuNDE5bC0zLjY4OS0yLjc2MSAgICBjLTUuNTI1LTQuMTM2LTE0LjQ3OC00LjEzNi0yMC4wMDIsMGwtMzUuNjI3LDI2LjY2OGMtNS41MjMsNC4xMzMtNS41MjMsMTAuODQsMCwxNC45NzFsMy4yNDMsMi40MzJsMzIuMzg0LDI0LjIzOCAgICBDMzk4LjI1MywyODcuNjg0LDQwNy4yMDYsMjg3LjY4NCw0MTIuNzMsMjgzLjU1MXoiLz4KCTwvZz4KPC9nPgo8L3N2Zz4=&logoColor=white)](https://github.com/coil-kt/coil)
[![Clean](https://img.shields.io/badge/Clean%20Architecture-4169E1.svg?style=for-the-badge&logo=ccleaner&logoColor=white)](https://d.android.com/topic/architecture)
[![Dependabot](https://img.shields.io/badge/Dependabot-0366D6?&style=for-the-badge&logo=dependabot&logoColor=white)](https://github.com/dependabot)
[![Github](https://img.shields.io/badge/Github%20Actions-3F464F?&style=for-the-badge&logo=github&logoColor=white)](https://github.com/michaelbel/movies/tree/develop/.github/workflows)
[![Codebeat](https://img.shields.io/badge/Codebeat-26A9E1?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNTAwIiBoZWlnaHQ9IjI1MDAiIHZpZXdCb3g9IjAgMCAyNTYgMjU2IiBwcmVzZXJ2ZUFzcGVjdFJhdGlvPSJ4TWlkWU1pZCI+PHBhdGggZD0iTTU3LjY5NyA4OS41NjFsMjcuOTIgNTguMjM4IDUxLjA0NS0xMTYuMjQ0IDU2LjMwNSAxMzguOTgzIDE3Ljk0My00MC4zNzJIMjU2VjEyOEMyNTYgNTcuMzEgMTk4LjY5IDAgMTI4IDBTMCA1Ny4zMSAwIDEyOHYyLjE2NmgzNi45N0w1Ny42OTYgODkuNTZ6TTE5Mi41OCAyMDMuMTc2TDEzNS44MTEgOTMuNDI4bC01MC41MDMgOTAuODc3LTI4LjA3NS02NC43MzUtMTMuNjkgMjYuOTE1SDEuMzE1QzEwLjI4NiAyMDguNDM1IDYzLjU3NSAyNTYgMTI4IDI1NmM2NC40MjUgMCAxMTcuNzktNDcuNjQyIDEyNi42ODUtMTA5LjU5M2gtMzUuMjY3bC0yNi44MzggNTYuNzY5eiIgZmlsbD0iI2ZmZmZmZiIvPjwvc3ZnPg==&logoColor=white)](https://codebeat.co/projects/github-com-michaelbel-movies-develop)
[![Tmdb](https://img.shields.io/badge/TMDB_API-073731?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9IiNmZmZmZmYiIHdpZHRoPSI4MDBweCIgaGVpZ2h0PSI4MDBweCIgdmlld0JveD0iMCAwIDMyIDMyIj4KICA8cGF0aCBkPSJNMjUuOTkgMjkuMTk4YzIuODA3IDAgNC43MDgtMS44OTYgNC43MDgtNC43MDh2LTE5Ljc4MWMwLTIuODA3LTEuOTAxLTQuNzA4LTQuNzA4LTQuNzA4aC0xOS45NzljLTIuODA3IDAtNC43MDggMS45MDEtNC43MDggNC43MDh2MjcuMjkybDIuNDExLTIuODAydi0yNC40OWMwLjAwNS0xLjI2NiAxLjAzMS0yLjI5MiAyLjI5Ny0yLjI5MmgxOS45NzRjMS4yNjYgMCAyLjI5MiAxLjAyNiAyLjI5MiAyLjI5MnYxOS43ODFjMCAxLjI2Ni0xLjAyNiAyLjI5Mi0yLjI5MiAyLjI5MmgtMTYuNzU1bC0yLjQxNyAyLjQxNy0wLjAxNi0wLjAxNnpNMTEuNzE0IDE1LjI4NmgtMi4yNnY3LjU5OWgyLjI2YzUuMDU3IDAgNS4wNTctNy41OTkgMC03LjU5OXpNMTEuNzE0IDIxLjM2NWgtMC43MzR2LTQuNTU3aDAuNzM0YzIuOTU4IDAgMi45NTggNC41NTcgMCA0LjU1N3pNMTEuMjc2IDEzLjg1NGgxLjUxNnYtNi4wODNoMS44OTF2LTEuNTA1aC01LjMwMnYxLjUwNWgxLjg5NnpNMTguNzUgOS41OTlsLTIuNjI1LTMuMzMzaC0wLjQ5djcuNzE0aDEuNTQydi00LjI0bDEuNTczIDIuMDQyIDEuNTc4LTIuMDQyLTAuMDEwIDQuMjRoMS41NDJ2LTcuNzE0aC0wLjQ3OXpNMjEuMzEzIDE5LjA4OWMwLjQ3NC0wLjMzMyAwLjY3Ny0wLjkyMiAwLjY5OC0xLjUgMC4wMzEtMS4zMzktMC44MDctMi4zMDctMi4xNTYtMi4zMDdoLTMuMDA1djcuNjA5aDMuMDA1YzEuMjQtMC4wMTAgMi4yNDUtMS4wMjEgMi4yNDUtMi4yNnYtMC4wMzZjMC0wLjYyLTAuMzA3LTEuMTcyLTAuNzgxLTEuNXpNMTguMzcgMTYuODAyaDEuMzU0YzAuNDMyIDAgMC42OTggMC4zMzkgMC42OTggMC43NjYgMC4wMzEgMC40MDYtMC4yODYgMC43Ni0wLjY5OCAwLjc2aC0xLjM1NHpNMTkuNzI0IDIxLjM3aC0xLjM1NHYtMS41MTZoMS4zN2MwLjQxMSAwIDAuNzQ1IDAuMzMzIDAuNzQ1IDAuNzQ1djAuMDE2YzAgMC40MTctMC4zMzMgMC43NTUtMC43NSAwLjc1NXoiIGZpbGw9IiNmZmZmZmYiLz4KPC9zdmc+&logoColor=white)](https://developers.themoviedb.org/3/getting-started)
[![InAppReview](https://img.shields.io/badge/IN--APP_REVIEW-34A853?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTMsMjAuNVYzLjVDMywyLjkxIDMuMzQsMi4zOSAzLjg0LDIuMTVMMTMuNjksMTJMMy44NCwyMS44NUMzLjM0LDIxLjYgMywyMS4wOSAzLDIwLjVNMTYuODEsMTUuMTJMNi4wNSwyMS4zNEwxNC41NCwxMi44NUwxNi44MSwxNS4xMk0yMC4xNiwxMC44MUMyMC41LDExLjA4IDIwLjc1LDExLjUgMjAuNzUsMTJDMjAuNzUsMTIuNSAyMC41MywxMi45IDIwLjE4LDEzLjE4TDE3Ljg5LDE0LjVMMTUuMzksMTJMMTcuODksOS41TDIwLjE2LDEwLjgxTTYuMDUsMi42NkwxNi44MSw4Ljg4TDE0LjU0LDExLjE1TDYuMDUsMi42NloiIGZpbGw9IiNmZmZmZmYiLz48L3N2Zz4=&logoColor=white)](https://d.android.com/guide/playcore/in-app-review)
[![InAppUpdate](https://img.shields.io/badge/IN--APP_UPDATE-34A853?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTMsMjAuNVYzLjVDMywyLjkxIDMuMzQsMi4zOSAzLjg0LDIuMTVMMTMuNjksMTJMMy44NCwyMS44NUMzLjM0LDIxLjYgMywyMS4wOSAzLDIwLjVNMTYuODEsMTUuMTJMNi4wNSwyMS4zNEwxNC41NCwxMi44NUwxNi44MSwxNS4xMk0yMC4xNiwxMC44MUMyMC41LDExLjA4IDIwLjc1LDExLjUgMjAuNzUsMTJDMjAuNzUsMTIuNSAyMC41MywxMi45IDIwLjE4LDEzLjE4TDE3Ljg5LDE0LjVMMTUuMzksMTJMMTcuODksOS41TDIwLjE2LDEwLjgxTTYuMDUsMi42NkwxNi44MSw4Ljg4TDE0LjU0LDExLjE1TDYuMDUsMi42NloiIGZpbGw9IiNmZmZmZmYiLz48L3N2Zz4=&logoColor=white)](https://d.android.com/guide/playcore/in-app-updates)

- [x] [Modularization](https://d.android.com/topic/modularization)
- [x] [MVVM](https://d.android.com/topic/architecture)
- [x] [KTS Gradle Files](https://d.android.com/studio/build/migrate-to-kts)
- [x] [Kotlin Symbol Processing API](https://d.android.com/studio/build/migrate-to-ksp)
- [x] [Gradle Version Catalog](https://d.android.com/build/migrate-to-catalogs)
- [x] [Build Variants](https://d.android.com/build/build-variants)
- [x] [Product Flavors](https://d.android.com/build/build-variants#product-flavors)
- [x] [Using buildSrc Directory](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)
- [x] MinSDK 23
- [x] TargetSDK 34
- [x] CompileSDK 34
- [x] [Dark Theme](https://d.android.com/develop/ui/views/theming/darktheme)
- [x] Amoled Theme
- [x] [Material You Dynamic Colors](https://d.android.com/develop/ui/views/theming/dynamic-colors)
- [x] [Themed App Icon](https://d.android.com/develop/ui/views/launch/icon_design_adaptive)
- [x] [Palette Colors API](https://d.android.com/develop/ui/views/graphics/palette-colors)
- [x] [Accompanist](https://github.com/google/accompanist)
- [x] [Compose PreviewParameterProvider](https://d.android.com/jetpack/compose/tooling#previewparameter)
- [x] [Downloadable Fonts](https://d.android.com/develop/ui/views/text-and-emoji/downloadable-fonts)
- [x] [KotlinX Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [x] [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [x] [Appcompat](https://d.android.com/jetpack/androidx/releases/appcompat)
- [x] [ViewModel](https://d.android.com/topic/libraries/architecture/viewmodel)
- [x] [Lifecycle](https://d.android.com/topic/libraries/architecture/lifecycle)
- [x] [WorkManager](https://d.android.com/topic/libraries/architecture/workmanager)
- [x] [DataStore](https://d.android.com/datastore)
- [x] [Startup](https://d.android.com/jetpack/androidx/releases/startup)
- [x] [Navigation](https://d.android.com/guide/navigation)
- [x] [Paging3](https://d.android.com/topic/libraries/architecture/paging/v3-overview)
- [x] [Browser](https://d.android.com/jetpack/androidx/releases/browser)
- [x] [OkHttp](https://github.com/square/okhttp)
- [x] [Chucker](https://github.com/ChuckerTeam/chucker)
- [x] [Flaker](https://github.com/rotbolt/flaker)
- [x] [Timber](https://github.com/JakeWharton/timber)
- [x] [App Shortcuts](https://d.android.com/develop/ui/views/launch/shortcuts)
- [x] [Github Releases](https://github.com/michaelbel/movies/releases)
- [x] [Lint](https://d.android.com/studio/write/lint)
- [x] [Detekt](https://github.com/detekt/detekt)
- [x] [Spotless](https://github.com/diffplug/spotless)
- [x] [Distribute App via Telegram Bot](https://github.com/appleboy/telegram-action)
- [x] [Non-Transitive R classes](https://d.android.com/studio/build/optimize-your-build#use-non-transitive-r-classes)
- [x] [SplashScreen API](https://d.android.com/develop/ui/views/launch/splash-screen)
- [x] [Per-App Language Preferences](https://d.android.com/guide/topics/resources/app-languages)
- [x] [Settings Panel](https://d.android.com/reference/android/provider/Settings.Panel)
- [x] [Benchmark](https://d.android.com/topic/performance/benchmarking/benchmarking-overview)
- [x] [Support Localization](https://d.android.com/guide/topics/resources/localization)
- [x] [Notification Runtime Permission](https://d.android.com/develop/ui/views/notifications/notification-permission)
- [x] [Changing Launcher App Icon](https://d.android.com/guide/topics/manifest/activity-alias-element)
- [x] [Predictive Back Gesture](https://d.android.com/guide/navigation/custom-back/predictive-back-gesture)
- [x] [Codacy Static Code Analysis](https://app.codacy.com/gh/michaelbel/movies/dashboard)
- [x] [Display Content Edge-to-Edge](https://d.android.com/develop/ui/views/layout/edge-to-edge)
- [x] [Support Landscape Orientation](https://d.android.com/guide/topics/large-screens/support-different-screen-sizes)
- [x] [Support Display Cutouts](https://d.android.com/jetpack/compose/system/cutouts)
- [x] [Voice Input](https://d.android.com/training/wearables/user-input/voice)
- [x] [User Interactions](https://d.android.com/jetpack/compose/text/user-interactions)
- [x] [Glance AppWidget](https://d.android.com/jetpack/compose/glance)
- [x] [Tile Quick Settings](https://d.android.com/develop/ui/views/quicksettings-tiles)
- [x] [Grammatical Gender](https://d.android.com/about/versions/14/features/grammatical-inflection)
- [x] [Biometric Authentication Dialog](https://d.android.com/training/sign-in/biometric-auth)
- [x] [LeakCanary](https://github.com/square/leakcanary)
- [x] [ConstraintLayout Multiplatform](https://github.com/Lavmee/constraintlayout-compose-multiplatform)
- [x] [Koin](https://github.com/InsertKoinIO/koin)
- [x] [Screenshot Detection](https://d.android.com/about/versions/14/features/screenshot-detection)
- [x] [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)
- [x] [ConstraintLayout](https://d.android.com/develop/ui/views/layout/constraint-layout) removed in [44723cb](https://github.com/michaelbel/movies/commit/44723cbbafdad89bef6043f99cbd0fbab1ecf19a)
- [x] [Dagger Hilt](https://github.com/google/dagger) removed in [#274](https://github.com/michaelbel/movies/pull/274)
- [x] [Retrofit](https://github.com/square/retrofit) removed in [#275](https://github.com/michaelbel/movies/pull/275)


## Модули
```mermaid
graph TD;
    feature-->androidApp
    feature-->desktopApp

    account-->feature
    auth-->feature
    details-->feature
    feed-->feature
    gallery-->feature
    search-->feature
    settings-->feature
    debug-->feature

    account-impl-->account
    auth-impl-->auth
    details-impl-->details
    feed-impl-->feed
    gallery-impl-->gallery
    search-impl-->search
    settings-impl-->settings
    debug-impl-->debug

    core-->account-impl
    core-->auth-impl
    core-->details-impl
    core-->feed-impl
    core-->gallery-impl
    core-->search-impl
    core-->settings-impl
    core-->debug-impl

    navigation-->core
    ui-->core
    common-->core
    interactor-->core
    notifications-->core
    work-->core
    widget-->core
    platform-services-->core

    analytics-->interactor
    repository-->interactor

    network-->repository
    persistence-->repository
```


## Роадмап
[Movies App Roadmap](https://github.com/users/michaelbel/projects/1/views/1)


## Вклад
[![](https://img.shields.io/github/issues-pr-closed-raw/michaelbel/movies?style=for-the-badge&logo=github&labelColor=3F464F&color=green)](https://github.com/michaelbel/movies/pulls)

Твоя помощь приветствуется!

⭐ Присоединяйся к [звездочетам](https://github.com/michaelbel/movies/stargazers)  
↗️ Отправляй пулл-реквесты


## Траблы
[![](https://img.shields.io/github/issues-closed-raw/michaelbel/movies?style=for-the-badge&logo=github&labelColor=3F464F&color=green)](https://github.com/michaelbel/movies/issues)

Если попался баг или хочешь предложить фичу, не стесняйся, заводи [issue](https://github.com/michaelbel/movies/issues).


## Контакты
[![](https://img.shields.io/badge/Telegram-24A1DE?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/michaelbel)
[![](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/michael-bel)
[![](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:michaelvel24865@gmail.com)
[![](https://img.shields.io/badge/Instagram-E1306C?style=for-the-badge&logo=instagram&logoColor=white)](https://instagram.com/michael_bel)
[![](https://img.shields.io/badge/Twitter-000000?style=for-the-badge&logo=x&logoColor=white)](https://x.com/michael_bely)
[![](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/@michaelbely)

Подписывайся на [telegram-канал](https://t.me/foundout)  
Добавляйся в друзья на [Кинопоиске](https://www.kinopoisk.ru/user/4104533)  
Добавляйся в друзья на [MyShows](https://myshows.me/michaelbel)


## История звездочек
<a href="https://star-history.com/#michaelbel/movies&Date">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=michaelbel/movies&type=Date&theme=dark" />
    <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/svg?repos=michaelbel/movies&type=Date" />
    <img alt="Star History Chart" src="https://api.star-history.com/svg?repos=michaelbel/movies&type=Date" />
  </picture>
</a>


## Лицензия
[![](https://img.shields.io/github/license/michaelbel/movies?style=for-the-badge&logo=github&labelColor=3F464F)](license.txt)

<a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">
  <img alt="Apache License 2.0" src="config/images/apache.png" height="110"/>
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
