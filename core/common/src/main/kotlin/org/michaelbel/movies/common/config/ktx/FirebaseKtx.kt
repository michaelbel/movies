package org.michaelbel.movies.common.config.ktx

import android.content.Context
import com.google.firebase.FirebaseApp

fun Context.installFirebaseApp() {
    FirebaseApp.initializeApp(this)
}