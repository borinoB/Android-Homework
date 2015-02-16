package bel.by.mywebbrowser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by borino on 11.02.2015.
 */
public class MyHistoryListAdapter extends BaseAdapter implements ListAdapter {

    private WebBackForwardList historyList;

    MyHistoryListAdapter(WebBackForwardList historyList) {
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.getSize();
    }

    @Override
    public WebHistoryItem getItem(int position) {
        return historyList.getItemAtIndex(position);
    }

    @Override
    public long getItemId(int position) {
        return historyList.getCurrentIndex();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null != view) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_history_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        WebHistoryItem item = getItem(position);
        holder.name.setText(item.getTitle());
        holder.url.setText(item.getUrl());
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.history_item_note)
        TextView name;
        @InjectView(R.id.history_item_url)
        TextView url;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
