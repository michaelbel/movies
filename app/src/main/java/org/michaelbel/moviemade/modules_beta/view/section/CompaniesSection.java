package org.michaelbel.moviemade.modules_beta.view.section;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.modules_beta.view.ChipView;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CompaniesSection extends FrameLayout {

    private CompaniesAdapter adapter;
    private CompaniesSectionListener companiesListener;
    private List<Company> companies = new ArrayList<>();

    public interface CompaniesSectionListener {
        void onCompaniesClick(View view, Company company);
    }

    public CompaniesSection(Context context) {
        super(context);

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                .setChildGravity(Gravity.START)
                .setScrollingEnabled(false)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();

        adapter = new CompaniesAdapter();

        RecyclerListView recyclerView = new RecyclerListView(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(chipsLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Company company = companies.get(position);
            companiesListener.onCompaniesClick(view, company);
        });
        addView(recyclerView);
    }

    public CompaniesSection setCompanies(List<Company> companies) {
        this.companies.addAll(companies);
        adapter.notifyDataSetChanged();
        return this;
    }

    public CompaniesSection setListener(CompaniesSectionListener listener) {
        companiesListener = listener;
        return this;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    private class CompaniesAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(new ChipView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            Company company = companies.get(position);

            ChipView view = (ChipView) holder.itemView;
            view.setRadius(ScreenUtils.dp(10))
                .setTextColor(ContextCompat.getColor(getContext(), Theme.secondaryTextColor()))
                .setColorBackground(ContextCompat.getColor(getContext(), Theme.backgroundColor()))
                .setText(company.name)
                .changeLayoutParams();
        }

        @Override
        public int getItemCount() {
            return companies != null ? companies.size() : 0;
        }
    }
}