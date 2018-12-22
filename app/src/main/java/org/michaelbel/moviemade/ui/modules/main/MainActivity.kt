package org.michaelbel.moviemade.ui.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.account.AccountFragment
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment
import org.michaelbel.moviemade.ui.modules.search.SearchActivity
import org.michaelbel.moviemade.ui.modules.settings.SettingsActivity
import org.michaelbel.moviemade.ui.widgets.bottombar.BottomNavigationBar
import org.michaelbel.moviemade.ui.widgets.bottombar.BottomNavigationItem
import org.michaelbel.moviemade.utils.DeviceUtil
import org.michaelbel.moviemade.utils.KEY_MAIN_FRAGMENT
import org.michaelbel.moviemade.utils.KEY_TOKEN
import shortbread.Shortcut

class MainActivity : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {

    companion object {
        private const val DEFAULT_FRAGMENT = 0
    }

    private var nowPlayingFragment: NowPlayingFragment? = null
    private var topRatedFragment: TopRatedFragment? = null
    private var upcomingFragment: UpcomingFragment? = null
    private var profileFragment: AccountFragment? = null

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
        setTheme(R.style.AppThemeTransparentStatusBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar_title!!.setText(R.string.app_name)
        toolbar!!.setOnClickListener {
            val position = bottom_bar!!.currentSelectedPosition
            if (position != 3) {
                scrollToTop(position)
            }
        }

        app_bar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent20))

        val params = app_bar!!.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin = DeviceUtil.getStatusBarHeight(this)

        nowPlayingFragment = NowPlayingFragment()
        topRatedFragment = TopRatedFragment()
        upcomingFragment = UpcomingFragment()
        profileFragment = AccountFragment()

        bottom_bar!!.setTabSelectedListener(this)
        bottom_bar!!.setBarBackgroundColor(R.color.primary)
        bottom_bar!!.activeColor = R.color.md_white
        bottom_bar!!.setMode(BottomNavigationBar.MODE_FIXED)
        bottom_bar!!.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT)
        bottom_bar!!.setFirstSelectedPosition(getSharedPreferences().getInt(KEY_MAIN_FRAGMENT, DEFAULT_FRAGMENT))
            .addItem(BottomNavigationItem(R.drawable.ic_fire, R.string.now_playing).setActiveColorResource(R.color.accent))
            .addItem(BottomNavigationItem(R.drawable.ic_star_circle, R.string.top_rated).setActiveColorResource(R.color.accent))
            .addItem(BottomNavigationItem(R.drawable.ic_movieroll, R.string.upcoming).setActiveColorResource(R.color.accent))
            .addItem(BottomNavigationItem(R.drawable.ic_account_circle, R.string.account).setActiveColorResource(R.color.accent))
            .initialise()

        if (savedInstanceState == null) {
            startCurrentFragment()
        }
    }

    override fun onTabSelected(position: Int) {
        when (position) {
            0 -> startFragment(nowPlayingFragment!!, R.id.fragment_view)
            1 -> startFragment(topRatedFragment!!, R.id.fragment_view)
            2 -> startFragment(upcomingFragment!!, R.id.fragment_view)
            3 -> {
                startFragment(profileFragment!!, R.id.fragment_view)
                toolbar_title!!.text = null
                app_bar!!.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
            }
        }

        if (position != 3) {
            getSharedPreferences().edit().putInt(KEY_MAIN_FRAGMENT, position).apply()
            toolbar_title!!.setText(R.string.app_name)
            app_bar!!.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent20))
        }
    }

    override fun onTabReselected(position: Int) {
        if (position != 3) {
            scrollToTop(position)
        }
    }

    override fun onTabUnselected(position: Int) {}

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val action = intent.action
        val data = intent.dataString

        if (Intent.ACTION_VIEW == action && data != null) {
            profileFragment!!.presenter.createSessionId(getSharedPreferences().getString(KEY_TOKEN, "")!!)
        }
    }

    private fun scrollToTop(position: Int) {
        when (position) {
            0 -> if (nowPlayingFragment!!.adapter.itemCount == 0) {
                nowPlayingFragment!!.presenter.getNowPlaying()
            } else {
                nowPlayingFragment!!.recyclerView.smoothScrollToPosition(0)
            }

            1 -> if (topRatedFragment!!.adapter.itemCount == 0) {
                topRatedFragment!!.presenter.getTopRated()
            } else {
                topRatedFragment!!.recyclerView.smoothScrollToPosition(0)
            }

            2 -> if (upcomingFragment!!.adapter.itemCount == 0) {
                upcomingFragment!!.presenter.getUpcoming()
            } else {
                upcomingFragment!!.recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    private fun startCurrentFragment() {
        val position = getSharedPreferences().getInt(KEY_MAIN_FRAGMENT, DEFAULT_FRAGMENT)

        when (position) {
            0 -> startFragment(nowPlayingFragment!!, R.id.fragment_view)
            1 -> startFragment(topRatedFragment!!, R.id.fragment_view)
            2 -> startFragment(upcomingFragment!!, R.id.fragment_view)
        }
    }

    @Shortcut(id = "favorites", rank = 3, icon = R.drawable.ic_shortcut_favorite, shortLabelRes = R.string.favorites)
    fun showFavorites() {
        startFragment(profileFragment!!, R.id.fragment_view)
        toolbar_title!!.text = null
        app_bar!!.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
        bottom_bar!!.selectTab(3)
        startFavorites(profileFragment!!.accountId)
    }

    @Shortcut(id = "watchlist", rank = 2, icon = R.drawable.ic_shortcut_bookmark, shortLabelRes = R.string.watchlist)
    fun showUpcomingMovies() {
        startFragment(profileFragment!!, R.id.fragment_view)
        toolbar_title!!.text = null
        app_bar!!.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent))
        bottom_bar!!.selectTab(3)
        startWatchlist(profileFragment!!.accountId)
    }
}