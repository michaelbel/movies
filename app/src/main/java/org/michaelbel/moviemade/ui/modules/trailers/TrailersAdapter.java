package org.michaelbel.moviemade.ui.modules.trailers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Video;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
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

public class TrailersAdapter extends RecyclerView.Adapter {

    private AppCompatImageView stillImage;
    private AppCompatImageView playerIcon;
    private AppCompatTextView trailerName;
    private AppCompatTextView qualityText;

    public ArrayList<Video> trailers = new ArrayList<>();

    public void setTrailers(List<Video> results) {
        trailers.addAll(results);
        notifyItemRangeInserted(trailers.size() + 1, results.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 3.5);
        } else {
            view.getLayoutParams().height = parent.getWidth() / 2;
        }

        stillImage = view.findViewById(R.id.still_image);
        playerIcon = view.findViewById(R.id.player_icon);
        trailerName = view.findViewById(R.id.author_name);
        qualityText = view.findViewById(R.id.quality_badge);
        return new RecyclerListView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Video trailer = trailers.get(position);

        trailerName.setText(trailer.getName());
        qualityText.setText(holder.itemView.getContext().getString(R.string.video_size, String.valueOf(trailer.getSize())));

        Glide.with(holder.itemView.getContext()).load(String.format(Locale.US, ConstantsKt.YOUTUBE_IMAGE, trailer.getKey())).thumbnail(0.1F).into(stillImage);

        trailer.getSite();
        if (trailer.getSite().equals("YouTube")) {
            playerIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(holder.itemView.getContext(), R.drawable.ic_youtube, ContextCompat.getColor(holder.itemView.getContext(), R.color.youtubeColor)));
        } else {
            playerIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(holder.itemView.getContext(), R.drawable.ic_play_circle, ContextCompat.getColor(holder.itemView.getContext(), R.color.iconActive)));
        }
    }

    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }
}