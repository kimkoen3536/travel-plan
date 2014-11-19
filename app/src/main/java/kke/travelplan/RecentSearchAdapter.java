package kke.travelplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecentSearchAdapter extends ArrayAdapter<SearchItem> {
    private final Context context;

    public RecentSearchAdapter(Context context) {
        super(context, R.layout.adapter_recent_search);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SearchItem item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_recent_search, parent, false);
        TextView keywordTextView = (TextView) itemView.findViewById(R.id.keyword_text_view);
        keywordTextView.setText(item.getKeyword());
        return itemView;
    }
}
