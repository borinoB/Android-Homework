package by.minsk.angurets.mywebbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by aangurets on 07.02.2015.
 */

public class BrowserActivity extends ActionBarActivity {

    private EditText mUri;
    private ImageButton mOpenButton;
    private ImageButton mBackButton;
    private ImageButton mForwardButton;
    private ImageButton mHistoryButton;
    private String mTempURI;
    private WebView mWebView;
    public static final String PREFIX = "http://";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout);

        mUri = (EditText) findViewById(R.id.uri);
        mOpenButton = (ImageButton) findViewById(R.id.open_button);
        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mForwardButton = (ImageButton) findViewById(R.id.forward_button);
        mHistoryButton = (ImageButton) findViewById(R.id.history_button);
        mWebView = (WebView) findViewById(R.id.webView);

        mBackButton.setEnabled(false);
        mForwardButton.setEnabled(false);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryStorage.addToHistoryItems(mUri.getText().toString());
                mBackButton.setEnabled(true);
                if (mUri.getText().toString().startsWith(PREFIX)) {
                    mWebView.loadUrl(mUri.getText().toString());
                } else {
                    mWebView.loadUrl(PREFIX + mUri.getText().toString());
                }
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForwardButton.setEnabled(true);
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    mBackButton.setEnabled(false);
                    Toast.makeText(BrowserActivity.this, "You can't go back!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                } else {
                    mForwardButton.setEnabled(false);
                    Toast.makeText(BrowserActivity.this, "You can't go forward!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowserActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mTempURI = mWebView.getUrl();
        state.putString("url", mTempURI);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTempURI = savedInstanceState.getString("url");
        mWebView.loadUrl(mTempURI);
        mUri.setText(mTempURI);
    }
}