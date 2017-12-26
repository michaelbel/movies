package org.michaelbel.application.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.ui.fragment.NowPlayingFragment;
import org.michaelbel.application.ui.fragment.PopularFragment;
import org.michaelbel.application.ui.fragment.SearchFragment;
import org.michaelbel.application.ui.fragment.TopRatedFragment;
import org.michaelbel.application.ui.fragment.UpcomingFragment;
import org.michaelbel.application.ui.view.NavigationView;
import org.michaelbel.application.util.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private final int MENU_SEARCH = 10;

    public Toolbar toolbar;
    public TextView toolbarTextView;
    public ViewPager viewPager;
    public TabLayout tabLayout;

    public DrawerLayout drawerLayout;
    public AppBarLayout appBarLayout;
    public NavigationView navigationView;

    public FrameLayout searchViewLayout;

    private String requestToken;
    private CustomTabsServiceConnection connection;
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //getWindow().setStatusBarColor(0x33000000);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);

        toolbarTextView = findViewById(R.id.toolbar_title);
        toolbarTextView.setText(R.string.AppName);

        drawerLayout = findViewById(R.id.drawer);
        appBarLayout = findViewById(R.id.app_bar);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            if (position == 2) {
                startActivity(new Intent(this, FavsActivity.class));
            } else if (position == 4) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (position == 5) {
                startActivity(new Intent(this, AboutActivity.class));
            }
        });
        /*navigationView.setOnNavigationHeaderClick(new NavigationView.OnNavigationHeaderClickListener() {
            @Override
            public void onHeaderClick(View view) {
                createRequestToken();
            }
        });*/

        viewPager = findViewById(R.id.view_pager);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), R.string.NowPlaying);
        adapter.addFragment(new PopularFragment(), R.string.Popular);
        adapter.addFragment(new TopRatedFragment(), R.string.TopRated);
        adapter.addFragment(new UpcomingFragment(), R.string.Upcoming);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));

        searchViewLayout = findViewById(R.id.search_fragment_view);
        searchViewLayout.setBackgroundColor(0xFFFFFFFF);
        searchViewLayout.setVisibility(View.INVISIBLE);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SEARCH, Menu.NONE, "Search")
                .setIcon(R.drawable.ic_search)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setOnMenuItemClickListener(item -> {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    return true;
                });

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        /*Toast.makeText(this, "Получилось!", Toast.LENGTH_SHORT).show();
        createSession();

        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            try {
                Toast.makeText(this, "Получилось!", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.START);
                createSession();
            } catch (Exception e) {
                FirebaseCrash.report(e);
            }
        }*/
    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                if (getCurrentFocus() != null) {
                    KeyboardUtils.hideKeyboard(getCurrentFocus());
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public void startMovie(int movieId, String movieTitle) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("movieTitle", movieTitle);
        startActivity(intent);
    }

    public void startSearchFragment() {
        searchViewLayout.setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.search_fragment_view, new SearchFragment())
                .commit();
    }

    public void finishSearchFragment() {
        searchViewLayout.setVisibility(View.INVISIBLE);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            try {
                Toast.makeText(this, "Получилось!", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.START);
                createSession();
            } catch (Exception e) {
                FirebaseCrash.report(e);
            }
        }
    }*/

    /*private void createRequestToken() {
        AUTHENTICATION service = ApiFactory.getRetrofit().create(AUTHENTICATION.class);
        Call<Auth.CreatedRequest> call = service.createRequestToken(Url.TMDB_API_KEY);
        call.enqueue(new Callback<Auth.CreatedRequest>() {
            @Override
            public void onResponse(Call<Auth.CreatedRequest> call, Response<Auth.CreatedRequest> response) {
                Auth.CreatedRequest request = response.body();
                requestToken = request.request_token;

                uri = Uri.parse("https://www.themoviedb.org/authenticate/" + requestToken + "?redirect_to=anything://auth_callback_anything");

                connection = new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent intent = builder.build();
                        client.warmup(0L);
                        intent.launchUrl(MainActivity.this, uri);

                        client.newSession(new CustomTabsCallback() {
                            @Override
                            public void onNavigationEvent(int navigationEvent, Bundle extras) {
                                //super.onNavigationEvent(navigationEvent, extras);
                                if (navigationEvent == NAVIGATION_FINISHED) {
                                    Toast.makeText(MainActivity.this, "Это успех", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {

                    }
                };

                if (requestToken != null) {
                    CustomTabsClient.bindCustomTabsService(MainActivity.this, "com.android.chrome", connection);
                    //Browser.openUrl(MainActivity.this, "https://www.themoviedb.org/authenticate/" + requestToken + "?redirect_to=anything://auth_callback_anything");
                }
            }

            @Override
            public void onFailure(Call<Auth.CreatedRequest> call, Throwable t) {
                FirebaseCrash.report(t);
            }
        });
    }

    private void createSession() {
        AUTHENTICATION service = ApiFactory.getRetrofit().create(AUTHENTICATION.class);
        Call<Auth.Session> call = service.createSession(Url.TMDB_API_KEY, requestToken);
        call.enqueue(new Callback<Auth.Session>() {
            @Override
            public void onResponse(Call<Auth.Session> call, Response<Auth.Session> response) {
                Auth.Session session = response.body();

                if (session.sessionId != null) {
                    SharedPreferences preferences = getSharedPreferences("user_config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("session_id", session.sessionId);
                    editor.apply();
                }

                Toast.makeText(MainActivity.this, "Session id получен", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Auth.Session> call, Throwable t) {
                FirebaseCrash.report(t);
            }
        });
    }*/
}