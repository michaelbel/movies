package org.michaelbel.movies.network.flaker

import android.content.Context
import androidx.startup.Initializer
import io.github.rotbolt.flakerandroidokhttp.di.FlakerAndroidOkhttpContainer

@Suppress("unused")
class FlakerInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        FlakerAndroidOkhttpContainer.install(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}