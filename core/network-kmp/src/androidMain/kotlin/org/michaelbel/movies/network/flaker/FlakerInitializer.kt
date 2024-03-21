@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.network.flaker

import android.content.Context
import androidx.startup.Initializer
import io.github.rotbolt.flakerandroidokhttp.di.FlakerAndroidOkhttpContainer

@Suppress("unused")
actual class FlakerInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        FlakerAndroidOkhttpContainer.install(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}