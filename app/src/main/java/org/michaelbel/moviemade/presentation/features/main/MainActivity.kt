package org.michaelbel.moviemade.presentation.features.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.KEY_TOKEN
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.account.AccountFragment
import org.michaelbel.moviemade.presentation.features.main.fragments.NowPlayingFragment
import org.michaelbel.moviemade.presentation.features.main.fragments.TopRatedFragment
import org.michaelbel.moviemade.presentation.features.main.fragments.UpcomingFragment
import org.michaelbel.moviemade.presentation.features.search.SearchActivity
import org.michaelbel.moviemade.presentation.features.settings.SettingsActivity
import shortbread.Shortcut
import javax.inject.Inject

class MainActivity: BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val KEY_FRAGMENT = "fragment"
        private const val DEFAULT_FRAGMENT = R.id.item_playing

        private const val ARG_NAVIGATION_BAR_POSITION = "pos"
    }

    private lateinit var accountFragment: AccountFragment

    @Inject
    lateinit var preferences: SharedPreferences

    override fun setTheme(resid: Int) {
        super.setTheme(R.style.AppTheme_TransparentStatusBar)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val action = intent.action
        val data = intent.dataString

        if (Intent.ACTION_VIEW == action && data != null) {
            val token = preferences.getString(KEY_TOKEN, "") ?: ""
            accountFragment.presenter.createSessionId(token)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_search) {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        } else if (item.itemId == R.id.item_settings) {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App[application].createActivityComponent().inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent20))

        val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin = DeviceUtil.getStatusBarHeight(this)

        accountFragment = AccountFragment()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            val item = preferences.getInt(KEY_FRAGMENT, DEFAULT_FRAGMENT)
            bottomNavigationView.selectedItemId = item
        } else {
            bottomNavigationView.selectedItemId = savedInstanceState.getInt(ARG_NAVIGATION_BAR_POSITION)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putInt(ARG_NAVIGATION_BAR_POSITION, bottomNavigationView.selectedItemId)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_playing -> supportFragmentManager.beginTransaction().replace(container.id, NowPlayingFragment()).commit()
            R.id.item_rated -> supportFragmentManager.beginTransaction().replace(container.id, TopRatedFragment()).commit()
            R.id.item_upcoming -> supportFragmentManager.beginTransaction().replace(container.id, UpcomingFragment()).commit()
            R.id.item_account -> {
                supportFragmentManager.beginTransaction().replace(container.id, accountFragment).commit()
                supportActionBar?.title = ""
                appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
            }
        }

        if (item.itemId != R.id.item_account) {
            preferences.edit().putInt(KEY_FRAGMENT, item.itemId).apply()
            supportActionBar?.setTitle(R.string.app_name)
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent20))
        }

        return true
    }

    @Shortcut(id = "favorites", rank = 3, icon = R.drawable.ic_shortcut_favorite, shortLabelRes = R.string.favorites)
    fun showFavorites() {
        supportFragmentManager.beginTransaction().replace(container.id, accountFragment).commit()
        supportActionBar?.title = ""
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
        bottomNavigationView.selectedItemId = R.id.item_account
        startFavorites(accountFragment.accountId)
    }

    @Shortcut(id = "watchlist", rank = 2, icon = R.drawable.ic_shortcut_bookmark, shortLabelRes = R.string.watchlist)
    fun showUpcomingMovies() {
        supportFragmentManager.beginTransaction().replace(container.id, accountFragment).commit()
        supportActionBar?.title = ""
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
        bottomNavigationView.selectedItemId = R.id.item_account
        startWatchlist(accountFragment.accountId)
    }
}