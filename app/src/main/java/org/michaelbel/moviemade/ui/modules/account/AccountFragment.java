package org.michaelbel.moviemade.ui.modules.account;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Account;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.utils.Browser;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.Error;
import org.michaelbel.moviemade.utils.SharedPrefsKt;
import org.michaelbel.moviemade.utils.SpannableUtil;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends BaseFragment implements NetworkChangeListener, AccountContract.View {

    private int accountId;
    private MainActivity activity;
    private NetworkChangeReceiver networkChangeReceiver;

    @BindView(R.id.login_layout) LinearLayoutCompat loginLayout;
    @BindView(R.id.logo_image) AppCompatImageView logoImage;
    @BindView(R.id.username_field) AppCompatEditText usernameField;
    @BindView(R.id.password_field) AppCompatEditText passwordField;
    @BindView(R.id.signin_btn) CardView signInBtn;

    @BindView(R.id.account_layout) FrameLayout accountLayout;
    @BindView(R.id.user_avatar) CircleImageView userAvatar;
    @BindView(R.id.login_text) AppCompatTextView loginText;
    @BindView(R.id.name_text) AppCompatTextView nameText;
    @BindView(R.id.backdrop_image) AppCompatImageView backdropImage;

    @Inject AccountRepository repository;
    @Inject SharedPreferences sharedPreferences;

    private AccountContract.Presenter presenter;

    public int getAccountId() {
        return accountId;
    }

    public AccountContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(R.string.logout)
            .setIcon(R.drawable.ic_logout)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.logout);
                builder.setMessage(R.string.msg_logout);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> presenter.deleteSession());
                builder.show();
                return true;
            });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        activity = (MainActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        Moviemade.get(activity).getFragmentComponent().inject(this);
        presenter = new AccountPresenter(this, repository, sharedPreferences);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, "").isEmpty()) {
            showLogin();
        } else {
            showAccount();
        }

        signInBtn.setOnClickListener(v -> {
            String name = usernameField.getText() != null ? usernameField.getText().toString().trim() : null;
            String pass = passwordField.getText() != null ? passwordField.getText().toString().trim() : null;

            if (name != null && name.length() == 0 || pass != null && pass.length() == 0) {
                Toast.makeText(activity, SpannableUtil.replaceTags(getString(R.string.msg_enter_data)), Toast.LENGTH_SHORT).show();
            } else {
                presenter.createRequestToken(name, pass);
            }
        });
    }

    private void showLogin() {
        sharedPreferences.edit().putString(SharedPrefsKt.KEY_SESSION_ID, "").apply();
        loginLayout.setVisibility(View.VISIBLE);
        accountLayout.setVisibility(View.GONE);

        Glide.with(activity).load(TmdbConfigKt.TMDB_LOGO).thumbnail(0.1f).into(logoImage);
        usernameField.setBackground(null);
        passwordField.setBackground(null);
        DrawableUtil.INSTANCE.clearCursorDrawable(usernameField);
        DrawableUtil.INSTANCE.clearCursorDrawable(passwordField);
        passwordField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signInBtn.performClick();
                return true;
            }
            return false;
        });
    }

    private void showAccount() {
        hideKeyboard(passwordField);
        accountLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);

        loginText.setText(R.string.loading_login);
        nameText.setText(R.string.loading_name);

        if (!sharedPreferences.getString(SharedPrefsKt.KEY_ACCOUNT_BACKDROP, "").isEmpty()) {
            Glide.with(activity).load(sharedPreferences.getString(SharedPrefsKt.KEY_ACCOUNT_BACKDROP, "")).thumbnail(0.1f).into(backdropImage);
        }

        presenter.getAccountDetails();
    }

    // FIXME remove all @OnClick methods
    @OnClick(R.id.signup_btn)
    void signUpClick(View v) {
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

    @OnClick(R.id.favorites_text)
    void favoritesClick(View v) {
        activity.startFavorites(accountId);
    }

    @OnClick(R.id.watchlist_text)
    void watchlistClick(View v) {
        activity.startWatchlist(accountId);
    }

    @Override
    public void startBrowserAuth(@NonNull String token) {
        Browser.INSTANCE.openUrl(activity, String.format(TmdbConfigKt.TMDB_AUTH_URL, token, TmdbConfigKt.REDIRECT_URL));
    }

    @Override
    public void sessionChanged(boolean state) {
        Objects.requireNonNull(usernameField.getText()).clear();
        Objects.requireNonNull(passwordField.getText()).clear();

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
    public void onNetworkChanged() {}

    @Override
    public void setError(@Error int error) {
        switch (error) {
            case Error.ERROR_UNAUTHORIZED:
                sharedPreferences.edit().putString(SharedPrefsKt.KEY_SESSION_ID, "").apply();
                loginLayout.setVisibility(View.VISIBLE);
                accountLayout.setVisibility(View.GONE);
                break;
            case Error.ERROR_CONNECTION_NO_TOKEN:
                Toast.makeText(activity, R.string.error_empty_token, Toast.LENGTH_SHORT).show();
                break;
            case Error.ERROR_NO_CONNECTION:
                Toast.makeText(activity, R.string.error_no_connection, Toast.LENGTH_SHORT).show();
                break;
            case Error.ERROR_AUTH_WITH_LOGIN:
                Toast.makeText(activity, SpannableUtil.replaceTags(getString(R.string.error_invalid_data)), Toast.LENGTH_SHORT).show();
                break;
            case Error.ERROR_NOT_FOUND:
                Toast.makeText(activity, SpannableUtil.replaceTags(getString(R.string.error_not_found)), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
    }

    private void hideKeyboard(View view) {
        if (view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!(imm != null && imm.isActive())) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}