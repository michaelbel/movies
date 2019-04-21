package org.michaelbel.moviemade.presentation.features.user

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.readystatesoftware.chuck.internal.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_account.*
import org.michaelbel.data.remote.model.MoviesResponse.Companion.FAVORITE
import org.michaelbel.data.remote.model.MoviesResponse.Companion.WATCHLIST
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.GRAVATAR_URL
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_BACKDROP
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.login.LoginFragment
import java.util.*
import javax.inject.Inject

class UserFragment: BaseFragment() {

    companion object {
        internal fun newInstance() = UserFragment()
    }

    private var accountId: Int = 0
    private var sessionId: String = ""

    private lateinit var viewModel: UserModel

    @Inject
    lateinit var factory: UserFactory

    @Inject
    lateinit var preferences: SharedPreferences

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
                        viewModel.deleteSession(sessionId)
                    }
                    builder.show()
                    return@setOnMenuItemClickListener true
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
        sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(UserModel::class.java)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAccount()

        favoritesText.setOnClickListener {
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, FAVORITE)
            intent.putExtra(EXTRA_ACCOUNT_ID, accountId)
            startActivity(intent)
        }
        watchlistText.setOnClickListener {
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, WATCHLIST)
            intent.putExtra(EXTRA_ACCOUNT_ID, accountId)
            startActivity(intent)
        }

        viewModel.sessionDeleted.observe(viewLifecycleOwner, Observer {
            showLogin()
        })
        viewModel.account.observe(viewLifecycleOwner, Observer {
            accountId = it.id
            preferences.edit().putInt(KEY_ACCOUNT_ID, accountId).apply()
            loginText.text = if (it.username.isEmpty()) getString(R.string.none) else it.username
            nameText.text = if (it.name.isEmpty()) getString(R.string.none) else it.name
            Glide.with(requireContext())
                    .load(String.format(Locale.US, GRAVATAR_URL, it.avatar.gravatar.hash))
                    .thumbnail(0.1F).into(avatar)
        })
    }

    override fun onScrollToTop() {
        super.onScrollToTop()
    }

    private fun showLogin() {
        preferences.edit().putString(KEY_SESSION_ID, "").apply()

        requireFragmentManager().transaction {
            replace((requireActivity() as MainActivity).container.id, LoginFragment.newInstance())
        }
    }

    private fun showAccount() {
        loginText.setText(R.string.loading_login)
        nameText.setText(R.string.loading_name)

        val backdrop = preferences.getString(KEY_ACCOUNT_BACKDROP, "") ?: ""
        if (backdrop.isNotEmpty()) {
            Glide.with(requireContext()).load(backdrop).thumbnail(0.1F).into(cover)
        }

        viewModel.accountDetails(sessionId)
    }
}