package org.michaelbel.application.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.ACCOUNT;
import org.michaelbel.application.rest.model.Account;
import org.michaelbel.application.ui.view.cell.EmptyCell;
import org.michaelbel.application.util.ScreenUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationView extends FrameLayout {

    private int rowCount;
    private int headerRow;
    private int emptyRow1;
    private int moviesRow;
    private int mostPopularRow;
    private int topRatedRow;
    private int upComingRow;
    private int dividerRow1;
    private int watchlistRow;
    private int favoritesRow;
    private int dividerRow2;
    private int settingsRow;
    private int aboutRow;
    private int emptyRow2;

    private Rect rect;
    private int drawerMaxWidth;
    private Rect tempRect = new Rect();
    private Drawable insetForeground = new ColorDrawable(0x33000000);
    private OnNavigationItemSelectedListener onNavigationItemSelectedListener;
    private OnNavigationHeaderClickListener onNavigationHeaderClick;

    public interface OnNavigationItemSelectedListener {
        void onNavigationItemSelected(View view, int position);
    }

    public interface OnNavigationHeaderClickListener {
        void onHeaderClick(View view);
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
        headerRow = rowCount++;
        emptyRow1 = rowCount++;
        favoritesRow = rowCount++;
        dividerRow2 = rowCount++;
        settingsRow = rowCount++;
        aboutRow = rowCount++;
        emptyRow2 = rowCount++;

        ListView listView = new ListView(context);
        listView.setDividerHeight(0);
        listView.setDrawSelectorOnTop(true);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(new NavigationViewAdapter());
        listView.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (onNavigationItemSelectedListener != null) {
                onNavigationItemSelectedListener.onNavigationItemSelected(view, position);
            }
        });
        addView(listView);
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

    public void setOnNavigationHeaderClick(OnNavigationHeaderClickListener listener) {
        onNavigationHeaderClick = listener;
    }

    private class NavigationViewAdapter extends BaseAdapter {

        @Override
        public boolean isEnabled(int i) {
            return !(i == emptyRow1 || i == emptyRow2 || i == dividerRow1 || i == dividerRow2);
        }

        @Override
        public int getCount() {
            return rowCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            int type = getItemViewType(position);

            if (type == 0) {
                if (view == null) {
                    view = new DrawerHeaderCell(getContext());
                }
            } else if (type == 1) {
                if (view == null) {
                    view = new DrawerActionCell(getContext());
                }

                DrawerActionCell cell = (DrawerActionCell) view;

                if (position == moviesRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_movie, 0xFFF44336));
                    cell.setText(getContext().getString(R.string.Movies));
                } else if (position == mostPopularRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_whatshot, 0xFFFF9800));
                    cell.setText(getContext().getString(R.string.Popular));
                } else if (position == topRatedRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_star_circle, 0xFF4CAF50));
                    cell.setText(getContext().getString(R.string.TopRated));
                } else if (position == upComingRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_movie_cadr, 0xFF2196F3));
                    cell.setText(getContext().getString(R.string.Upcoming));
                } else if (position == watchlistRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_watch,
                            ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(getContext().getString(R.string.Watchlist));
                } else if (position == favoritesRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_favorite,
                            ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(getContext().getString(R.string.Favorites));
                } else if (position == settingsRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_settings,
                            ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(getContext().getString(R.string.Settings));
                } else if (position == aboutRow) {
                    cell.setIcon(Theme.getIcon(R.drawable.ic_about,
                            ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                    cell.setText(getContext().getString(R.string.About));
                }
            } else if (type == 2) {
                if (view == null) {
                    view = new EmptyCell(getContext());
                }

                EmptyCell cell = (EmptyCell) view;
                cell.setMode(EmptyCell.MODE_DEFAULT);
                cell.setHeight(ScreenUtils.dp(8));
            } else if (type == 3) {
                if (view == null) {
                    view = new DividerCell(getContext());
                }
            }

            return view;
        }

        @Override
        public int getItemViewType(int i) {
            if (i == headerRow) {
                return 0;
            } else if (i == moviesRow || i == mostPopularRow || i == settingsRow ||
                    i == aboutRow || i == watchlistRow || i == favoritesRow || i == topRatedRow ||
                    i == upComingRow) {
                return 1;
            } else if (i == emptyRow1 || i == emptyRow2) {
                return 2;
            } else if (i == dividerRow1 || i == dividerRow2) {
                return 3;
            }

            return -1;
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }
    }

    public class DrawerHeaderCell extends FrameLayout {

        private ImageView avatarImageView;
        private TextView nameTextView;

        public DrawerHeaderCell(Context context) {
            super(context);

            setBackground(context.getDrawable(R.drawable.drawer_header));
            setOnClickListener(view -> {
                if (onNavigationHeaderClick != null) {
                    onNavigationHeaderClick.onHeaderClick(view);
                }
            });

            avatarImageView = new ImageView(context);
            avatarImageView.setBackground(context.getDrawable(R.drawable.tmdb_icon));
            avatarImageView.setLayoutParams(LayoutHelper.makeFrame(64, 64, Gravity.START | Gravity.BOTTOM, 16, 0, 0, 56));
            addView(avatarImageView);

            nameTextView = new TextView(context);
            nameTextView.setMaxLines(1);
            nameTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
            nameTextView.setGravity(Gravity.START);
            //nameTextView.setText("Link your TMDb account");
            nameTextView.setText(R.string.AppName);
            nameTextView.setEllipsize(TextUtils.TruncateAt.END);
            nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            nameTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            nameTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT,
                    LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM, 16, 0, 16, 16));
            addView(nameTextView);
        }

        private void setHeader() {
            SharedPreferences preferences = getContext().getSharedPreferences("user_config", Context.MODE_PRIVATE);
            String sessionId = preferences.getString("session_id", null);

            Log.e("ytt", "session id :" + sessionId);
            Log.e("ytt", "api key :" + Url.TMDB_API_KEY);

            if (sessionId == null) {
                nameTextView.setText("Link your TMDb account");
                avatarImageView.setImageResource(R.drawable.tmdb_icon);
            } else {
                ACCOUNT service = ApiFactory.getRetrofit().create(ACCOUNT.class);
                Call<Account> call = service.getDetails(Url.TMDB_API_KEY, sessionId);
                call.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
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
                    public void onFailure(Call<Account> call, Throwable t) {
                        //FirebaseCrash.report(t);
                    }
                });
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = getMeasuredWidth();
            int height = ScreenUtils.dp(148) + ScreenUtils.getStatusBarHeight();
            setMeasuredDimension(width, height);
        }
    }

    public class DrawerActionCell extends FrameLayout {

        private TextView textView;
        private ImageView imageView;

        public DrawerActionCell(Context context) {
            super(context);

            imageView = new ImageView(context);
            imageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT,
                    LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 0, 0));
            addView(imageView);

            textView = new TextView(context);
            textView.setLines(1);
            textView.setMaxLines(1);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
            textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT,
                    LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 72, 0, 16, 0));
            addView(textView);
        }

        public void setIcon(@NonNull Drawable resId) {
            imageView.setImageDrawable(resId);
        }

        public void setText(@NonNull String text) {
            textView.setText(text);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = getMeasuredWidth();
            int height = ScreenUtils.dp(48);
            setMeasuredDimension(width, height);
        }
    }

    public class DividerCell extends FrameLayout {

        public DividerCell(Context context) {
            super(context);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 1
            );
            params.topMargin = ScreenUtils.dp(8);
            params.bottomMargin = ScreenUtils.dp(8);

            View view = new View(context);
            view.setBackgroundColor(ContextCompat.getColor(context, Theme.dividerColor()));
            view.setLayoutParams(params);
            addView(view);
        }
    }
}