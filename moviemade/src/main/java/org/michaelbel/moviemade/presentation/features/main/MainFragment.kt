package org.michaelbel.moviemade.presentation.features.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.app.analytics.Analytics
import org.michaelbel.moviemade.app.ktx.dp
import org.michaelbel.moviemade.app.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.app.ktx.savedStateViewModels
import org.michaelbel.moviemade.app.ktx.smartScrollToTop
import org.michaelbel.moviemade.data.model.Movie
import org.michaelbel.moviemade.data.model.MovieResponse
import org.michaelbel.moviemade.databinding.FragmentMainBinding
import org.michaelbel.moviemade.presentation.features.main.adapter.MoviesPagingAdapter
import org.michaelbel.moviemade.presentation.features.main.adapter.SpacingItemDecoration
import org.michaelbel.moviemade.presentation.features.main.adapter.UiAction
import org.michaelbel.moviemade.presentation.features.main.adapter.UiState
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    @Inject lateinit var factory: MainViewModel.Factory

    private val list: String = Movie.NOW_PLAYING

    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by savedStateViewModels {
        factory.create(it, list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener { binding.recyclerView.smartScrollToTop() }

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpacingItemDecoration(1, 8.dp(requireContext())))
        }

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept,
        )

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.updateAvailableMessage.collect { updateAvailable ->
                    if (updateAvailable) {
                        Snackbar.make(binding.root, R.string.message_in_app_update_new_version_available, Snackbar.LENGTH_LONG)
                            .setAction(R.string.action_update, ::onAppUpdateClick)
                            .show()
                    }
                }
            }
            launch {
                viewModel.updateDownloadedMessage.collect { updateDownloaded ->
                    if (updateDownloaded) {
                        Snackbar.make(binding.root, R.string.message_in_app_update_new_version_downloaded, Snackbar.LENGTH_LONG)
                            .setAction(R.string.action_reload, ::onAppInstallClick)
                            .show()
                    }
                }
            }
        }
    }

    @Inject
    fun trackScreen(analytics: Analytics) {
        analytics.trackScreen(MainFragment::class.simpleName)
    }

    /*private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.topPadding = systemBars.top
            WindowInsetsCompat.CONSUMED
        }
    }*/

    private fun showMovieFragment(movie: MovieResponse) {}

    private fun onAppUpdateClick(view: View) {
        viewModel.snackBarActionUpdateClicked(requireActivity())
    }

    private fun onAppInstallClick(view: View) {
        viewModel.completeUpdate()
    }

    private fun FragmentMainBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<MovieResponse>>,
        uiActions: (UiAction) -> Unit
    ) {
        val moviesAdapter = MoviesPagingAdapter { showMovieFragment(it) }
        recyclerView.adapter = moviesAdapter

        bindList(
            repoAdapter = moviesAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun FragmentMainBinding.bindList(
        repoAdapter: MoviesPagingAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<MovieResponse>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.list))
            }
        })

        val notLoading = repoAdapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .map { it.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        launchAndRepeatWithViewLifecycle {
            launch {
                pagingData.collectLatest(repoAdapter::submitData)
            }
            launch {
                shouldScrollToTop.collect { shouldScroll ->
                    if (shouldScroll) recyclerView.scrollToPosition(0)
                }
            }
        }
    }
}
