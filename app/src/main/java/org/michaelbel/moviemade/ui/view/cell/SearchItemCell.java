package org.michaelbel.moviemade.ui.view.cell;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;

@SuppressWarnings("all")
public class SearchItemCell extends TextDetailCell {

    private DeleteIconClick deleteIconClick;

    public interface DeleteIconClick {
        void onIconClick(View view);
    }

    public SearchItemCell(Context context) {
        super(context);

        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 10, 16 + 64, 0));
        valueText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 35, 16 + 64, 0));

        FrameLayout iconLayout = new FrameLayout(context);
        iconLayout.setLayoutParams(LayoutHelper.makeFrame(56, 64, Gravity.END));
        iconLayout.setOnClickListener(v -> {
            if (deleteIconClick != null) {
                deleteIconClick.onIconClick(v);
            }
        });
        addView(iconLayout);

        ImageView deleteIcon = new ImageView(context);
        deleteIcon.setImageResource(R.drawable.ic_delete);
        deleteIcon.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        iconLayout.addView(deleteIcon);
    }

    public SearchItemCell setOnIconClick(DeleteIconClick listener) {
        deleteIconClick = listener;
        return this;
    }
}