package org.michaelbel.moviemade.presentation.features.account

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_account.*
import kotlinx.android.synthetic.main.view_login.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.Error
import org.michaelbel.moviemade.core.TmdbConfig.GRAVATAR_URL
import org.michaelbel.moviemade.core.TmdbConfig.REDIRECT_URL
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_AUTH_URL
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_LOGO
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_PRIVACY_POLICY
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_REGISTER
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_RESET_PASSWORD
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_TERMS_OF_USE
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.customtabs.Browser
import org.michaelbel.moviemade.core.entity.Account
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.FAVORITE
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.WATCHLIST
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_BACKDROP
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_DATE_AUTHORISED
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_TOKEN
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class AccountFragment: BaseFragment(), AccountContract.View {

    var accountId: Int = 0

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var presenter: AccountContract.Presenter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(R.string.logout)
                .setIcon(R.drawable.ic_logout)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setOnMenuItemClickListener {
                    val builder = AlertDialog.Builder(activity!!)
                    builder.setTitle(R.string.logout)
                    builder.setMessage(R.string.msg_logout)
                    builder.setNegativeButton(R.string.cancel, null)
                    builder.setPositiveButton(R.string.ok) { _, _ ->
                        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
                        presenter.deleteSession(sessionId)
                    }
                    builder.show()
                    true
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        if (sessionId.isEmpty()) {
            showLogin()
        } else {
            showAccount()
        }

        signinBtn.setOnClickListener {
            val name = username.text.toString().trim()
            val pass = password.text.toString().trim()

            if (name.isEmpty() || pass.isEmpty()) {
                Toast.makeText(activity, R.string.msg_enter_data, Toast.LENGTH_SHORT).show()
            } else {
                presenter.createRequestToken(name, pass)
            }
        }

        signupBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_REGISTER) }
        resetBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_RESET_PASSWORD) }
        loginBtn.setOnClickListener { presenter.createRequestToken() }
        termsBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_TERMS_OF_USE) }
        privacyBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_PRIVACY_POLICY) }

        favoritesText.setOnClickListener {
            (requireActivity() as MainActivity).startMovies(FAVORITE, accountId)
        }
        watchlistText.setOnClickListener {
            (requireActivity() as MainActivity).startMovies(WATCHLIST, accountId)
        }
    }

    private fun showLogin() {
        preferences.edit().putString(KEY_SESSION_ID, "").apply()
        loginLayout.visibility = VISIBLE
        accountLayout.visibility = GONE

        Glide.with(requireContext()).load(TMDB_LOGO).thumbnail(0.1F).into(logoImage)
        ViewUtil.clearCursorDrawable(username)
        ViewUtil.clearCursorDrawable(password)
        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signinBtn.performClick()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun showAccount() {
        hideKeyboard(password)
        accountLayout.visibility = VISIBLE
        loginLayout.visibility = GONE

        loginText.setText(R.string.loading_login)
        nameText.setText(R.string.loading_name)

        val backdrop = preferences.getString(KEY_ACCOUNT_BACKDROP, "") ?: ""
        if (backdrop.isNotEmpty()) {
            Glide.with(requireContext()).load(backdrop).thumbnail(0.1F).into(cover)
        }

        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        presenter.getAccountDetails(sessionId)
    }

    override fun startBrowserAuth(token: String, date: String) {
        preferences.edit {
            putString(KEY_TOKEN, token)
            putString(KEY_DATE_AUTHORISED, date)
        }

        Browser.openUrl(requireContext(), String.format(TMDB_AUTH_URL, token, REDIRECT_URL))
    }

    override fun sessionCreated(sessionId: String) {
        username.text?.clear()
        password.text?.clear()

        preferences.edit().putString(KEY_SESSION_ID, sessionId).apply()
        showAccount()
    }

    override fun sessionDeleted() {
        username.text?.clear()
        password.text?.clear()
        showLogin()
    }

    override fun setAccount(account: Account) {
        accountId = account.id
        preferences.edit().putInt(KEY_ACCOUNT_ID, accountId).apply()
        loginText.text = if (account.username.isEmpty()) getString(R.string.none) else account.username
        nameText.text = if (account.name.isEmpty()) getString(R.string.none) else account.name
        Glide.with(requireContext())
                .load(String.format(Locale.US, GRAVATAR_URL, account.avatar.gravatar.hash))
                .thumbnail(0.1F).into(avatar)
    }

    override fun setError(throwable: Throwable) {
        val code = (throwable as HttpException).code()
        if (code == 401) {
            preferences.edit().putString(KEY_SESSION_ID, "").apply()
            loginLayout.visibility = VISIBLE
            privacyBtn.visibility = GONE
        } else if (code == 404) {
            Toast.makeText(requireContext(), R.string.error_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun setError(@Error error: Int) {
        when (error) {
            Error.ERROR_UNAUTHORIZED -> {
                preferences.edit().putString(KEY_SESSION_ID, "").apply()
                loginLayout.visibility = VISIBLE
                privacyBtn.visibility = GONE
            }
            Error.ERROR_CONNECTION_NO_TOKEN ->
                Toast.makeText(requireContext(), R.string.error_empty_token, Toast.LENGTH_SHORT).show()
            Error.ERR_NO_CONNECTION ->
                Toast.makeText(requireContext(), R.string.error_no_connection, Toast.LENGTH_SHORT).show()
            Error.ERROR_AUTH_WITH_LOGIN ->
                Toast.makeText(requireContext(), R.string.error_invalid_data, Toast.LENGTH_SHORT).show()
            Error.ERROR_NOT_FOUND ->
                Toast.makeText(requireContext(), R.string.error_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun saveToken(token: String, date: String) {
        preferences.edit {
            putString(KEY_TOKEN, token)
            putString(KEY_DATE_AUTHORISED, date)
        }
    }

    private fun hideKeyboard(view: View?) {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive.not()) {
            return
        }
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}