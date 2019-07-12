package org.michaelbel.moviemade.presentation.features.user

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import org.michaelbel.data.Movie.Companion.FAVORITE
import org.michaelbel.data.Movie.Companion.WATCHLIST
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.GRAVATAR_URL
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_AVATAR
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_LOGIN
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_NAME
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.startActivity
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.login.LoginFragment
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import org.michaelbel.moviemade.presentation.features.main.MainActivity.Companion.FRAGMENT_TAG
import java.util.*
import javax.inject.Inject

class UserFragment: BaseFragment() {

    companion object {
        internal fun newInstance() = UserFragment()
    }

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
                        viewModel.deleteSession(preferences.getString(KEY_SESSION_ID, "") ?: "")
                    }
                    builder.show()
                    return@setOnMenuItemClickListener true
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(UserModel::class.java)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        App.d("UserFragment, sessionId = $sessionId")
        if (sessionId.isEmpty()) {
            requireFragmentManager()
                    .beginTransaction()
                    .replace((requireActivity() as MainActivity).container.id, LoginFragment.newInstance(), FRAGMENT_TAG)
                    .commit()
        }

        updateData()

        favoritesText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, FAVORITE)
                putExtra(EXTRA_ACCOUNT_ID, preferences.getInt(KEY_ACCOUNT_ID, 0))
            }
        }
        watchlistText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, WATCHLIST)
                putExtra(EXTRA_ACCOUNT_ID, preferences.getInt(KEY_ACCOUNT_ID, 0))
            }
        }

        viewModel.accountDetails(preferences.getString(KEY_SESSION_ID, "") ?: "")
        viewModel.account.observe(viewLifecycleOwner, Observer {
            App.d("${this.javaClass.simpleName}:: Account observe: $it")

            preferences.edit {
                putInt(KEY_ACCOUNT_ID, it.id)
                putString(KEY_ACCOUNT_LOGIN, it.username)
                putString(KEY_ACCOUNT_NAME, it.name)
                putString(KEY_ACCOUNT_AVATAR, it.avatar.gravatar.hash)
            }

            updateData()
        })
        viewModel.sessionDeleted.observe(viewLifecycleOwner, Observer {
            App.d("\"${this.javaClass.simpleName}:: Session deleted")
            it.getContentIfNotHandled().let {
                preferences.edit().putString(KEY_SESSION_ID, "").apply()

                requireFragmentManager()
                        .beginTransaction()
                        .replace((requireActivity() as MainActivity).container.id, LoginFragment.newInstance(), FRAGMENT_TAG)
                        .commit()
            }
        })
        viewModel.throwable.observe(viewLifecycleOwner, Observer {
            //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateData() {
        val name = preferences.getString(KEY_ACCOUNT_NAME, "") ?: ""
        nameText.text = name
        App.d("\"${this.javaClass.simpleName}:: name = $name")

        val login = preferences.getString(KEY_ACCOUNT_LOGIN, "") ?: ""
        App.d("\"${this.javaClass.simpleName}:: login = $login")
        loginText.text = login

        val path = preferences.getString(KEY_ACCOUNT_AVATAR, "http://null") ?: "http://null"
        App.d("\"${this.javaClass.simpleName}:: avatar = $path")
        Glide.with(requireContext()).load(String.format(Locale.US, GRAVATAR_URL, path)).thumbnail(0.1F).into(avatar)

        val backdrop = preferences.getString(SharedPrefs.KEY_ACCOUNT_BACKDROP, "") ?: "http://null"
        App.d("\"${this.javaClass.simpleName}:: backdrop = $backdrop")
        Glide.with(requireContext()).load(backdrop).thumbnail(0.1F).into(cover)
    }
}