package com.pham.richard.webview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends ActionBarActivity {

    static final public String MYPREFS = "myprefs";
    static final public String PREF_URL = "restore_url";
    static final public String WEBPAGE_NOTHING = "about:blank";
    static public String TARGET_WEBPAGE = "";
    static final public String LOG_TAG = "webview_example";
    static final private String SAN_JOSE_MERCURY_URL = "http://www.mercurynews.com";
    static final private String SANTA_CRUZ_SENTINAL_URL = "http://www.santacruzsentinel.com";
    static final private String SF_GATE_URL = "http://www.sfgate.com";


    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*myWebView = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Binds the Javascript interface
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        myWebView.loadUrl(TARGET_WEBPAGE);
        myWebView.loadUrl("javascript:alert(\"Hello\")");
        */
    }

    public class JavaScriptInterface {
        Context mContext; // Having the context is useful for lots of things,
        // like accessing preferences.

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void myFunction(String args) {
            final String myArgs = args;
            Log.i(LOG_TAG, "I am in the javascript call.");
            runOnUiThread(new Runnable() {
                public void run() {
                    //Button v = (Button) findViewById(R.id.button1);
                    //v.setText(myArgs);
                }
            });
        }
    }


    /*When the button is clicked, retrieve the button ID from
    the button that was clicked. Send the URL to ReaderActivity
    based on which button was clicked.
     */
    public void onWebClick(View view) {
        Intent intent = new Intent(MainActivity.this, ReaderActivity.class);
        int REQUESTED_WEBPAGE = view.getId();
        switch(REQUESTED_WEBPAGE) {
            case R.id.MercuryButton:
                intent.putExtra("TargetWebPage", SAN_JOSE_MERCURY_URL);
                break;
            case R.id.SentinelButton:
                intent.putExtra("TargetWebPage", SANTA_CRUZ_SENTINAL_URL);
                break;

            case R.id.SFGateButton:
                intent.putExtra("TargetWebPage", SF_GATE_URL);
                break;
        }
        startActivity(intent);
    }


    @Override
    public void onPause() {
/*
        Method pause = null;
        try {
            //pause = WebView.class.getMethod("onPause");
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
        */
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
