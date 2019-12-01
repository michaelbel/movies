package org.michaelbel.moviemade.presentation.features.trailers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_lce.*
import kotlinx.android.synthetic.main.view_player_dialog.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.domain.TrailersRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import javax.inject.Inject

class TrailersFragment: BaseFragment(R.layout.fragment_lce) {

    companion object {
        private const val ARG_MOVIE = "movie"
        private const val DIALOG_TAG = "dialog_youtube"

        fun newInstance(movie: Movie): TrailersFragment {
            return TrailersFragment().apply {
                arguments = bundleOf(ARG_MOVIE to movie)
            }
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: TrailersRepository

    private val viewModel: TrailersModel by lazy { getViewModel { TrailersModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        toolbar.apply {
            title = getString(R.string.trailers)
            subtitle = movie.title
            setOnClickListener { onScrollToTop() }
            navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
            setNavigationOnClickListener { requireActivity().finish() }
        }

        val spans = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, 5F.toDp(requireContext())))

        emptyView.setOnClickListener {
            emptyView.gone()
            viewModel.trailers(movie.id.toLong())
        }

        viewModel.trailers(movie.id.toLong())
        viewModel.loading.reObserve(viewLifecycleOwner, Observer { progressBar.visibility = if (it) VISIBLE else GONE })
        viewModel.content.reObserve(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            recyclerView.scheduleLayoutAnimation()
        })
        viewModel.error.reObserve(viewLifecycleOwner, Observer {
            emptyView.visible()
            emptyView.setMode(it)
        })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            val dialog = YoutubePlayerDialogFragment.newInstance(it.key.toUri().toString())
            dialog.show(requireActivity().supportFragmentManager, DIALOG_TAG)
        })
        viewModel.longClick.reObserve(viewLifecycleOwner, Observer { startActivity(Intent(Intent.ACTION_VIEW, ("vnd.youtube:" + it.key).toUri())) })
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    class YoutubePlayerDialogFragment: BottomSheetDialogFragment() {

        companion object {
            private const val KEY_VIDEO_URL = "video_url"

            fun newInstance(url: String): YoutubePlayerDialogFragment {
                return YoutubePlayerDialogFragment().apply {
                    arguments = bundleOf(KEY_VIDEO_URL to url)
                }
            }
        }

        private lateinit var videoUrl: String

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
                inflater.inflate(R.layout.view_player_dialog, container, false)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            videoUrl = arguments?.getString(KEY_VIDEO_URL) ?: ""

            lifecycle.addObserver(playerView)

            playerView.initialize({
                it.addListener(object: AbstractYouTubePlayerListener() {
                    override fun onReady() {
                        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                            it.loadVideo(videoUrl, 0F)
                        } else {
                            it.cueVideo(videoUrl, 0F)
                        }
                    }
                })
            }, true)
        }
    }
}