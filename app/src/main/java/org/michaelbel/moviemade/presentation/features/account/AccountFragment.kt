package org.michaelbel.moviemade.presentation.features.account

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_account.*
import kotlinx.android.synthetic.main.view_login.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Account
import org.michaelbel.moviemade.core.utils.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import java.util.*
import javax.inject.Inject

class AccountFragment: BaseFragment(), NetworkChangeReceiver.Listener, AccountContract.View {

    var accountId: Int = 0

    private var networkChangeReceiver: NetworkChangeReceiver? = null

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var repository: AccountRepository

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
                    builder.setPositiveButton(R.string.ok) { _, _ -> presenter.deleteSession() }
                    builder.show()
                    true
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App[requireActivity().application].createFragmentComponent().inject(this)
        networkChangeReceiver = NetworkChangeReceiver(this)
        requireActivity().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))
        presenter = AccountPresenter(this, repository, preferences)
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

        signupBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_SIGNUP) }
        resetBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_RESET_PASSWORD) }
        loginBtn.setOnClickListener { presenter.createRequestToken() }
        termsBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_TERMS_OF_USE) }
        privacyBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_PRIVACY_POLICY) }

        favoritesText.setOnClickListener { (requireActivity() as MainActivity).startFavorites(accountId) }
        watchlistText.setOnClickListener { (requireActivity() as MainActivity).startWatchlist(accountId) }
    }

    private fun showLogin() {
        preferences.edit().putString(KEY_SESSION_ID, "").apply()
        loginLayout.visibility = VISIBLE
        accountLayout.visibility = GONE

        Glide.with(requireContext()).load(TMDB_LOGO).thumbnail(0.1F).into(logo_image)
        username.background = null
        password.background = null
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

        presenter.getAccountDetails()
    }

    override fun startBrowserAuth(token: String) {
        Browser.openUrl(requireContext(), String.format(TMDB_AUTH_URL, token, REDIRECT_URL))
    }

    override fun sessionChanged(state: Boolean) {
        username.text?.clear()
        password.text?.clear()

        if (state) {
            // Session created.
            showAccount()
        } else {
            // Session deleted.
            showLogin()
        }
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

    override fun onNetworkChanged() {}

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
        requireActivity().unregisterReceiver(networkChangeReceiver)
    }
}