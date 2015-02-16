package bel.by.mywebbrowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by borino on 10.02.2015.
 */
public class MyBrowserWebClient extends WebViewClient {

    @InjectView(R.id.ET_url_line)
    EditText editText;

    private final MyBrowserActivity myBrowserActivity;

    public MyBrowserWebClient(MyBrowserActivity myBrowserActivity) {
        this.myBrowserActivity = myBrowserActivity;
        ButterKnife.inject(this, myBrowserActivity);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        editText.setText(url);
        myBrowserActivity.refreshButtonState();
    }
}
