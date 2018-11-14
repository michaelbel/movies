package org.michaelbel.moviemade.modules_beta.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.modules_beta.view.cell.DividerCell;
import org.michaelbel.moviemade.modules_beta.view.cell.DrawerActionCell;
import org.michaelbel.moviemade.modules_beta.view.cell.EmptyCell;
import org.michaelbel.moviemade.utils.ScreenUtils;

public class NavigationView extends FrameLayout {

    private int rowCount;
    private int headerRow;
    private int emptyRow1;
    private int playingRow;
    private int popularRow;
    private int ratedRow;
    private int upcomingRow;
    private int dividerRow1;
    private int peopleRow;
    private int genresRow;
    private int dividerRow2;
    private int favoritesRow;
    private int watchlistRow;
    private int dividerRow3;
    private int settingsRow;
    private int aboutRow;
    private int emptyRow2;

    private RecyclerListView recyclerView;

    private Rect rect;
    private int drawerMaxWidth;
    private Rect tempRect = new Rect();
    private Drawable insetForeground = new ColorDrawable(0x33000000);
    private OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    public interface OnNavigationItemSelectedListener {
        void onNavigationItemSelected(View view, int position);
    }

    public NavigationView(Context context) {
        super(context);
        initialize(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void initialize(Context context) {
        rowCount = 0;
        headerRow = rowCount++;    // position = 0
        emptyRow1 = rowCount++;
        playingRow = rowCount++;
        popularRow = rowCount++;
        ratedRow = rowCount++;
        upcomingRow = rowCount++;
        dividerRow1 = rowCount++;
        genresRow = rowCount++;    // position = 3
        peopleRow = rowCount++;    // position = 4
        dividerRow2 = rowCount++;
        watchlistRow = rowCount++; // position = 6
        favoritesRow = rowCount++; // position = 7
        dividerRow3 = rowCount++;
        settingsRow = rowCount++;  // position = 9
        aboutRow = rowCount++;     // position = 10
        emptyRow2 = rowCount++;

        recyclerView = new RecyclerListView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new NavigationViewAdapter());
        recyclerView.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (onNavigationItemSelectedListener != null) {
                onNavigationItemSelectedListener.onNavigationItemSelected(view, position);
            }
        });
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(recyclerView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        if (w != oldW) {
            if (drawerMaxWidth <= 0) {
                if (getLayoutParams().width != ViewGroup.LayoutParams.MATCH_PARENT &&
                        getLayoutParams().width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    drawerMaxWidth = getLayoutParams().width;
                    updateWidth();
                } else {
                    drawerMaxWidth = ScreenUtils.dp(400);
                    updateWidth();
                }
            }

            updateWidth();
        }
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        rect = new Rect(insets);
        setWillNotDraw(insetForeground == null);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (rect != null && insetForeground != null) {
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());

            tempRect.set(0, 0, width, rect.top);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            tempRect.set(0, height - rect.bottom, width, height);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            tempRect.set(0, rect.top, rect.left, height - rect.bottom);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            tempRect.set(width - rect.right, rect.top, width, height - rect.bottom);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    public void updateTheme() {
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), Theme.foregroundColor()));
        recyclerView.setAdapter(new NavigationViewAdapter());
    }

    private void updateWidth() {
        int viewportWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        int viewportHeight = getContext().getResources().getDisplayMetrics().heightPixels;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int navigationBarWidthResId = getResources().getIdentifier("navigation_bar_width", "dimen", "android");
            if (navigationBarWidthResId > 0) {
                viewportWidth -= getResources().getDimensionPixelSize(navigationBarWidthResId);
            }
        }

        int viewportMin = Math.min(viewportWidth, viewportHeight);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
        int actionBarSize = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        int width = viewportMin - actionBarSize;
        getLayoutParams().width = Math.min(width, drawerMaxWidth);
    }

    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
        onNavigationItemSelectedListener = listener;
    }

    private class NavigationViewAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            View view;

            if (type == 0) {
                view = new DrawerHeaderCell(parent.getContext());
            } else if (type == 1) {
                view = new EmptyCell(parent.getContext());
            } else if (type == 2) {
                view = new DividerCell(parent.getContext());
            } else {
                view = new DrawerActionCell(parent.getContext());
            }

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.setMode(EmptyCell.MODE_DEFAULT);
                cell.setHeight(ScreenUtils.dp(8));
            } else if (type == 3) {
                DrawerActionCell cell = (DrawerActionCell) holder.itemView;

                if (position == playingRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_movie, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.now_playing);
                } else if (position == popularRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_movie, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.Popular);
                } else if (position == ratedRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_movie, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.top_rated);
                } else if (position == upcomingRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_movie, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.upcoming);
                } else if (position == peopleRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_account_multiple, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.PopularPeople);
                } else if (position == genresRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_view_list, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.Genres);
                } else if (position == favoritesRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_favorite, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.Favorites);
                } else if (position == watchlistRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_bookmark, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.Watchlist);
                } else if (position == settingsRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_settings, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.settings);
                } else if (position == aboutRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_about, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(R.string.about);
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == headerRow) {
                return 0;
            } else if (position == emptyRow1 || position == emptyRow2) {
                return 1;
            } else if (position == dividerRow1 || position == dividerRow2 || position == dividerRow3) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public class DrawerHeaderCell extends FrameLayout {

        private ImageView avatarImage;
        private TextView nameTextView;

        public DrawerHeaderCell(Context context) {
            super(context);

            //setBackground(context.getDrawable(R.drawable.drawer_header));

            avatarImage = new ImageView(context);
            avatarImage.setBackground(context.getDrawable(R.drawable.tmdb_icon));
            avatarImage.setLayoutParams(LayoutHelper.makeFrame(64, 64, Gravity.START | Gravity.BOTTOM, 16, 0, 0, 56));
            addView(avatarImage);

            nameTextView = new TextView(context);
            nameTextView.setLines(1);
            nameTextView.setMaxLines(1);
            nameTextView.setText(R.string.AppName);
            nameTextView.setGravity(Gravity.START);
            nameTextView.setEllipsize(TextUtils.TruncateAt.END);
            nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            nameTextView.setTextColor(ContextCompat.getColor(context, R.color.md_white));
            nameTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            nameTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM, 16, 0, 16, 16));
            addView(nameTextView);
        }

        /*private void setHeader() {
            SharedPreferences preferences = getContext().getSharedPreferences("user_config", Context.MODE_PRIVATE);
            String sessionId = preferences.getString("session_id", null);

            Log.e("ytt", "session id :" + sessionId);
            Log.e("ytt", "api key :" + Url.TMDB_API_KEY);

            if (sessionId == null) {
                nameTextView.setText("Link your TMDb account");
                avatarImage.setImageResource(R.drawable.tmdb_icon);
            } else {
                ACCOUNT service = ApiFactory.getRetrofit().create(ACCOUNT.class);
                Call<Account> call = service.getDetails(Url.TMDB_API_KEY, sessionId);
                call.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                        if (response.isSuccessful()) {
                            Account account = response.body();

                            nameTextView.setText(account.name);

                            if (account.username != null) {
                                //statusText.setText(account.username);
                            }

                            Log.e("ytt", "api key :" + account.id);
                        } else {
                            Log.e("tag", "server not found");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {

                    }
                });
            }
        }*/

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = getMeasuredWidth();
            int height = ScreenUtils.dp(148) + Extensions.getStatusBarHeight(getContext());
            setMeasuredDimension(width, height);
        }
    }
}