package by.minsk.angurets.mywebbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by aangurets on 07.02.2015.
 */
public class BrowserActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;
    private ImageButton mBackButton;
    private ImageButton mForwardButton;
    public static final String ADRESS = "adress";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout);

        mWebView = (WebView) findViewById(R.id.webView);
        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mForwardButton = (ImageButton) findViewById(R.id.forward_button);
        mBackButton.setOnClickListener(this);
        mForwardButton.setOnClickListener(this);

        mWebView.getSettings().setJavaScriptEnabled(true);

        String mAdress = getIntent().getStringExtra(ADRESS);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mAdress);

    }

    public void onShowCantBack(View view) {
        Toast toast = Toast.makeText(this, R.string.show_back, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void onShowCantForward(View view) {
        Toast toast = Toast.makeText(this, R.string.show_forward, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        if (view == mBackButton) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                mBackButton.setEnabled(false);
                onShowCantBack(view);
            }
        } else {
            if (mWebView.canGoForward()) {
                mWebView.goForward();
            } else {
                mForwardButton.setEnabled(false);
                onShowCantForward(view);
            }
        }

    }
}
