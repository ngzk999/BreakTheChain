package com.example.breakthechain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class webView extends AppCompatActivity {
    WebView webView;
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webview);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}