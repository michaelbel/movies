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
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.picasso.CircleTransformation
import org.michaelbel.data.remote.model.Movie.Companion.FAVORITE
import org.michaelbel.data.remote.model.Movie.Companion.WATCHLIST
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.GRAVATAR_URL
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_AVATAR
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_LOGIN
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_NAME
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.databinding.FragmentUserBinding
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.login.LoginFragment
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import org.michaelbel.moviemade.presentation.features.main.MainActivity.Companion.FRAGMENT_TAG
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment: BaseFragment(R.layout.fragment_user) {

    @Inject lateinit var preferences: SharedPreferences

    private val viewModel: UserModel by viewModels()
    private val binding: FragmentUserBinding by viewBinding()

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
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionId: String = preferences.getString(KEY_SESSION_ID, "") ?: ""
        if (sessionId.isEmpty()) {
            parentFragmentManager.commitNow {
                replace((requireActivity() as MainActivity).containerId, LoginFragment.newInstance(), FRAGMENT_TAG)
            }
        }

        updateData()

        binding.favoritesText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, FAVORITE)
                putExtra(EXTRA_ACCOUNT_ID, preferences.getLong(KEY_ACCOUNT_ID, 0L))
            }
        }
        binding.watchlistText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, WATCHLIST)
                putExtra(EXTRA_ACCOUNT_ID, preferences.getLong(KEY_ACCOUNT_ID, 0L))
            }
        }

        viewModel.accountDetails(preferences.getString(KEY_SESSION_ID, "") ?: "")

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.account.collect {
                    preferences.edit {
                        putLong(KEY_ACCOUNT_ID, it.id.toLong())
                        putString(KEY_ACCOUNT_LOGIN, it.username)
                        putString(KEY_ACCOUNT_NAME, it.name)
                        putString(KEY_ACCOUNT_AVATAR, it.avatar.gravatar.hash)
                    }
                    updateData()
                }
            }
            launch {
                viewModel.sessionDeleted.collect {
                    preferences.edit().putString(KEY_SESSION_ID, "").apply()
                    parentFragmentManager.commitNow {
                        replace((requireActivity() as MainActivity).containerId, LoginFragment.newInstance(), FRAGMENT_TAG)
                    }
                }
            }
            launch {
                viewModel.throwable.collect {
                    //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateData() {
        val name = preferences.getString(KEY_ACCOUNT_NAME, "")
        binding.nameText.text = name

        val login = preferences.getString(KEY_ACCOUNT_LOGIN, "")
        binding.loginText.text = login

        val avatarPath: String = String.format(Locale.US, GRAVATAR_URL, preferences.getString(KEY_ACCOUNT_AVATAR, ""))
        if (avatarPath.trim().isNotEmpty()) {
            binding.avatar.loadImage(avatarPath,
                    resize = Pair(72F.toDp(requireContext()), 72F.toDp(requireContext())),
                    placeholder = R.drawable.placeholder_circle,
                    error = R.drawable.error_circle,
                    transformation = CircleTransformation(ContextCompat.getColor(requireContext(), R.color.strokeColor), 1F.toDp(requireContext())))
        }

        val backdrop = preferences.getString(SharedPrefs.KEY_ACCOUNT_BACKDROP, "http://null") ?: "http://null"
        Picasso.get().load(backdrop).resize(requireContext().displayWidth, 220F.toDp(requireContext())).into(binding.cover, object: Callback {
            override fun onSuccess() {
                binding.blurLayout.isVisible = true
            }

            override fun onError(e: Exception?) {
                binding.blurLayout.isGone = true
            }
        })
    }

    companion object {
        fun newInstance() = UserFragment()
    }
}