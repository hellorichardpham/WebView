package com.pham.richard.webview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.pham.richard.webview.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReaderActivity extends ActionBarActivity {
    static public String WEBPAGE_URL = "";
    static final public String PREF_URL = "restore_url";
    static final public String WEBPAGE_NOTHING = "about:blank";
    WebView myWebView;
    static final public String LOG_TAG = "webview_example";
    static final private String CNN_URL = "www.cnn.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Bundle extras = getIntent().getExtras();
        WEBPAGE_URL = extras.getString("TargetWebPage");
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Binds the Javascript interface
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        myWebView.loadUrl(WEBPAGE_URL);
        myWebView.loadUrl("javascript:alert(\"Hello\")");
    }

    public void onClickShare(View v) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, myWebView.getUrl());
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onPause() {
        Method pause = null;
        try {
            pause = WebView.class.getMethod("onPause");
        } catch (SecurityException e) {
            // Nothing
        } catch (NoSuchMethodException e) {
            // Nothing
        }
        if (pause != null) {
            try {
                pause.invoke(myWebView);
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
        } else {
            // No such method.  Stores the current URL.
            String suspendUrl = myWebView.getUrl();
            SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
            SharedPreferences.Editor ed = settings.edit();
            ed.putString(PREF_URL, suspendUrl);
            ed.commit();
            // And loads a URL without any processing.
            myWebView.clearView();
            myWebView.loadUrl(WEBPAGE_NOTHING);
        }
        super.onPause();
    }

    public class JavaScriptInterface {
        Context mContext; // Having the context is useful for lots of things,
        // like accessing preferences.

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(WEBPAGE_URL) || Uri.parse(url).getHost().equals(CNN_URL)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site,
            // so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

}
