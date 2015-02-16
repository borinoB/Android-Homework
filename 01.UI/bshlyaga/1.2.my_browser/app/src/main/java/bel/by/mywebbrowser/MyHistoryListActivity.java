package bel.by.mywebbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by borino on 11.02.2015.
 */
public class MyHistoryListActivity extends Activity {


    @InjectView(R.id.history_list_url)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list_url);
        ButterKnife.inject(this);
        listView.setAdapter(new MyHistoryListAdapter(DB.getInstance().getHistoryList()));
        getIntent().getIntExtra(String.valueOf(R.string.CURRENT_POSITION), 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @OnItemClick(R.id.history_list_url)
    void onListItemClick(ListView l, View v, int clickPosition, long id) {

        int currentPosition = DB.getInstance().getHistoryList().getCurrentIndex();
        //if current position is same.
        int step = 0;
        Log.i("current position = ", String.valueOf(currentPosition));
        Log.i("current click position = ", String.valueOf(clickPosition));
        Log.i("count = ", String.valueOf(l.getAdapter().getCount()));
        //if current posotion and click same step =0
        if (currentPosition != clickPosition) {
            //if go back step >0 , else <0
            if (currentPosition < clickPosition) {
                step = currentPosition - clickPosition;
            } else {
                step = clickPosition - currentPosition;
            }
        }
        setResult(MyBrowserActivity.RESULT_CODE
                , new Intent(this, MyBrowserActivity.class).putExtra(String.valueOf(R.string.INT_STEP), step));
        super.finish();
    }
}
