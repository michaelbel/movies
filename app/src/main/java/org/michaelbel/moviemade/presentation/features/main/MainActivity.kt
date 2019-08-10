package org.michaelbel.moviemade.presentation.features.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.transaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.michaelbel.data.remote.model.Movie.Companion.NOW_PLAYING
import org.michaelbel.data.remote.model.Movie.Companion.TOP_RATED
import org.michaelbel.data.remote.model.Movie.Companion.UPCOMING
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.startActivity
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.common.base.BaseActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.login.LoginFragment
import org.michaelbel.moviemade.presentation.features.search.SearchActivity
import org.michaelbel.moviemade.presentation.features.settings.SettingsActivity
import org.michaelbel.moviemade.presentation.features.user.UserFragment
import javax.inject.Inject

class MainActivity: BaseActivity(R.layout.activity_main),
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener {

    companion object {
        const val FRAGMENT_TAG = "fragment-tag"

        private const val KEY_FRAGMENT = "fragment"
        private const val DEFAULT_FRAGMENT = R.id.item_playing

        private const val ARG_BOTTOM_BAR_POSITION = "pos"
    }

    @Inject lateinit var preferences: SharedPreferences

    override fun setTheme(resid: Int) {
        super.setTheme(R.style.AppTheme_TransparentStatusBar)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment is LoginFragment) {
            fragment.onNewIntent(intent.action, intent.dataString)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_search) {
            startActivity<SearchActivity>()
        } else if (item.itemId == R.id.item_settings) {
            startActivity<SettingsActivity>()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[application].createActivityComponent().inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent20))

        val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin = DeviceUtil.statusBarHeight(this)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setOnNavigationItemReselectedListener(this)

        val itemId: Int = savedInstanceState?.getInt(ARG_BOTTOM_BAR_POSITION) ?: preferences.getInt(KEY_FRAGMENT, DEFAULT_FRAGMENT)

        bottomNavigationView.selectedItemId = itemId

        when (itemId) {
            R.id.item_playing -> {
                supportFragmentManager.transaction {
                    replace(container.id, MoviesFragment.newInstance(NOW_PLAYING), FRAGMENT_TAG)
                }
            }
            R.id.item_rated -> {
                supportFragmentManager.transaction {
                    replace(container.id, MoviesFragment.newInstance(TOP_RATED), FRAGMENT_TAG)
                }
            }
            R.id.item_upcoming -> {
                supportFragmentManager.transaction {
                    replace(container.id, MoviesFragment.newInstance(UPCOMING), FRAGMENT_TAG)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(ARG_BOTTOM_BAR_POSITION, bottomNavigationView.selectedItemId)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_playing ->
                supportFragmentManager.transaction {
                    replace(container.id, MoviesFragment.newInstance(NOW_PLAYING), FRAGMENT_TAG)
                }
            R.id.item_rated ->
                supportFragmentManager.transaction {
                    replace(container.id, MoviesFragment.newInstance(TOP_RATED), FRAGMENT_TAG)
                }
            R.id.item_upcoming ->
                supportFragmentManager.transaction {
                    replace(container.id, MoviesFragment.newInstance(UPCOMING), FRAGMENT_TAG)
                }
            R.id.item_user -> {
                val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
                supportFragmentManager.transaction {
                    if (sessionId.isEmpty()) {
                        replace(container.id, LoginFragment.newInstance(), FRAGMENT_TAG)
                    } else {
                        replace(container.id, UserFragment.newInstance(), FRAGMENT_TAG)
                    }
                }

                supportActionBar?.title = ""
                appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
            }
        }

        if (item.itemId != R.id.item_user) {
            preferences.edit().putInt(KEY_FRAGMENT, item.itemId).apply()
            supportActionBar?.setTitle(R.string.app_name)
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent20))
        }

        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val fragment: BaseFragment? = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as BaseFragment?
        fragment?.onScrollToTop()
    }

    /*@Shortcut(id = "favorites", rank = 3, icon = R.drawable.ic_shortcut_favorite, shortLabelRes = R.string.favorites)
    fun showFavorites() {
        supportFragmentManager
                .beginTransaction()
                .replace(container.id, UserFragment.newInstance(), UserFragment::class.simpleName)
                .commit()
        supportActionBar?.title = ""
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
        bottomNavigationView.selectedItemId = R.id.item_account

        val intent = Intent(this, ContainerActivity::class.java)
        intent.putExtra(FRAGMENT_NAME, FAVORITE)
        intent.putExtra(EXTRA_ACCOUNT_ID, accountFragment.accountId)
        startActivity(intent)
    }

    @Shortcut(id = "watchlist", rank = 2, icon = R.drawable.ic_shortcut_bookmark, shortLabelRes = R.string.watchlist)
    fun showWatchlist() {
        supportFragmentManager
                .beginTransaction()
                .replace(container.id, UserFragment.newInstance(), UserFragment::class.simpleName)
                .commit()
        supportActionBar?.title = ""
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
        bottomNavigationView.selectedItemId = R.id.item_account

        val intent = Intent(this, ContainerActivity::class.java)
        intent.putExtra(FRAGMENT_NAME, WATCHLIST)
        intent.putExtra(EXTRA_ACCOUNT_ID, accountFragment.accountId)
        startActivity(intent)
    }*/
}