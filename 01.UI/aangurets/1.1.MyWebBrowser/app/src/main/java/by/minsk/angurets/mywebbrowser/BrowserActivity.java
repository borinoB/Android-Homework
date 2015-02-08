package by.minsk.angurets.mywebbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by aangurets on 07.02.2015.
 */
public class BrowserActivity extends Activity {

    private WebView mWebView;
    private Button mBackButton;
    private Button mForwardButton;
    public static final String ADRESS = "adress";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout);

        mWebView = (WebView) findViewById(R.id.webView);
        mBackButton = (Button) findViewById(R.id.back_button);
        mForwardButton = (Button) findViewById(R.id.forward_button);
        mWebView.getSettings().setJavaScriptEnabled(true);

        class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }

        String mAdress = getIntent().getStringExtra(ADRESS);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(mAdress);

    }

    public void moveOnBack(View view) {
        if (mWebView.canGoBack()) {
            mForwardButton.setEnabled(true);
            mWebView.goBack();
        } else {
            mBackButton.setEnabled(false);
            onShowCantBack(view);
        }
    }

    public void goForward(View view) {
        if (mWebView.canGoForward()) {
            mBackButton.setEnabled(true);
            mWebView.goForward();
        } else {
            mForwardButton.setEnabled(false);
            onShowCantForward(view);
        }
    }

    public void onShowCantBack(View view) {
        Toast toast = Toast.makeText(
                getApplicationContext(), R.string.show_back, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void onShowCantForward(View view) {
        Toast toast = Toast.makeText(
                getApplicationContext(), R.string.show_forward, Toast.LENGTH_SHORT);
        toast.show();
    }

}
