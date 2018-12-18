package org.michaelbel.moviemade.ui.modules.keywords.activity;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Keyword;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordFragment;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class KeywordActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        Keyword keyword = (Keyword) getIntent().getSerializableExtra(IntentsKt.KEYWORD);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(keyword.getName());

        if (savedInstanceState == null) {
            startFragment(KeywordFragment.newInstance(keyword.getId()), R.id.fragment_view);
        }
    }
}