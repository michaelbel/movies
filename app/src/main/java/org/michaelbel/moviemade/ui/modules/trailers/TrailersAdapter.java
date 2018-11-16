package org.michaelbel.moviemade.ui.modules.trailers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Video;
import org.michaelbel.moviemade.utils.ConstantsKt;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.DrawableUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    public ArrayList<Video> trailers = new ArrayList<>();

    public void setTrailers(List<Video> results) {
        trailers.addAll(results);
        notifyItemRangeInserted(trailers.size() + 1, results.size());
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 3.5);
        } else {
            view.getLayoutParams().height = parent.getWidth() / 2;
        }

        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Video trailer = trailers.get(position);

        holder.trailerName.setText(trailer.getName());
        holder.qualityText.setText(holder.itemView.getContext().getString(R.string.video_size, String.valueOf(trailer.getSize())));

        Glide.with(holder.itemView.getContext()).load(String.format(Locale.US, ConstantsKt.YOUTUBE_IMAGE, trailer.getKey())).thumbnail(0.1F).into(holder.stillImage);

        trailer.getSite();
        if (trailer.getSite().equals("YouTube")) {
            holder.playerIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(holder.itemView.getContext(), R.drawable.ic_youtube, ContextCompat.getColor(holder.itemView.getContext(), R.color.youtubeColor)));
        } else {
            holder.playerIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(holder.itemView.getContext(), R.drawable.ic_play_circle, ContextCompat.getColor(holder.itemView.getContext(), R.color.iconActive)));
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.still_image) AppCompatImageView stillImage;
        @BindView(R.id.player_icon) AppCompatImageView playerIcon;
        @BindView(R.id.author_name) AppCompatTextView trailerName;
        @BindView(R.id.quality_badge) AppCompatTextView qualityText;

        private TrailersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}