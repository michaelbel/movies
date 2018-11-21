package org.michaelbel.moviemade.ui.modules.account;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Account;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.utils.Browser;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.Error;
import org.michaelbel.moviemade.utils.SharedPrefsKt;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AccountFragment extends MvpAppCompatFragment implements AccountMvp, NetworkChangeListener {

    private int accountId;
    private Unbinder unbinder;
    private MainActivity activity;
    private NetworkChangeReceiver networkChangeReceiver;

    @BindView(R.id.login_layout) LinearLayoutCompat loginLayout;
    @BindView(R.id.logo_image) AppCompatImageView logoImage;
    @BindView(R.id.username_text) AppCompatEditText usernameBox;
    @BindView(R.id.password_text) AppCompatEditText passwordBox;
    @BindView(R.id.signin_btn) CardView signinBtn;

    @BindView(R.id.account_layout) LinearLayoutCompat accountLayout;
    @BindView(R.id.user_avatar) AppCompatImageView userAvatar;
    @BindView(R.id.text1) AppCompatTextView loginText;
    @BindView(R.id.text2) AppCompatTextView nameText;
    @BindView(R.id.fave_text) AppCompatTextView faveText;
    @BindView(R.id.watch_text) AppCompatTextView watchText;
    @BindView(R.id.account_view) FrameLayout accountView;

    @Inject
    SharedPreferences sharedPreferences;

    @InjectPresenter
    public AccountPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        Moviemade.getComponent().injest(this);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, parentView);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, "").isEmpty()) {
            showLogin();
        } else {
            showAccount();
        }
    }

    private void showLogin() {
        sharedPreferences.edit().putString(SharedPrefsKt.KEY_SESSION_ID, "").apply();
        loginLayout.setVisibility(View.VISIBLE);
        accountLayout.setVisibility(View.GONE);

        Glide.with(activity).load(TmdbConfigKt.TMDB_LOGO).thumbnail(0.1f).into(logoImage);
        usernameBox.setBackground(null);
        passwordBox.setBackground(null);
        DrawableUtil.INSTANCE.clearCursorDrawable(usernameBox);
        DrawableUtil.INSTANCE.clearCursorDrawable(passwordBox);
        passwordBox.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signinBtn.performClick();
                return true;
            }
            return false;
        });
    }

    private void showAccount() {
        hideKeyboard(passwordBox);
        accountLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);

        accountView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.logout);
            builder.setMessage(R.string.logout_msg);
            builder.setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> presenter.deleteSession());
            builder.show();
            return true;
        });

        faveText.setOnClickListener(v -> activity.startFave(accountId));

        loginText.setText(R.string.loading_login);
        nameText.setText(R.string.loading_name);

        presenter.getAccountDetails();
    }

    @OnClick(R.id.signin_btn)
    void signinClick(View v) {
        String name = usernameBox.getText() != null ? usernameBox.getText().toString().trim() : null;
        String pass = passwordBox.getText() != null ? passwordBox.getText().toString().trim() : null;

        if (name != null && name.length() == 0 || pass != null && pass.length() == 0) {
            Toast.makeText(activity, "Enter Username and Password", Toast.LENGTH_SHORT).show();
        } else {
            presenter.createRequestToken(name, pass);
        }
    }

    @OnClick(R.id.signup_btn)
    void signupClick(View v) {
        Browser.INSTANCE.openUrl(activity, TmdbConfigKt.TMDB_SIGNUP);
    }

    @OnClick(R.id.reset_pass)
    void resetPassClick(View v) {
        Browser.INSTANCE.openUrl(activity, TmdbConfigKt.TMDB_RESET_PASSWORD);
    }

    @OnClick(R.id.login_btn)
    void loginClick(View v) {
        presenter.createRequestToken();
    }

    @OnClick(R.id.terms_card)
    void termsClick(View v) {
        Browser.INSTANCE.openUrl(activity, TmdbConfigKt.TMDB_TERMS_OF_USE);
    }

    @OnClick(R.id.policy_card)
    void policyClick(View v) {
        Browser.INSTANCE.openUrl(activity, TmdbConfigKt.TMDB_PRIVACY_POLICY);
    }

    @Override
    public void startBrowserAuth(@NonNull String token) {
        Browser.INSTANCE.openUrl(activity, String.format(TmdbConfigKt.TMDB_AUTH_URL, token, TmdbConfigKt.REDIRECT_URL));
    }

    @Override
    public void sessionChanged(boolean state) {
        Objects.requireNonNull(usernameBox.getText()).clear();
        Objects.requireNonNull(passwordBox.getText()).clear();

        if (state) {
            // Session created.
            showAccount();
        } else {
            // Session deleted.
            showLogin();
        }
    }

    @Override
    public void setAccount(@NonNull Account account) {
        accountId = account.getId();
        sharedPreferences.edit().putInt(SharedPrefsKt.KEY_ACCOUNT_ID, accountId).apply();
        loginText.setText(account.getUsername().isEmpty() ? getString(R.string.none) : account.getUsername());
        nameText.setText(account.getName().isEmpty() ? getString(R.string.none) : account.getName());
        Glide.with(activity).load(String.format(Locale.US, TmdbConfigKt.GRAVATAR_URL, account.getAvatar().getGravatar().getHash())).thumbnail(0.1f).into(userAvatar);
    }

    @Override
    public void onNetworkChanged() {

    }

    @Override
    public void setError(@Error int error) {
        if (error == Error.ERROR_UNAUTHORIZED) {
            sharedPreferences.edit().putString(SharedPrefsKt.KEY_SESSION_ID, "").apply();
            loginLayout.setVisibility(View.VISIBLE);
            accountLayout.setVisibility(View.GONE);
        } else if (error == Error.ERROR_CONNECTION_NO_TOKEN) {
            Toast.makeText(activity, R.string.no_connection, Toast.LENGTH_SHORT).show();
        } else if (error == Error.ERROR_NO_CONNECTION) {
            Toast.makeText(activity, "Internet is lost", Toast.LENGTH_SHORT).show();
        } else if (error == Error.ERROR_AUTH_WITH_LOGIN) {
            Toast.makeText(activity, "Invalid Login or Password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.onDestroy();
    }

    private void hideKeyboard(View view) {
        if (view == null) {
            return;
        }

        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!(imm != null && imm.isActive())) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}