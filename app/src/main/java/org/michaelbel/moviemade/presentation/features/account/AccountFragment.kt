package org.michaelbel.moviemade.presentation.features.account

import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
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
        if (preferences.getString(KEY_SESSION_ID, "")!!.isEmpty()) {
            showLogin()
        } else {
            showAccount()
        }

        signin_btn.setOnClickListener {
            val name = if (username_field.text != null) username_field.text!!.toString().trim { it <= ' ' } else null
            val pass = if (password_field.text != null) password_field.text!!.toString().trim { it <= ' ' } else null

            if (name != null && name.length == 0 || pass != null && pass.length == 0) {
                Toast.makeText(activity, SpannableUtil.replaceTags(getString(R.string.msg_enter_data)), Toast.LENGTH_SHORT).show()
            } else {
                presenter.createRequestToken(name!!, pass!!)
            }
        }

        signup_btn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_SIGNUP) }
        reset_pass.setOnClickListener { Browser.openUrl(requireContext(), TMDB_RESET_PASSWORD) }
        login_btn.setOnClickListener { presenter.createRequestToken() }
        terms_btn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_TERMS_OF_USE) }
        privacy_btn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_PRIVACY_POLICY) }

        favorites_text.setOnClickListener { (requireActivity() as MainActivity).startFavorites(accountId) }
        watchlist_text.setOnClickListener { (requireActivity() as MainActivity).startWatchlist(accountId) }
    }

    private fun showLogin() {
        preferences.edit().putString(KEY_SESSION_ID, "").apply()
        login_layout.visibility = VISIBLE
        account_layout.visibility = GONE

        Glide.with(activity!!).load(TMDB_LOGO).thumbnail(0.1f).into(logo_image)
        username_field.background = null
        password_field.background = null
        ViewUtil.clearCursorDrawable(username_field)
        ViewUtil.clearCursorDrawable(password_field)
        password_field.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signin_btn.performClick()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun showAccount() {
        hideKeyboard(password_field)
        account_layout.visibility = VISIBLE
        login_layout.visibility = GONE

        login_text.setText(R.string.loading_login)
        name_text.setText(R.string.loading_name)

        if (!preferences.getString(KEY_ACCOUNT_BACKDROP, "")!!.isEmpty()) {
            Glide.with(activity!!).load(preferences.getString(KEY_ACCOUNT_BACKDROP, "")).thumbnail(0.1F).into(backdrop_image)
        }

        presenter.getAccountDetails()
    }

    override fun startBrowserAuth(token: String) {
        Browser.openUrl(requireContext(), String.format(TMDB_AUTH_URL, token, REDIRECT_URL))
    }

    override fun sessionChanged(state: Boolean) {
        Objects.requireNonNull<Editable>(username_field.text).clear()
        Objects.requireNonNull<Editable>(password_field.text).clear()

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
        login_text.text = if (account.username.isEmpty()) getString(R.string.none) else account.username
        name_text.text = if (account.name.isEmpty()) getString(R.string.none) else account.name
        Glide.with(activity!!).load(String.format(Locale.US, GRAVATAR_URL, account.avatar.gravatar.hash)).thumbnail(0.1F).into(user_avatar)
    }

    override fun onNetworkChanged() {}

    override fun setError(@Error error: Int) {
        when (error) {
            Error.ERROR_UNAUTHORIZED -> {
                preferences.edit().putString(KEY_SESSION_ID, "").apply()
                login_layout.visibility = VISIBLE
                privacy_btn.visibility = GONE
            }
            Error.ERROR_CONNECTION_NO_TOKEN -> Toast.makeText(activity, R.string.error_empty_token, Toast.LENGTH_SHORT).show()
            Error.ERR_NO_CONNECTION -> Toast.makeText(activity, R.string.error_no_connection, Toast.LENGTH_SHORT).show()
            Error.ERROR_AUTH_WITH_LOGIN -> Toast.makeText(activity, SpannableUtil.replaceTags(getString(R.string.error_invalid_data)), Toast.LENGTH_SHORT).show()
            Error.ERROR_NOT_FOUND -> Toast.makeText(activity, SpannableUtil.replaceTags(getString(R.string.error_not_found)), Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }

        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!(imm.isActive)) {
            return
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
        requireActivity().unregisterReceiver(networkChangeReceiver)
    }
}