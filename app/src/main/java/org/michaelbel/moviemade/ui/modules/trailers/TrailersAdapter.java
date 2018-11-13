package org.michaelbel.moviemade.ui.modules.trailers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.extensions.DeviceUtil;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersAdapter extends RecyclerView.Adapter {

    private ImageView stillImage;
    private ImageView playerIcon;
    private AppCompatTextView trailerName;
    private AppCompatTextView qualityText;

    public ArrayList<Trailer> trailers;

    public TrailersAdapter() {
        trailers = new ArrayList<>();
    }

    public void setTrailers(ArrayList<Trailer> results) {
        trailers.addAll(results);
        notifyItemRangeInserted(trailers.size() + 1, results.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        if (DeviceUtil.isLandscape(parent.getContext()) || DeviceUtil.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 3.5);
        } else {
            view.getLayoutParams().height = parent.getWidth() / 2;
        }

        stillImage = view.findViewById(R.id.still_image);
        playerIcon = view.findViewById(R.id.player_icon);
        trailerName = view.findViewById(R.id.trailer_name);
        qualityText = view.findViewById(R.id.quality_badge);
        return new RecyclerListView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Trailer trailer = trailers.get(position);

        trailerName.setText(trailer.name);
        qualityText.setText(holder.itemView.getContext().getString(R.string.video_size, trailer.size));

        Glide.with(holder.itemView.getContext())
             .load(String.format(Locale.US, Url.YOUTUBE_IMAGE, trailer.key))
             .thumbnail(0.1F)
             .into(stillImage);

        if (trailer.site != null) {
            if (trailer.site.equals("YouTube")) {
                playerIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_youtube, ContextCompat.getColor(holder.itemView.getContext(), R.color.youtubeColor)));
            } else {
                playerIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_play_circle, ContextCompat.getColor(holder.itemView.getContext(), R.color.iconActive)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }
}