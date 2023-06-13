package com.mobarok.fbaudiencenetworkads;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class AudienceNetwork {
    private static final String TAG = "AdNetwork";
    private final Activity activity;

    InterstitialAd interstitialAd;
    /*private NativeAd fbNativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;*/
    private int counter = 1;
    private Boolean fullScreenStatus = false;
    private Boolean bannerStatus = false;
    private Dismissed dismissed;
    private String interstitialAdId = "";
    private String bannerAdId = "";
    //private String nativeAdId = "";
    private int interval = 3;
    private int bannerLayId;

    private boolean loaded = false;
    private boolean withClick;



    public interface Dismissed {
        void onclick();
    }


    public AudienceNetwork(Activity activity) {
        this.activity = activity;
    }

    public AudienceNetwork build() {
        loadInterstitialAd();
        loadBannerAd(bannerLayId);
        return this;
    }

    public AudienceNetwork init() {
        AudienceNetworkAds.initialize(activity);
        return this;
    }

    public void show(Dismissed dismissed) {
        withClick = true;
        this.dismissed = dismissed;
        showInterstitialAd();
    }

    public void show() {
        withClick = false;
        showInterstitialAd();
    }

    public AudienceNetwork setFullScreenStatus(Boolean status) {
        this.fullScreenStatus = status;
        return this;
    }
    public AudienceNetwork setBannerStatus(Boolean status) {
        this.bannerStatus = status;
        return this;
    }

    public boolean isLoaded(){
        return loaded;
    }


    public AudienceNetwork setInterstitialId(String interstitialId) {
        this.interstitialAdId = interstitialId;
        return this;
    }

    public AudienceNetwork setBannerId(String bannerId) {
        this.bannerAdId = bannerId;
        return this;
    }

    /*public AudienceNetwork setNativeId(String nativeId) {
        this.nativeAdId = nativeId;
        return this;
    }*/




    public AudienceNetwork setClick(int interval) {
        this.interval = interval;
        return this;
    }


    public AudienceNetwork setBannerLayoutId(int bannerLayoutId) {
        this.bannerLayId = bannerLayoutId;
        return this;
    }


    private void loadInterstitialAd() {
        if (fullScreenStatus) {
            interstitialAd = new InterstitialAd(activity, interstitialAdId);
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    if (withClick) AudienceNetwork.this.dismissed.onclick();
                    interstitialAd.loadAd();
                }

                @Override
                public void onError(Ad ad, AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    loaded = true;
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build()
            );

        }
    }


    private void showInterstitialAd() {
        if (fullScreenStatus) {
            if (counter == interval) {
                if (interstitialAd.isAdLoaded()){
                    interstitialAd.show();
                }else {
                    if (withClick) AudienceNetwork.this.dismissed.onclick();
                    loadInterstitialAd();
                }
                counter = 1;
            } else {
                counter++;
                if (withClick) AudienceNetwork.this.dismissed.onclick();
            }
            Log.d(TAG, "Current counter : " + counter);
        }
    }

    private void loadBannerAd(int bannerLayoutId){
        if (bannerStatus){
            AdView adView = new AdView(activity, bannerAdId, AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = activity.findViewById(bannerLayoutId);
            adContainer.addView(adView);
            adView.loadAd();
        }
    }


}



