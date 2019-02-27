package org.michaelbel.moviemade.presentation.features.trailers.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.view_player_dialog.*
import org.michaelbel.moviemade.R

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

        lifecycle.addObserver(player_view)

        player_view.initialize({ youTubePlayer ->
            youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                        youTubePlayer.loadVideo(videoUrl, 0F)
                    } else {
                        youTubePlayer.cueVideo(videoUrl, 0F)
                    }
                }
            })
        }, true)
    }
}