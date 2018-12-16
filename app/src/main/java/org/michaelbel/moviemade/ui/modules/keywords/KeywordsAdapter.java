package org.michaelbel.moviemade.ui.modules.keywords;

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
import butterknife.BindView;
import butterknife.ButterKnife;

public class KeywordsAdapter extends RecyclerView.Adapter<KeywordsAdapter.KeywordsViewHolder> {

    private List<Keyword> keywords = new ArrayList<>();

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> results) {
        keywords.addAll(results);
        notifyItemRangeInserted(keywords.size() + 1, results.size());
    }

    @NonNull
    @Override
    public KeywordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_keyword, parent, false);
        return new KeywordsViewHolder(view);
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

        @BindView(R.id.keyword_name) AppCompatTextView keywordName;

        private KeywordsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}