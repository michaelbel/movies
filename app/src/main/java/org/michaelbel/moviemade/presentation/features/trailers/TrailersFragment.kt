package org.michaelbel.moviemade.presentation.features.trailers

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_trailers.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.MOVIE_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.trailers.dialog.YoutubePlayerDialogFragment
import javax.inject.Inject

class TrailersFragment: BaseFragment(),
        NetworkChangeReceiver.Listener,
        TrailersContract.View,
        TrailersAdapter.Listener {

    companion object {
        private const val YOUTUBE_DIALOG_FRAGMENT_TAG = "youtubeFragment"

        internal fun newInstance(movieId: Int): TrailersFragment {
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)

            val fragment = TrailersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var movieId: Int = 0
    lateinit var adapter: TrailersAdapter

    private var connectionFailure = false
    private var networkChangeReceiver: NetworkChangeReceiver? = null

    @Inject
    lateinit var presenter: TrailersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trailers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as TrailersActivity).toolbar.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        val spanCount = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = TrailersAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 5F)))

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            progressBar.visibility = VISIBLE
            presenter.getVideos(movieId)
        }

        movieId = if (arguments != null) arguments!!.getInt(MOVIE_ID) else 0
        presenter.getVideos(movieId)
    }

    override fun onTrailerClick(video: Video) {
        val dialog = YoutubePlayerDialogFragment.newInstance(video.key.toUri().toString())
        dialog.show(requireActivity().supportFragmentManager, YOUTUBE_DIALOG_FRAGMENT_TAG)
    }

    override fun onTrailerLongClick(video: Video): Boolean {
        startActivity(Intent(Intent.ACTION_VIEW, ("vnd.youtube:" + video.key).toUri()))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = GONE
    }

    override fun setTrailers(trailers: List<Video>) {
        connectionFailure = false
        adapter.addTrailers(trailers)
    }

    override fun setError(@EmptyViewMode mode: Int) {
        connectionFailure = true
        emptyView.visibility = VISIBLE
        emptyView.setMode(mode)
        hideLoading()
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter.itemCount == 0) {
            presenter.getVideos(movieId)
        }
    }
}