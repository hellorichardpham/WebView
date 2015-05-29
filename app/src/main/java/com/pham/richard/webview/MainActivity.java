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
    static final private String SAN_JOSE_MERCURY_URL = "http://www.mercurynews.com";
    static final private String SANTA_CRUZ_SENTINAL_URL = "http://www.santacruzsentinel.com";
    static final private String CNN_URL = "http://www.cnn.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

            case R.id.CNNButton:
                intent.putExtra("TargetWebPage", CNN_URL);
                break;
        }
        startActivity(intent);
    }


    @Override
    public void onPause() {
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
