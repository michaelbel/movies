package org.michaelbel.moviemade.presentation.features.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.databinding.FragmentSearchBinding
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesModel
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import org.michaelbel.moviemade.presentation.features.search.SearchActivity.Companion.EXTRA_QUERY
import java.util.*

@AndroidEntryPoint
class SearchMoviesFragment: BaseFragment(R.layout.fragment_search) {

    private val viewModel: MoviesModel by viewModels()
    private val binding: FragmentSearchBinding by viewBinding()

    private var iconActionMode: Int = ITEM_MIC

    private var query: String? = null
    private var actionMenu: Menu? = null
    private lateinit var adapter: ListAdapter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val results = data.getStringArrayListExtra(EXTRA_RESULTS)
            if (results != null && results.size > 0) {
                val textResults = results[0]
                if (!TextUtils.isEmpty(textResults)) {
                    binding.searchEditText.setText(textResults)
                    val text = binding.searchEditText.text ?: ""
                    binding.searchEditText.setSelection(text.length)
                    changeActionIcon()
                    search(textResults)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener { onScrollToTop() }
        binding.toolbar.navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
        binding.toolbar.setNavigationOnClickListener { requireActivity().finish() }
        binding.toolbar.inflateMenu(R.menu.menu_search)
        binding.toolbar.setOnMenuItemClickListener {
            if (iconActionMode == ITEM_CLR) {
                changeActionIcon()
                binding.searchEditText.text?.clear()
                binding.searchEditText.showKeyboard()
            } else {
                val intent = Intent(ACTION_RECOGNIZE_SPEECH).apply {
                    putExtras(bundleOf(EXTRA_LANGUAGE_MODEL to LANGUAGE_MODEL_FREE_FORM, EXTRA_LANGUAGE to Locale.ENGLISH, EXTRA_PROMPT to R.string.speak_now))
                }
                startActivityForResult(intent, SPEECH_REQUEST_CODE)
            }
            return@setOnMenuItemClickListener true
        }
        actionMenu = binding.toolbar.menu

        val spans = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = ListAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        binding.recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, 3F.toDp(requireContext())))
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && query != null) {
                    viewModel.searchMovies(query as String)
                }
            }
        })

        binding.progressBar.isGone = true

        binding.emptyView.setMode(EmptyState.MODE_NO_RESULTS)

        binding.searchEditText.doOnTextChanged { _, _, _, _ -> changeActionIcon() }
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(v.text.toString().trim())
                binding.searchEditText.hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.searchEditText.setSelection(binding.searchEditText.text.toString().length)

        if (savedInstanceState != null) {
            iconActionMode = savedInstanceState.getInt(KEY_MENU_ICON)
        }

        changeActionIcon()

        // Start search with query argument.
        query = arguments?.getString(EXTRA_QUERY)
        if (query != null) {
            binding.searchEditText.setText(query)
            binding.searchEditText.setSelection(binding.searchEditText.text.toString().length)
            if (query != null) {
                search(query as String)
            }
        }

        binding.searchEditText.showKeyboard()

        launchAndRepeatWithViewLifecycle {
            launch { viewModel.loading.collect { binding.progressBar.isVisible = it } }
            launch {
                viewModel.content.collect {
                    adapter.setItems(it)
                    binding.recyclerView.scheduleLayoutAnimation()
                    binding.searchEditText.hideKeyboard()
                }
            }
            launch { viewModel.error.collect {
                binding.emptyView.isVisible = true
                binding.emptyView.setMode(it)

                if (BuildUtil.isApiKeyEmpty) {
                    binding.emptyView.setValue(R.string.error_empty_api_key)
                }
            } }
            launch { viewModel.click.collect {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            } }
            launch { viewModel.longClick.collect {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            } }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MENU_ICON, iconActionMode)
        super.onSaveInstanceState(outState)
    }

    override fun onScrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0)
    }

    private fun search(query: String) {
        adapter.clear()
        binding.emptyView.isGone = true

        this.query = query
        viewModel.searchMovies(query)
    }

    private fun changeActionIcon() {
        if (actionMenu != null) {
            val searchEmpty = binding.searchEditText.text.toString().trim().isEmpty()
            iconActionMode = if (searchEmpty) ITEM_MIC else ITEM_CLR
            actionMenu?.getItem(MENU_ITEM_INDEX)?.setIcon(if (searchEmpty) R.drawable.ic_voice else R.drawable.ic_clear)
        }
    }

    companion object {
        private const val KEY_MENU_ICON = "icon"

        private const val SPEECH_REQUEST_CODE = 101
        private const val MENU_ITEM_INDEX = 0
        private const val ITEM_CLR = 1
        private const val ITEM_MIC = 2

        fun newInstance(query: String?): SearchMoviesFragment {
            return SearchMoviesFragment().apply {
                arguments = bundleOf(EXTRA_QUERY to query)
            }
        }
    }
}