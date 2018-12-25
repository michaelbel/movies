package org.michaelbel.moviemade.ui.modules.movie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.widgets.bottomsheet.BottomSheetDialogFragment;
import org.michaelbel.moviemade.utils.IntentsKt;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BackdropDialog extends BottomSheetDialogFragment {

    private String path;
    private Unbinder unbinder;

    @Inject SharedPreferences sharedPreferences;

    @BindView(R.id.set_btn) AppCompatTextView setBtn;
    @BindView(R.id.cancel_btn) AppCompatTextView cancelBtn;

    static BackdropDialog newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(IntentsKt.BACKDROP, path);
        BackdropDialog fragment = new BackdropDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetTheme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Moviemade.get(getActivity()).getFragmentComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_backdrop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            path = args.getString(IntentsKt.BACKDROP);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.set_btn)
    public void setBtnClick(View v) {
        sharedPreferences.edit().putString(SharedPrefsKt.KEY_ACCOUNT_BACKDROP, path).apply();
        Toast.makeText(getActivity(), R.string.msg_image_applied, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void cancelBtnClick(View v) {
        dismiss();
    }
}