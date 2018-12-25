package org.michaelbel.moviemade.ui.modules.keywords.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Keyword;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class KeywordsAdapter extends RecyclerView.Adapter<KeywordsAdapter.KeywordsViewHolder> {

    private List<Keyword> keywords = new ArrayList<>();

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void addKeywords(List<Keyword> results) {
        keywords.addAll(results);
        notifyItemRangeInserted(keywords.size() + 1, results.size());
    }

    @NonNull
    @Override
    public KeywordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_keyword, parent, false);
        KeywordsViewHolder holder = new KeywordsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordsViewHolder holder, int position) {
        Keyword keyword = keywords.get(position);
        holder.keywordName.setText(keyword.getName());
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    class KeywordsViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView keywordName;

        private KeywordsViewHolder(View view) {
            super(view);
            keywordName = view.findViewById(R.id.keyword_name);
        }
    }
}