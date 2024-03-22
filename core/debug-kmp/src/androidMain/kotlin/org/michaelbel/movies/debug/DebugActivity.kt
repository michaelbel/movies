package org.michaelbel.movies.debug

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.debug.ui.DebugActivityContent

@AndroidEntryPoint
internal class DebugActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebugActivityContent { statusBarStyle, navigationBarStyle ->
                enableEdgeToEdge(statusBarStyle, navigationBarStyle)
            }
        }
    }
}