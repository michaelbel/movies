package org.michaelbel.moviemade.presentation.features.search

import android.app.Activity.RESULT_OK
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.BuildUtil
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.ViewUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import java.util.*
import javax.inject.Inject

class SearchMoviesFragment: BaseFragment(), SearchContract.View, MoviesAdapter.Listener {

    companion object {
        private const val KEY_MENU_ICON = "icon"

        private const val SPEECH_REQUEST_CODE = 101
        private const val MENU_ITEM_INDEX = 0
        private const val ITEM_CLR = 1
        private const val ITEM_MIC = 2

        internal fun newInstance() = SearchMoviesFragment()
    }

    private var iconActionMode = ITEM_MIC

    private var actionMenu: Menu? = null
    lateinit var adapter: MoviesAdapter

    @Inject
    lateinit var presenter: SearchContract.Presenter

    private lateinit var searchView: AppCompatEditText

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (results != null && results.size > 0) {
                    val textResults = results[0]
                    if (!TextUtils.isEmpty(textResults)) {
                        searchView.setText(textResults)
                        val text = searchView.text ?: ""
                        searchView.setSelection(text.length)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        actionMenu = menu
        val icon = if (iconActionMode == ITEM_MIC) R.drawable.ic_voice else R.drawable.ic_clear
        menu.add(null).setIcon(icon).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM).setOnMenuItemClickListener {
            if (iconActionMode == ITEM_CLR) {
                if (searchView.text != null) {
                    searchView.text?.clear()
                }
                changeActionIcon()

                val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
            } else {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now)
                startActivityForResult(intent, SPEECH_REQUEST_CODE)
            }
            true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = (requireActivity() as SearchActivity).search_edit_text

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadNextResults()
                }
            }
        })

        progressBar.visibility = GONE

        emptyView.setMode(EmptyViewMode.MODE_NO_RESULTS)

        searchView.background = null
        searchView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                changeActionIcon()
                if (s.toString().trim().length >= 2) {
                    presenter.search(s.toString().trim { it <= ' ' })
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        searchView.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(v.text.toString().trim { it <= ' ' })
                hideKeyboard(searchView)
                return@setOnEditorActionListener true
            }
            false
        }

        searchView.setSelection(searchView.text!!.length)
        ViewUtil.clearCursorDrawable(searchView)

        if (savedInstanceState != null) {
            iconActionMode = savedInstanceState.getInt(KEY_MENU_ICON)
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
        progressBar.visibility = VISIBLE
    }

    override fun setMovies(movies: List<Movie>) {
        progressBar.visibility = GONE
        adapter.addMovies(movies)
    }

    override fun setError(mode: Int) {
        progressBar.visibility = GONE
        emptyView.visibility = VISIBLE
        emptyView.setMode(mode)

        if (BuildUtil.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onMovieClick(movie: Movie) {
        (requireActivity() as SearchActivity).startMovie(movie)
    }

    private fun changeActionIcon() {
        if (actionMenu != null) {
            if (searchView.text.toString().trim().isEmpty()) {
                iconActionMode = ITEM_MIC
                actionMenu?.getItem(MENU_ITEM_INDEX)?.setIcon(R.drawable.ic_voice)
            } else {
                iconActionMode = ITEM_CLR
                actionMenu?.getItem(MENU_ITEM_INDEX)?.setIcon(R.drawable.ic_clear)
            }
        }
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