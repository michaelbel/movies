package org.michaelbel.moviemade.ui.modules.trailers.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;

import org.michaelbel.moviemade.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class YoutubePlayerDialogFragment extends BottomSheetDialogFragment {

    private static final String KEY_VIDEO_URL = "video_url";

    private String videoUrl;
    private Unbinder unbinder;

    @BindView(R.id.player_view) YouTubePlayerView youTubePlayerView;

    public static YoutubePlayerDialogFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(KEY_VIDEO_URL, url);

        YoutubePlayerDialogFragment fragment = new YoutubePlayerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_player_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            videoUrl = args.getString(KEY_VIDEO_URL);
        }

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.initialize(youTubePlayer -> {
            youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {
                    if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                        youTubePlayer.loadVideo(videoUrl, 0);
                    } else {
                        youTubePlayer.cueVideo(videoUrl, 0);
                    }
                }
            });
        },true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}