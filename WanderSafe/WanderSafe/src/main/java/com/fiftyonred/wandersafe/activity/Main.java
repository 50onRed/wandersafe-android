package com.fiftyonred.wandersafe.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fiftyonred.wandersafe.LocationProvider;
import com.fiftyonred.wandersafe.R;
import com.fiftyonred.wandersafe.service.WandersafeService;

public class Main extends Activity {

    private WebView mWebView;

    private LocationProvider locationProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = new LocationProvider(locationManager);

        final String url = locationProvider.getMapUrl();
        Log.d("wandersafe", url);

        mWebView = new WebView(this);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        this.setContentView(mWebView);

        Intent serviceIntent = new Intent(this, WandersafeService.class);
        startService(serviceIntent);
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
