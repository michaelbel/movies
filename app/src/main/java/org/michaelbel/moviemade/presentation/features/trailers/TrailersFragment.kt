package org.michaelbel.moviemade.presentation.features.trailers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_lce.*
import kotlinx.android.synthetic.main.view_player_dialog.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import javax.inject.Inject

class TrailersFragment: BaseFragment() {

    companion object {
        private const val ARG_MOVIE = "movie"
        private const val DIALOG_TAG = "dialog_youtube"

        internal fun newInstance(movie: Movie): TrailersFragment {
            val args = Bundle()
            args.putSerializable(ARG_MOVIE, movie)

            val fragment = TrailersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: TrailersModel

    @Inject
    lateinit var factory: TrailersFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(TrailersModel::class.java)
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        toolbar.title = getString(R.string.trailers)
        toolbar.subtitle = movie.title
        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        val spans = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, DeviceUtil.dp(requireContext(), 5F)))

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            viewModel.trailers(movie.id)
        }

        viewModel.trailers(movie.id)
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.content.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            emptyView.visibility = VISIBLE
            emptyView.setMode(it)
        })
        viewModel.click.observe(viewLifecycleOwner, Observer {
            val dialog = YoutubePlayerDialogFragment.newInstance(it.key.toUri().toString())
            dialog.show(requireActivity().supportFragmentManager, DIALOG_TAG)
        })
        viewModel.longClick.observe(viewLifecycleOwner, Observer {
            startActivity(Intent(Intent.ACTION_VIEW, ("vnd.youtube:" + it.key).toUri()))
        })
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    class YoutubePlayerDialogFragment: BottomSheetDialogFragment() {

        companion object {
            private const val KEY_VIDEO_URL = "video_url"

            fun newInstance(url: String): YoutubePlayerDialogFragment {
                val args = Bundle()
                args.putString(KEY_VIDEO_URL, url)

                val fragment = YoutubePlayerDialogFragment()
                fragment.arguments = args
                return fragment
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