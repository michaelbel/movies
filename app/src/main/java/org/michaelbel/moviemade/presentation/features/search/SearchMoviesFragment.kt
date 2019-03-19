package org.michaelbel.moviemade.presentation.features.search

import android.app.Activity.RESULT_OK
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import org.michaelbel.moviemade.presentation.features.search.SearchActivity.Companion.EXTRA_QUERY
import java.util.*
import javax.inject.Inject

class SearchMoviesFragment: BaseFragment(), SearchContract.View, MoviesAdapter.Listener {

    companion object {
        private const val KEY_MENU_ICON = "icon"

        private const val SPEECH_REQUEST_CODE = 101
        private const val MENU_ITEM_INDEX = 0
        private const val ITEM_CLR = 1
        private const val ITEM_MIC = 2

        internal fun newInstance(query: String): SearchMoviesFragment {
            val args = Bundle()
            args.putString(EXTRA_QUERY, query)

            val fragment = SearchMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var iconActionMode = ITEM_MIC

    private var query = ""
    private var actionMenu: Menu? = null
    private lateinit var adapter: MoviesAdapter

    @Inject
    lateinit var presenter: SearchContract.Presenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (results != null && results.size > 0) {
                    val textResults = results[0]
                    if (!TextUtils.isEmpty(textResults)) {
                        searchEditText.setText(textResults)
                        val text = searchEditText.text ?: ""
                        searchEditText.setSelection(text.length)
                        changeActionIcon()
                        presenter.search(textResults)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }
        toolbar.inflateMenu(R.menu.menu_search)
        toolbar.setOnMenuItemClickListener {
            if (iconActionMode == ITEM_CLR) {
                if (searchEditText.text != null) {
                    searchEditText.text?.clear()
                }
                changeActionIcon()
                showKeyboard(searchEditText)
            } else {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now)
                startActivityForResult(intent, SPEECH_REQUEST_CODE)
            }
            return@setOnMenuItemClickListener true
        }
        actionMenu = toolbar.menu

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.nextResults()
                }
            }
        })

        progressBar.visibility = GONE

        emptyView.setMode(EmptyViewMode.MODE_NO_RESULTS)

        searchEditText.addTextChangedListener(object: TextChanger {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeActionIcon()
                if (s.toString().trim().length >= 2) {
                    presenter.search(s.toString().trim { it <= ' ' })
                }
            }
        })
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(v.text.toString().trim())
                hideKeyboard(searchEditText)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        searchEditText.setSelection(searchEditText.text.toString().length)
        ViewUtil.clearCursorDrawable(searchEditText)

        if (savedInstanceState != null) {
            iconActionMode = savedInstanceState.getInt(KEY_MENU_ICON)
        }

        changeActionIcon()

        // Start search with query argument.
        query = arguments?.getString(EXTRA_QUERY) ?: ""
        if (query != "") {
            searchEditText.setText(query)
            searchEditText.setSelection(searchEditText.text.toString().length)
            presenter.search(query)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MENU_ICON, iconActionMode)
        super.onSaveInstanceState(outState)
    }

    override fun searchStart() {
        adapter.movies.clear()
        adapter.notifyDataSetChanged()
        emptyView.visibility = GONE
    }

    override fun loading(state: Boolean) {
        progressBar.visibility = if (state) VISIBLE else GONE
    }

    override fun content(results: List<Movie>) {
        adapter.addMovies(results)
        hideKeyboard(searchEditText)
    }

    override fun error(code: Int) {
        emptyView.visibility = VISIBLE
        emptyView.setMode(code)

        if (BuildUtil.isApiKeyEmpty()) {
            emptyView.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(requireContext(), MovieActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    private fun changeActionIcon() {
        if (actionMenu != null) {
            val searchEmpty = searchEditText.text.toString().trim().isEmpty()
            iconActionMode = if (searchEmpty) ITEM_MIC else ITEM_CLR
            actionMenu?.getItem(MENU_ITEM_INDEX)?.setIcon(if (searchEmpty) R.drawable.ic_voice else R.drawable.ic_clear)
        }
    }

    private fun showKeyboard(view: View) {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isActive) {
            return
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}