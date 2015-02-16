package bel.by.mywebbrowser;

import android.webkit.WebBackForwardList;

/**
 * Created by borino on 12.02.2015.
 */
public class DB {
    private DB() {
    }

    private static DB instance;

    public WebBackForwardList getHistoryList() {
        return historyList;
    }

    public void setHistoryList(WebBackForwardList historyList) {
        this.historyList = historyList;
    }

    private WebBackForwardList historyList;


    public static DB getInstance() {
        if (null == instance) instance = new DB();
        return instance;
    }


}
