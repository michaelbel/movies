package org.michaelbel.moviemade.ui.view.section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.rest.model.Company;
import org.michaelbel.moviemade.ui.adapter.Holder;
import org.michaelbel.moviemade.ui.view.ChipView2;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

public class CompaniesSection extends FrameLayout {

    private CompaniesAdapter adapter;
    private CompaniesSectionListener companiesListener;
    private List<Company> companies = new ArrayList<>();

    public interface CompaniesSectionListener {
        void onCompaniesClick(View view, Company company);
    }

    public CompaniesSection(@NonNull Context context) {
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

    private class CompaniesAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new ChipView2(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Company company = companies.get(position);

            ChipView2 view = (ChipView2) holder.itemView;
            view.setText(company.name).changeLayoutParams();
        }

        @Override
        public int getItemCount() {
            return companies != null ? companies.size() : 0;
        }
    }
}