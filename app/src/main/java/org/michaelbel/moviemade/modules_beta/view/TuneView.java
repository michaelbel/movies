package org.michaelbel.moviemade.modules_beta.view;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.material.widget2.ViewPagerAdapter;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.modules_beta.view.cell.RadioCell;

/**
 * Date: Fri, Mar 9 2018
 * Time: 20:22 MSK
 *
 * @author Michael Bel
 */

public class TuneView extends FrameLayout {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private SortLayout sortLayout;
    private DirectLayout directLayout;

    public Button doneButton;
    public Button cancelButton;

    public TuneView(@NonNull Context context) {
        super(context);

        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(layout);

        sortLayout = new SortLayout(context);
        sortLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        directLayout = new DirectLayout(context);
        directLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        ViewPagerAdapter adapter = new ViewPagerAdapter(context);
        adapter.addLayout(sortLayout, "Sort");
        adapter.addLayout(directLayout, "Direct");

        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 48 * 5));
        viewPager.setAdapter(adapter);

        tabLayout = new TabLayout(context);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.primaryColor()));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context, Theme.selectedTabColor()));
        tabLayout.setTabTextColors(ContextCompat.getColor(context, R.color.secondaryText), ContextCompat.getColor(context, Theme.selectedTabColor()));
        tabLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 48));
        layout.addView(tabLayout);

        layout.addView(viewPager);

        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, 48, Gravity.END, 0, 0, 16, 0));
        layout.addView(buttonsLayout);

        cancelButton = new Button(context);
        cancelButton.setText("Cancel");
        buttonsLayout.addView(cancelButton);

        doneButton = new Button(context);
        doneButton.setText("Done");
        buttonsLayout.addView(doneButton);
    }

    private class SortLayout extends LinearLayout {

        private RadioCell defaultRadio;
        private RadioCell titleRadio;
        private RadioCell releaseDateRadio;
        private RadioCell popularityRadio;
        private RadioCell voteAverageRadio;

        public SortLayout(@NonNull Context context) {
            super(context);

            setOrientation(VERTICAL);

            defaultRadio = new RadioCell(context);
            defaultRadio.setText("Default");
            defaultRadio.setDivider(true);
            defaultRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addView(defaultRadio);

            titleRadio = new RadioCell(context);
            titleRadio.setText("Title");
            titleRadio.setDivider(true);
            titleRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addView(titleRadio);

            releaseDateRadio = new RadioCell(context);
            releaseDateRadio.setText("Release Date");
            releaseDateRadio.setDivider(true);
            releaseDateRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addView(releaseDateRadio);

            popularityRadio = new RadioCell(context);
            popularityRadio.setText("Popularity");
            popularityRadio.setDivider(true);
            popularityRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addView(popularityRadio);

            voteAverageRadio = new RadioCell(context);
            voteAverageRadio.setText("Vote Average");
            voteAverageRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addView(voteAverageRadio);
        }
    }

    private class DirectLayout extends LinearLayout {

        private RadioCell ascendingRadio;
        private RadioCell descendingRadio;

        public DirectLayout(@NonNull Context context) {
            super(context);

            setOrientation(VERTICAL);

            ascendingRadio = new RadioCell(context);
            ascendingRadio.setText("Ascending");
            ascendingRadio.setDivider(true);
            ascendingRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ascendingRadio.isRadioChecked()) {
                        ascendingRadio.setRadioChecked(true);
                        descendingRadio.setRadioChecked(false);
                    }
                }
            });
            addView(ascendingRadio);

            descendingRadio = new RadioCell(context);
            descendingRadio.setText("Descending");
            descendingRadio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!descendingRadio.isRadioChecked()) {
                        descendingRadio.setRadioChecked(true);
                        ascendingRadio.setRadioChecked(false);
                    }
                }
            });
            addView(descendingRadio);
        }
    }
}