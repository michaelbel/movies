package org.michaelbel.moviemade.presentation.features.keywords

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.domain.KeywordsRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment2
import javax.inject.Inject

class KeywordsFragment: BaseFragment(R.layout.fragment_lce) {

    companion object {
        fun newInstance(movie: Movie): KeywordsFragment {
            return KeywordsFragment().apply {
                arguments = bundleOf("movie" to movie)
            }
        }
    }

    private val movie: Movie? by argumentDelegate()

    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: KeywordsRepository

    private val viewModel: KeywordsModel by lazy { getViewModel { KeywordsModel(repository) } }

    private val recyclerViewLayoutParams: ConstraintLayout.LayoutParams
        get() {
            val params = recyclerView.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(resources.getDimension(R.dimen.fragment_keywords_margin).toInt())
            return params
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            title = getString(R.string.keywords)
            subtitle = movie?.title
            navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
            setOnClickListener { onScrollToTop() }
            setNavigationOnClickListener { requireActivity().finish() }
        }

        adapter = ListAdapter()

        recyclerView.apply {
            this.adapter = adapter
            this.layoutParams = recyclerViewLayoutParams
            this.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()
        }

        emptyView.setOnClickListener {
            emptyView.isGone = true
            viewModel.keywords(movie?.id?.toLong() ?: 0L)
        }

        viewModel.keywords(movie?.id?.toLong() ?: 0L)
        /*viewModel.loading.observe(viewLifecycleOwner, { progressBar.isVisible = it })
        viewModel.content.observe(viewLifecycleOwner, { adapter.setItems(it) })
        viewModel.error.observe(viewLifecycleOwner, {
            emptyView.visible()
            emptyView.setMode(it)
        })
        viewModel.click.observe(viewLifecycleOwner, {
            requireFragmentManager().commitNow {
                add((requireActivity() as ContainerActivity).container.id, MoviesFragment2.newInstance(it))
                addToBackStack(tag)
            }
        })
        viewModel.longClick.observe(viewLifecycleOwner, {
            requireFragmentManager().commitNow {
                add((requireActivity() as ContainerActivity).container.id, MoviesFragment2.newInstance(it))
                addToBackStack(tag)
            }
        })*/
    }
}