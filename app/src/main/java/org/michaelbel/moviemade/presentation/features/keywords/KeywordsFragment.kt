package org.michaelbel.moviemade.presentation.features.keywords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import kotlinx.android.synthetic.main.activity_container.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.MoviesFragment2
import org.michaelbel.moviemade.presentation.base.BaseFragment
import javax.inject.Inject

/**
 * Show keywords list by movie.
 */
class KeywordsFragment: BaseFragment(), KeywordsContract.View, KeywordsAdapter.Listener {

    companion object {
        private const val ARG_MOVIE = "movie"
        private const val FRAGMENT_TAG = "fragment_keyword"

        internal fun newInstance(movie: Movie): KeywordsFragment {
            val args = Bundle()
            args.putSerializable(ARG_MOVIE, movie)

            val fragment = KeywordsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: KeywordsAdapter

    @Inject
    lateinit var presenter: KeywordsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        toolbar.title = getString(R.string.keywords)
        toolbar.subtitle = movie.title
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        adapter = KeywordsAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutParams = recyclerViewLayoutParams()
        recyclerView.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL).build()

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            progressBar.visibility = VISIBLE
            presenter.keywords(movie.id)
        }

        presenter.keywords(movie.id)
    }

    override fun onKeywordClick(keyword: Keyword) {
        requireFragmentManager()
                .beginTransaction()
                .replace((requireActivity() as ContainerActivity).container.id, MoviesFragment2.newInstance(keyword))
                .addToBackStack(FRAGMENT_TAG)
                .commit()
    }

    override fun loading(state: Boolean) {
        progressBar.visibility = if (state) VISIBLE else GONE
    }

    override fun content(results: List<Keyword>) {
        adapter.addKeywords(results)
    }

    override fun error(code: Int) {
        emptyView.visibility = VISIBLE
        emptyView.setMode(code)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    private fun recyclerViewLayoutParams(): ConstraintLayout.LayoutParams {
        val params = recyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.setMargins(resources.getDimension(R.dimen.fragment_keywords_margin).toInt())
        return params
    }
}