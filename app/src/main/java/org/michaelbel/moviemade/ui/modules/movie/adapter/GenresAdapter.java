package org.michaelbel.moviemade.ui.modules.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Genre;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder> {

    private List<Genre> genres = new ArrayList<>();

    public void setGenres(List<Genre> results) {
        genres.addAll(results);
        notifyItemRangeInserted(genres.size() + 1, results.size());
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_genre, parent, false);
        GenresViewHolder holder = new GenresViewHolder(view);
        return holder;
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

        AppCompatTextView chipText;

        private GenresViewHolder(View view) {
            super(view);
            chipText = view.findViewById(R.id.chip_name);
        }
    }
}