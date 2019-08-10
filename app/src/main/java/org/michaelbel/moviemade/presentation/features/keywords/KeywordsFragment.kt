package org.michaelbel.moviemade.presentation.features.keywords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.domain.KeywordsRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.getViewModel
import org.michaelbel.moviemade.core.reObserve
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment2
import javax.inject.Inject

class KeywordsFragment: BaseFragment() {

    companion object {
        private const val ARG_MOVIE = "movie"

        internal fun newInstance(movie: Movie): KeywordsFragment {
            val args = Bundle()
            args.putSerializable(ARG_MOVIE, movie)

            val fragment = KeywordsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: KeywordsRepository

    private val viewModel: KeywordsModel by lazy { getViewModel { KeywordsModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
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
        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutParams = recyclerViewLayoutParams()
        recyclerView.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            viewModel.keywords(movie.id.toLong())
        }

        viewModel.keywords(movie.id.toLong())
        viewModel.loading.reObserve(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.content.reObserve(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
        viewModel.error.reObserve(viewLifecycleOwner, Observer {
            emptyView.visibility = VISIBLE
            emptyView.setMode(it)
        })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            requireFragmentManager().transaction {
                add((requireActivity() as ContainerActivity).container.id, MoviesFragment2.newInstance(it))
                addToBackStack(tag)
            }
        })
        viewModel.longClick.reObserve(viewLifecycleOwner, Observer {
            requireFragmentManager().transaction {
                add((requireActivity() as ContainerActivity).container.id, MoviesFragment2.newInstance(it))
                addToBackStack(tag)
            }
        })
    }

    private fun recyclerViewLayoutParams(): ConstraintLayout.LayoutParams {
        val params = recyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.setMargins(resources.getDimension(R.dimen.fragment_keywords_margin).toInt())
        return params
    }
}