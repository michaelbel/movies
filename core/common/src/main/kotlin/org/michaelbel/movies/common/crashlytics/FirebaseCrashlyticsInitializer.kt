package org.michaelbel.movies.common.crashlytics

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

@Suppress("unused")
internal class FirebaseCrashlyticsInitializer: Initializer<FirebaseCrashlytics> {

    override fun create(context: Context): FirebaseCrashlytics {
        FirebaseApp.initializeApp(context.applicationContext)
        val firebaseCrashlytics: FirebaseCrashlytics = Firebase.crashlytics
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(true)
        return firebaseCrashlytics
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}