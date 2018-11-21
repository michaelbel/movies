package org.michaelbel.moviemade.ui.modules.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Genre;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder> {

    private List<Genre> genres = new ArrayList<>();

    void setGenres(List<Genre> results) {
        genres.addAll(results);
        notifyItemRangeInserted(genres.size() + 1, results.size());
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip, parent, false);
        return new GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
        Genre genre = genres.get(position);
        holder.chipText.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    class GenresViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.chip_name) AppCompatTextView chipText;

        private GenresViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}