package org.michaelbel.moviemade.presentation.features.search

import android.os.Bundle
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.ActivityParentBinding
import org.michaelbel.moviemade.presentation.common.base.BaseActivity

@AndroidEntryPoint
class SearchActivity: BaseActivity(R.layout.activity_parent) {

    private val binding: ActivityParentBinding by viewBinding(R.id.parentLayout)

    val containerId: Int
        get() = binding.container.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val query: String? = if (intent.getStringExtra(EXTRA_QUERY) != null) intent.getStringExtra(EXTRA_QUERY) else null

        if (savedInstanceState == null) {
            supportFragmentManager.commit { replace(containerId, SearchMoviesFragment.newInstance(query)) }
        }
    }

    companion object {
        const val EXTRA_QUERY = "query"
    }
}