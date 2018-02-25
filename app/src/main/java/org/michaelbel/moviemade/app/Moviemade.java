package org.michaelbel.moviemade.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.google.android.gms.analytics.Tracker;

import org.michaelbel.moviemade.app.eventbus.RxBus;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Moviemade extends Application {

    public static final String EMAIL = "michael-bel@outlook.com";
    public static final String TELEGRAM_URL = "https://t.me/michaelbel";
    public static final String PAYPAL_ME = "https://paypal.me/michaelbel";
    public static final String GITHUB_URL = "https://github.com/michaelbel/moviemade";
    public static final String ACCOUNT_WEB = "https://play.google.com/store/apps/developer?id=Michael+Bel";
    public static final String ACCOUNT_MARKET = "market://developer?id=Michael+Bel";
    public static final String APP_WEB = "https://play.google.com/store/apps/details?id=org.michaelbel.moviemade";
    public static final String APP_MARKET = "market://details?id=org.michaelbel.moviemade";

    public RxBus rxBus;
    public static volatile Context AppContext;
    public static volatile Handler AppHandler;

    public Tracker tracker;

    /*@Nonnull
    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @Nonnull
        @Override
        public String getPublicKey() {
            // encrypted public key of the app. Plain version can be found in Google Play's Developer
            // Console in Service & APIs section under "YOUR LICENSE KEY FOR THIS APPLICATION" title.
            // A naive encryption algorithm is used to "protect" the key. See more about key protection
            // here: https://developer.android.com/google/play/billing/billing_best_practices.html#key
            final String s = "PixnMSYGLjg7Ah0xDwYILlVZUy0sIiBoMi4jLDcoXTcNLiQjKgtlIC48NiRcHxwKHEcYEyZrPyMWXFRpV10VES9ENz" +
                    "g1Hj06HTV1MCAHJlpgEDcmOxFDEkA8OiQRKjEQDxhRWVVEMBYmNl1AJghcKUAYVT15KSQgBQABMgwqKSlqF1gZBA4fAw5rMyxKI" +
                    "w9LJFc7AhxZGjoPATgRUiUjKSsOWyRKDi4nIA9lKgAGOhMLDF06CwoKGFR6Wj0hGwReS10NXzQTIREhKlkuMz4XDTwUQjRCJUA+" +
                    "VjQVPUIoPicOLQJCLxs8RjZnJxY1OQNLKgQCPj83AyBEFSAJEk5UClYjGxVLNBU3FS4DCztENQMuOk5rFVclKz88AAApPgADGFx" +
                    "EEV5eQAF7QBhdQEE+Bzc5MygCAwlEFzclKRB7FB0uFgwPKgAvLCk2OyFiKxkgIy8BBQYjFy4/E1ktJikrEVlKJVYIHh16NDwtDC" +
                    "U0Vg8JNzoQBwQWOwk1GzZ4FT8fWicwITcRJi8=";
            return Encryption.decrypt(s, "michaelbel24865@gmail.com");
        }
    });*/

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = getApplicationContext();
        AppHandler = new Handler(getApplicationContext().getMainLooper());

        rxBus = new RxBus();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                //.schemaVersion(1)
                //.migration()
                .name("Moviemade10.realm")
                .build();
        Realm.setDefaultConfiguration(config);

        /*mBilling.addPlayStoreListener(new PlayStoreListener() {
            @Override
            public void onPurchasesChanged() {
                //Toast.makeText(this, "Purchases changes", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    /*@Nonnull
    public Billing getBilling() {
        return mBilling;
    }*/

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /*public synchronized Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.analytics);
        }

        return tracker;
    }*/

    public RxBus eventBus() {
        return rxBus;
    }
}