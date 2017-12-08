package org.elaya.mytodo.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.elaya.mytodo.R;

/**
 * This activity displays a help page.
 */

public class HelpActivity extends Activity {

    /***
     * Setup the GUI
     *
     * The help page is displayed in a WebView.
     * Set the location of the help page
     *
     * @param savedInstanceState Not used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        WebView lHelp= findViewById(R.id.helpHtml);
        Intent lIntent=getIntent();
        WebViewClient lClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        };

        lHelp.setWebViewClient(lClient);
        lHelp.loadUrl("file:///android_asset/"+lIntent.getStringExtra("page")+".html");
    }

    /**
     * On top there is a "close" button. When pressing this
     * button this method is called and the activity is closed.
     *
     * @param pButton Not used.
     */
    public void closePressed(View pButton){
        finish();
    }
}
