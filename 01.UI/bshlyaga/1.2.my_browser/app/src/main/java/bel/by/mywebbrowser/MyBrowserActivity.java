package bel.by.mywebbrowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by borino on 09.02.2015.
 */
public class MyBrowserActivity extends Activity {

    private static final CharSequence URL_PREFIX = "http://";
    final static int RESULT_CODE = 999555;
    final static int REQUEST_CODE = 777888;

    @InjectView(R.id.ET_url_line)
    EditText editTextUrlLine;

    @InjectView(R.id.web_view_MY)
    WebView webView;

    @InjectView(R.id.Button_history)
    Button buttonHistory;

    @InjectView(R.id.Button_history_back)
    Button buttonHistoryBack;

    @InjectView(R.id.Button_history_forward)
    Button buttonHistoryForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browser);
        ButterKnife.inject(this);
        initListener();
        initComponents();
        if (null != getIntent()) {
            onNewIntent(getIntent());
        }
        refreshButtonState();
    }

    private void initComponents() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new MyBrowserWebClient(this));
    }

    private void initListener() {
        editTextUrlLine.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //load page if push enter on virtual keyboard
                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                            goClick();
                            return true;
                        }
                        return false;
                    }
                }
        );
    }

    public void refreshButtonState() {
        buttonHistory.setEnabled(webView.canGoBack() || webView.canGoForward());
        buttonHistoryBack.setEnabled(webView.canGoBack());
        buttonHistoryForward.setEnabled(webView.canGoForward());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_CODE && requestCode == REQUEST_CODE) {
            int steps = intent.getIntExtra(String.valueOf(R.string.INT_STEP), 0);
            webView.goBackOrForward(steps);
        }
    }

    @OnClick(R.id.Button_url)
    void goClick() {
        boolean error = true;
        CharSequence url = editTextUrlLine.getText();
        if (!TextUtils.isEmpty(url)) {
            if (!url.toString().contains(URL_PREFIX)) {
                url = TextUtils.concat(URL_PREFIX, url);
            }
            if (Patterns.WEB_URL.matcher(url).matches()) {
                error = false;
                editTextUrlLine.setText(url);
                goToTheEndOfHistory();
                webView.loadUrl(url.toString());
            }
        }
        if (error) {
            Toast.makeText(this, getText(R.string.ERROR_PLEASE_ENTER_URL), Toast.LENGTH_LONG).show();
        }
    }

    private void goToTheEndOfHistory() {
        while (webView.canGoForward()) {
            webView.goForward();
        }
    }

    @OnClick(R.id.Button_history)
    void histClick() {
        DB.getInstance().setHistoryList(this.webView.copyBackForwardList());
        startActivityForResult(new Intent(this, MyHistoryListActivity.class), REQUEST_CODE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.Button_history_back)
    void historyBackClick() {
        webView.goBack();
    }

    @OnClick(R.id.Button_history_forward)
    void historyForward() {
        webView.goForward();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            editTextUrlLine.setText(uri.toString());
            goClick();
        }
    }

}
