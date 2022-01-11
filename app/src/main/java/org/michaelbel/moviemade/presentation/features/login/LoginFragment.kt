package org.michaelbel.moviemade.presentation.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.Browser
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.REDIRECT_URL
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_AUTH_URL
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_LOGO
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_PRIVACY_POLICY
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_REGISTER
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_RESET_PASSWORD
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_TERMS_OF_USE
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_AVATAR
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_LOGIN
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_NAME
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_TOKEN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_AUTH_WITH_LOGIN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_CONNECTION_NO_TOKEN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_NOT_FOUND
import org.michaelbel.moviemade.core.state.ErrorState.ERR_NO_CONNECTION
import org.michaelbel.moviemade.core.state.ErrorState.ERR_UNAUTHORIZED
import org.michaelbel.moviemade.core.text.SpannableUtil
import org.michaelbel.moviemade.databinding.FragmentLoginBinding
import org.michaelbel.moviemade.ktx.hideKeyboard
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.loadImage
import org.michaelbel.moviemade.ktx.toast
import org.michaelbel.moviemade.presentation.BaseFragment
import retrofit2.HttpException

@AndroidEntryPoint
class LoginFragment: BaseFragment(R.layout.fragment_login) {

    private val viewModel: LoginModel by viewModels()
    private val binding: FragmentLoginBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoImage.loadImage(TMDB_LOGO, resizeDimen = Pair(R.dimen.logo_image_size, R.dimen.logo_image_size))
        binding.password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_DONE) {
                binding.signInBtn.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.signInBtn.setOnClickListener {
            val name = binding.username.text?.trim()
            val pass = binding.password.text?.trim()

            if (name.isNullOrEmpty() || pass.isNullOrEmpty()) {
                requireContext().toast(SpannableUtil.replaceTags(getString(R.string.msg_enter_data)).toString())
            } else {
                viewModel.createRequestToken(name.toString(), pass.toString())
            }
        }
        binding.signUpBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_REGISTER) }
        binding.resetBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_RESET_PASSWORD) }
        binding.loginBtn.setOnClickListener { viewModel.createRequestToken() }
        binding.termsBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_TERMS_OF_USE) }
        binding.privacyBtn.setOnClickListener { Browser.openUrl(requireContext(), TMDB_PRIVACY_POLICY) }

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.sessionCreated.collect {
                    binding.username.text?.clear()
                    binding.password.text?.clear()

                    viewModel.preferences.edit().putString(SharedPrefs.KEY_SESSION_ID, it).apply()
                    binding.password.hideKeyboard()
                }
            }
            launch {
                viewModel.error.collect {
                    when (it) {
                        ERR_UNAUTHORIZED -> viewModel.preferences.edit().putString(SharedPrefs.KEY_SESSION_ID, "").apply()
                        ERR_CONNECTION_NO_TOKEN -> Toast.makeText(requireContext(), R.string.error_empty_token, Toast.LENGTH_SHORT).show()
                        ERR_NO_CONNECTION -> Toast.makeText(requireContext(), R.string.error_no_connection, Toast.LENGTH_SHORT).show()
                        ERR_AUTH_WITH_LOGIN -> Toast.makeText(requireContext(), R.string.error_invalid_data, Toast.LENGTH_SHORT).show()
                        ERR_NOT_FOUND -> Toast.makeText(requireContext(), R.string.error_not_found, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            launch {
                viewModel.throwable.collect {
                    val code = (it as HttpException).code()
                    if (code == 401) {
                        viewModel.preferences.edit().putString(SharedPrefs.KEY_SESSION_ID, "").apply()
                    } else if (code == 404) {
                        toast(R.string.error_not_found)
                    }
                }
            }
            launch {
                viewModel.account.collect {
                    viewModel.preferences.edit {
                        putLong(KEY_ACCOUNT_ID, it.id.toLong())
                        putString(KEY_ACCOUNT_LOGIN, it.username)
                        putString(KEY_ACCOUNT_NAME, it.name)
                        putString(KEY_ACCOUNT_AVATAR, it.avatar.gravatar.hash)
                    }
                }
            }
            launch {
                viewModel.browserAuth.collect {
                    Browser.openUrl(requireContext(), String.format(TMDB_AUTH_URL, it, REDIRECT_URL))
                }
            }
        }
    }

    override fun onNewIntent(action: String?, data: String?) {
        if (Intent.ACTION_VIEW == action && data != null) {
            viewModel.createSessionId(viewModel.preferences.getString(KEY_TOKEN, "") ?: "")
        }
    }

    companion object {
        internal fun newInstance() = LoginFragment()
    }
}