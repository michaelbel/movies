package org.michaelbel.moviemade.ui.fragment;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.json.JSONObject;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.core.widget.RecyclerListView;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.model.InAppProduct;
import org.michaelbel.moviemade.ui.AboutActivity;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.AboutView;
import org.michaelbel.moviemade.ui.view.cell.EmptyCell;
import org.michaelbel.moviemade.ui.view.cell.TextCell;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AboutFragment extends Fragment {

    private int rowCount;
    private int infoRow;
    private int forkGithubRow;
    private int rateGooglePlay;
    private int otherAppsRow;
    private int libsRow;
    private int helpRow;
    private int feedbackRow;
    private int shareFriendsRow;
    private int donatePaypalRow;
    private int poweredByRow;

    private AboutActivity activity;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerListView recyclerView;

    //private UiCheckout mCheckout;
    //private Purchase mPurchase;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //final Billing billing = ((Moviemade) getActivity().getApplication()).getBilling();
        //mCheckout = Checkout.forUi(new MyIntentStarter(this), this, billing);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AboutActivity) getActivity();
        //mCheckout.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        //activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finish());
        //activity.binding.toolbarTitle.setText(R.string.About);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.toolbarTitle.setText(R.string.About);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        rowCount = 0;
        infoRow = rowCount++;
        rateGooglePlay = rowCount++;
        forkGithubRow = rowCount++;
        libsRow = rowCount++;
        otherAppsRow = rowCount++;
        helpRow = rowCount++;
        feedbackRow = rowCount++;
        shareFriendsRow = rowCount++;
        donatePaypalRow = rowCount++;
        poweredByRow = rowCount++;

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new AboutAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (position == forkGithubRow) {
                Browser.openUrl(activity, Moviemade.GITHUB_URL);
                //mCheckout.startPurchaseFlow(ProductTypes.IN_APP, "donation_id", null, new PurchaseListener());
            } else if (position == rateGooglePlay) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Moviemade.APP_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.openUrl(activity, Moviemade.APP_WEB);
                }
            } else if (position == otherAppsRow) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Moviemade.ACCOUNT_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.openUrl(activity, Moviemade.ACCOUNT_WEB);
                }
            } else if (position == libsRow) {
                activity.startFragment(new LibsFragment(), R.id.fragment_view, "libsFragment");
            } else if (position == feedbackRow) {
                try {
                    PackageManager packageManager = activity.getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageInfo("org.telegram.messenger", 0);
                    if (packageInfo != null) {
                        Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse(Moviemade.TELEGRAM_URL));
                        startActivity(telegram);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, Moviemade.EMAIL);
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(intent, getString(R.string.Feedback)));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (position == shareFriendsRow) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, Moviemade.APP_WEB);
                startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
            } else if (position == donatePaypalRow) {
                Browser.openUrl(activity, Moviemade.PAYPAL_ME);
            }
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mCheckout.loadInventory(Inventory.Request.create().loadAllPurchases(), new InventoryCallback());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    public static final int REQUEST_CODE_BUY = 1234;
    public static final int BILLING_RESPONSE_RESULT_OK = 0;

    private List<InAppProduct> getInAppPurchases(String type, String... productIds) throws Exception {
        ArrayList<String> skuList = new ArrayList<>(Arrays.asList(productIds));

        Bundle query = new Bundle();
        query.putStringArrayList("ITEM_ID_LIST", skuList);

        Bundle skuDetails = activity.billingService.getSkuDetails(3, activity.getPackageName(), type, query);
        ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");
        List<InAppProduct> results = new ArrayList<>();
        for (String responseItem : responseList) {
            JSONObject jo = new JSONObject(responseItem);
            InAppProduct product = new InAppProduct();
            product.productId = jo.getString("productId");
            product.storeName = jo.getString("title");
            product.storeDescription = jo.getString("description");
            product.price = jo.getString("price");
            product.isSubscription = jo.getString("type").equals("subs");
            product.priceAmountMicros = Integer.parseInt(jo.getString("price_amount_micros"));
            product.currencyIsoCode = jo.getString("price_currency_code");
            results.add(product);
        }
        return results;
    }

    public void purchasingItem() throws Exception {
        //Bundle bundle = activity.billingService.getBuyIntent(3, activity.getPackageName(), "donation_id", "inapp", "ca-app-pub-3651393080934289/2751044208");
        //Bundle bundle = activity.billingService.getBuyIntent(3, activity.getPackageName(), "donation_id", "inapp", "ca-app-pub-3651393080934289~1447245983");
        List<InAppProduct> purchases = getInAppPurchases("inapp", "donation_id");

        Bundle bundle = activity.billingService.getBuyIntent(3, activity.getPackageName(), "ca-app-pub-3651393080934289/2751044208", "inapp", "ca-app-pub-3651393080934289~1447245983");
        PendingIntent pendingIntent = bundle.getParcelable("BUY_INTENT");

        if (pendingIntent != null) {
            startIntentSenderForResult(pendingIntent.getIntentSender(), REQUEST_CODE_BUY, new Intent(), 0, 0, 0, null);
        }
    }

    private void purchaseProduct() throws Exception {
        String sku = "donation_id";
        String type = "inapp";

        String developerPayload = "12345";
        Bundle buyIntentBundle = activity.billingService.getBuyIntent(3, activity.getPackageName(), sku, type, developerPayload);
        PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

        startIntentSenderForResult(pendingIntent.getIntentSender(), REQUEST_CODE_BUY, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), null);
    }

    /*private void purchaseProduct(InAppProduct product) throws Exception {
        String sku = product.getScu();
        String type = product.getType();

        String developerPayload = "12345";
        Bundle buyIntentBundle = activity.billingService.getBuyIntent(3, activity.getPackageName(), sku, type, developerPayload);
        PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

        startIntentSenderForResult(pendingIntent.getIntentSender(), REQUEST_CODE_BUY, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), null);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //mCheckout.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == REQUEST_CODE_BUY) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", -1);

            if (responseCode == BILLING_RESPONSE_RESULT_OK) {
                String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
                String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
                readPurchase(purchaseData);
            }

            *//*if (requestCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Log.e("2580", sku + " purchased!");
                } catch (JSONException e) {
                    Log.e("2580", "Failed to parse purchase data");
                    e.printStackTrace();
                }
            }*//*
        }*/
    }

    /*private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(@Nonnull Purchase purchase) {
            mPurchase = purchase;
            onPurchaseChanged();
        }
    }*/

    @Override
    public void onDestroy() {
        //mCheckout.stop();
        super.onDestroy();
    }

    /*private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(@Nonnull Inventory.Products products) {
            final Inventory.Product product = products.get(ProductTypes.IN_APP);
            if (!product.supported) {
                return;
            }
            mPurchase = product.getPurchaseInState("donation_id", Purchase.State.PURCHASED);
            onPurchaseChanged();
            //mBuyConsume.setEnabled(true);
        }
    }*/

    private void onPurchaseChanged() {
        //mBuyConsume.setText(mPurchase != null ? R.string.consume : R.string.buy);
    }

    private void readPurchase(String purchaseData) {
        try {
            JSONObject jo = new JSONObject(purchaseData);
            String orderId = jo.optString("orderId");
            String packageName = jo.getString("packageName");
            String productId = jo.getString("productId");
            long purchaseTime = jo.getLong("purchaseTime");
            int purchaseState = jo.getInt("purchaseState");
            String developerPayload = jo.optString("developerPayload");
            String purchaseToken = jo.getString("purchaseToken");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AboutAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View view;

            if (type == 0) {
                view = new AboutView(activity);
            } else if (type == 1) {
                view = new EmptyCell(activity);
            } else {
                view = new TextCell(activity);
            }

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 0) {
                AboutView view = (AboutView) holder.itemView;
                view.setName(getString(R.string.AppForAndroid, getString(R.string.AppName)));
                view.setVersion(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, BuildConfig.VERSION_DATE);
            } else if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == helpRow) {
                    cell.setMode(EmptyCell.MODE_TEXT);
                    cell.setText(AndroidUtils.replaceTags(getString(R.string.ProjectInfo)));
                } else if (position == poweredByRow) {
                    cell.setMode(EmptyCell.MODE_TEXT);
                    cell.setText(AndroidUtils.replaceTags(getString(R.string.PoweredBy)));
                    cell.setTextGravity(Gravity.CENTER_HORIZONTAL);
                }
            } else if (type == 2) {
                TextCell cell = (TextCell) holder.itemView;
                cell.changeLayoutParams().setMode(TextCell.MODE_ICON).setHeight(ScreenUtils.dp(52));

                if (position == rateGooglePlay) {
                    cell.setIcon(R.drawable.ic_google_play).setText(R.string.RateGooglePlay).setDivider(true);
                } else if (position == forkGithubRow) {
                    cell.setIcon(R.drawable.ic_github).setText(R.string.ForkGithub).setDivider(true);
                } else if (position == libsRow) {
                    cell.setIcon(R.drawable.ic_storage).setText(R.string.OpenSourceLibs).setDivider(true);
                } else if (position == otherAppsRow) {
                    cell.setIcon(R.drawable.ic_shop).setText(R.string.OtherDeveloperApps).setDivider(false);
                } else if (position == feedbackRow) {
                    cell.setIcon(R.drawable.ic_mail).setText(R.string.Feedback).setDivider(true);
                } else if (position == shareFriendsRow) {
                    cell.setIcon(R.drawable.ic_share).setText(R.string.ShareWithFriends).setDivider(true);
                } else if (position == donatePaypalRow) {
                    cell.setIcon(R.drawable.ic_cash_usd).setText(R.string.DonatePaypal).setDivider(false);
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == infoRow) {
                return 0;
            } else if (position == helpRow || position == poweredByRow) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    /*private static class MyIntentStarter implements IntentStarter {
        @Nonnull
        private final Fragment mFragment;

        public MyIntentStarter(@Nonnull Fragment fragment) {
            mFragment = fragment;
        }

        @Override
        public void startForResult(@Nonnull IntentSender intentSender, int requestCode, @Nonnull Intent intent) throws IntentSender.SendIntentException {
            mFragment.startIntentSenderForResult(intentSender, requestCode, intent, 0, 0, 0, null);
        }
    }*/
}