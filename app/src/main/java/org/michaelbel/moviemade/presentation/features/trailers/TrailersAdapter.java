package org.michaelbel.moviemade.presentation.features.trailers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.core.entity.Video;
import org.michaelbel.moviemade.core.utils.DeviceUtil;
import org.michaelbel.moviemade.core.utils.ViewUtil;
import org.michaelbel.moviemade.core.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.internal.DebouncingOnClickListener;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    public interface Listener {
        void onTrailerClick(Video video, View view);
        boolean onTrailerLongClick(Video video, View view);
    }

    private Listener trailerClickListener;
    private List<Video> trailers = new ArrayList<>();

    public TrailersAdapter(Listener listener) {
        trailerClickListener = listener;
    }

    public List<Video> getTrailers() {
        return trailers;
    }

    public void addTrailers(List<Video> results) {
        trailers.addAll(results);
        notifyItemRangeInserted(trailers.size() + 1, results.size());
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trailer, parent, false);
        TrailersViewHolder holder = new TrailersViewHolder(view);
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    trailerClickListener.onTrailerClick(trailers.get(pos), v);
                }
            }
        });
        view.setOnLongClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                trailerClickListener.onTrailerLongClick(trailers.get(pos), v);
                return true;
            }
            return false;
        });

        if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 3.5);
        } else {
            view.getLayoutParams().height = parent.getWidth() / 2;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Video trailer = trailers.get(position);

        holder.trailerName.setText(trailer.getName());
        holder.qualityText.setText(holder.itemView.getContext().getString(R.string.video_size, String.valueOf(trailer.getSize())));

        Glide.with(holder.itemView.getContext()).load(String.format(Locale.US, TmdbConfigKt.YOUTUBE_IMAGE, trailer.getKey())).thumbnail(0.1F).into(holder.stillImage);

        trailer.getSite();
        if (trailer.getSite().equals("YouTube")) {
            holder.playerIcon.setImageDrawable(ViewUtil.INSTANCE.getIcon(holder.itemView.getContext(), R.drawable.ic_youtube, ContextCompat.getColor(holder.itemView.getContext(), R.color.youtubeColor)));
        } else {
            holder.playerIcon.setImageDrawable(ViewUtil.INSTANCE.getIcon(holder.itemView.getContext(), R.drawable.ic_play_circle, ContextCompat.getColor(holder.itemView.getContext(), R.color.iconActiveColor)));
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView stillImage;
        AppCompatImageView playerIcon;
        AppCompatTextView trailerName;
        AppCompatTextView qualityText;

        private TrailersViewHolder(View view) {
            super(view);
            stillImage = view.findViewById(R.id.still_image);
            playerIcon = view.findViewById(R.id.player_icon);
            trailerName = view.findViewById(R.id.trailer_name);
            qualityText = view.findViewById(R.id.quality_badge);
        }
    }
}