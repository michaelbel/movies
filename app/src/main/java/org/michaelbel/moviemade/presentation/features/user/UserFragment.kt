package org.michaelbel.moviemade.presentation.features.user

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import org.michaelbel.core.picasso.CircleTransformation
import org.michaelbel.data.remote.model.Movie.Companion.FAVORITE
import org.michaelbel.data.remote.model.Movie.Companion.WATCHLIST
import org.michaelbel.domain.UsersRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.GRAVATAR_URL
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_AVATAR
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_LOGIN
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_NAME
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.ktx.*
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

class UserFragment: BaseFragment(R.layout.fragment_user) {

    companion object {
        fun newInstance() = UserFragment()
    }

    @Inject lateinit var repository: UsersRepository
    @Inject lateinit var preferences: SharedPreferences

    private val viewModel: UserModel by lazy { getViewModel { UserModel(repository) } }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(R.string.logout)
                .setIcon(R.drawable.ic_logout)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setOnMenuItemClickListener {
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle(R.string.logout)
                        setMessage(R.string.msg_logout)
                        setNegativeButton(R.string.cancel, null)
                        setPositiveButton(R.string.ok) { _, _ ->
                            viewModel.deleteSession(preferences.getString(KEY_SESSION_ID, "") ?: "")
                        }
                        show()
                    }
                    return@setOnMenuItemClickListener true
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionId: String = preferences.getString(KEY_SESSION_ID, "") ?: ""
        if (sessionId.isEmpty()) {
            requireFragmentManager().commitNow {
                replace((requireActivity() as MainActivity).container.id, LoginFragment.newInstance(), FRAGMENT_TAG)
            }
        }

        updateData()

        favoritesText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, FAVORITE)
                putExtra(EXTRA_ACCOUNT_ID, preferences.getLong(KEY_ACCOUNT_ID, 0L))
            }
        }
        watchlistText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, WATCHLIST)
                putExtra(EXTRA_ACCOUNT_ID, preferences.getLong(KEY_ACCOUNT_ID, 0L))
            }
        }

        viewModel.accountDetails(preferences.getString(KEY_SESSION_ID, "") ?: "")
        viewModel.account.reObserve(viewLifecycleOwner, Observer {
            preferences.edit {
                putLong(KEY_ACCOUNT_ID, it.id.toLong())
                putString(KEY_ACCOUNT_LOGIN, it.username)
                putString(KEY_ACCOUNT_NAME, it.name)
                putString(KEY_ACCOUNT_AVATAR, it.avatar.gravatar.hash)
            }

            updateData()
        })
        viewModel.sessionDeleted.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let {
                preferences.edit().putString(KEY_SESSION_ID, "").apply()

                requireFragmentManager().commitNow {
                    replace((requireActivity() as MainActivity).container.id, LoginFragment.newInstance(), FRAGMENT_TAG)
                }
            }
        })
        viewModel.throwable.reObserve(viewLifecycleOwner, Observer {
            //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateData() {
        val name = preferences.getString(KEY_ACCOUNT_NAME, "")
        nameText.text = name

        val login = preferences.getString(KEY_ACCOUNT_LOGIN, "")
        loginText.text = login

        val avatarPath: String = String.format(Locale.US, GRAVATAR_URL, preferences.getString(KEY_ACCOUNT_AVATAR, ""))
        if (avatarPath.trim().isNotEmpty()) {
            avatar.loadImage(avatarPath,
                    resize = Pair(72F.toDp(requireContext()), 72F.toDp(requireContext())),
                    placeholder = R.drawable.placeholder_circle,
                    error = R.drawable.error_circle,
                    transformation = CircleTransformation(ContextCompat.getColor(requireContext(), R.color.strokeColor), 1F.toDp(requireContext())))
        }

        val backdrop = preferences.getString(SharedPrefs.KEY_ACCOUNT_BACKDROP, "http://null") ?: "http://null"
        Picasso.get().load(backdrop).resize(requireContext().displayWidth, 220F.toDp(requireContext())).into(cover, object: Callback {
            override fun onSuccess() {
                blurLayout?.visible()
            }

            override fun onError(e: Exception?) {
                blurLayout?.gone()
            }
        })
    }
}