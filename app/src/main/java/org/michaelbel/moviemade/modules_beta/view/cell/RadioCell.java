package org.michaelbel.moviemade.modules_beta.view.cell;

import android.content.Context;
import android.view.Gravity;

import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.extensions.DeviceUtil;

import androidx.appcompat.widget.AppCompatRadioButton;

/**
 * Date: Вт, Март 13 2018
 * Time: 00:51 MSK
 *
 * @author Michael Bel
 */

public class RadioCell extends TextCell {

    private AppCompatRadioButton radioButton;

    public RadioCell(Context context) {
        super(context);

        setHeight(DeviceUtil.INSTANCE.dp(context,48));

        radioButton = new AppCompatRadioButton(context);
        radioButton.setClickable(false);
        radioButton.setFocusable(false);
        radioButton.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(radioButton);

        //textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 56, 0, 16, 0));
    }

    public void setRadioChecked(boolean checked) {
        radioButton.setChecked(checked);
    }

    public boolean isRadioChecked() {
        return radioButton.isChecked();
    }
}