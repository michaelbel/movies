package org.michaelbel.moviemade.presentation.features.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesModel
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import org.michaelbel.moviemade.presentation.features.search.SearchActivity.Companion.EXTRA_QUERY
import java.util.*
import javax.inject.Inject

class SearchMoviesFragment: BaseFragment(R.layout.fragment_search) {

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

    private var iconActionMode: Int = ITEM_MIC

    private var query: String? = null
    private var actionMenu: Menu? = null
    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: MoviesRepository

    private val viewModel: MoviesModel by lazy { getViewModel { MoviesModel(repository) } }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val results = data.getStringArrayListExtra(EXTRA_RESULTS)
            if (results != null && results.size > 0) {
                val textResults = results[0]
                if (!TextUtils.isEmpty(textResults)) {
                    searchEditText.setText(textResults)
                    val text = searchEditText.text ?: ""
                    searchEditText.setSelection(text.length)
                    changeActionIcon()
                    search(textResults)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }
        toolbar.inflateMenu(R.menu.menu_search)
        toolbar.setOnMenuItemClickListener {
            if (iconActionMode == ITEM_CLR) {
                changeActionIcon()
                searchEditText.text?.clear()
                searchEditText.showKeyboard()
            } else {
                val intent = Intent(ACTION_RECOGNIZE_SPEECH).apply {
                    putExtras(bundleOf(EXTRA_LANGUAGE_MODEL to LANGUAGE_MODEL_FREE_FORM, EXTRA_LANGUAGE to Locale.ENGLISH, EXTRA_PROMPT to R.string.speak_now))
                }
                startActivityForResult(intent, SPEECH_REQUEST_CODE)
            }
            return@setOnMenuItemClickListener true
        }
        actionMenu = toolbar.menu

        val spans = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, 3F.toDp(requireContext())))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && query != null) {
                    viewModel.searchMovies(query as String)
                }
            }
        })

        progressBar.gone()

        emptyView.setMode(EmptyState.MODE_NO_RESULTS)

        searchEditText.doOnTextChanged { _, _, _, _ -> changeActionIcon() }
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(v.text.toString().trim())
                searchEditText.hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        searchEditText.setSelection(searchEditText.text.toString().length)
        searchEditText.clearCursorDrawable()

        if (savedInstanceState != null) {
            iconActionMode = savedInstanceState.getInt(KEY_MENU_ICON)
        }

        changeActionIcon()

        // Start search with query argument.
        query = arguments?.getString(EXTRA_QUERY)
        if (query != null) {
            searchEditText.setText(query)
            searchEditText.setSelection(searchEditText.text.toString().length)
            if (query != null) {
                search(query as String)
            }
        }

        viewModel.loading.reObserve(viewLifecycleOwner, Observer { progressBar.visibility = if (it) VISIBLE else GONE })
        viewModel.content.reObserve(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            recyclerView.scheduleLayoutAnimation()
            searchEditText.hideKeyboard()
        })
        viewModel.error.reObserve(viewLifecycleOwner, Observer { error ->
            error.getContentIfNotHandled()?.let {
                emptyView.visible()
                emptyView.setMode(it)

                if (BuildUtil.isApiKeyEmpty) {
                    emptyView.setValue(R.string.error_empty_api_key)
                }
            }
        })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireActivity().startActivity<MovieActivity> { putExtra(EXTRA_MOVIE, it) }
            }
        })
        viewModel.longClick.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireActivity().startActivity<MovieActivity> { putExtra(EXTRA_MOVIE, it) }
            }
        })

        searchEditText.showKeyboard()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MENU_ICON, iconActionMode)
        super.onSaveInstanceState(outState)
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    private fun search(query: String) {
        adapter.clear()
        emptyView.gone()

        this.query = query
        viewModel.searchMovies(query)
    }

    private fun changeActionIcon() {
        if (actionMenu != null) {
            val searchEmpty = searchEditText.text.toString().trim().isEmpty()
            iconActionMode = if (searchEmpty) ITEM_MIC else ITEM_CLR
            actionMenu?.getItem(MENU_ITEM_INDEX)?.setIcon(if (searchEmpty) R.drawable.ic_voice else R.drawable.ic_clear)
        }
    }
}