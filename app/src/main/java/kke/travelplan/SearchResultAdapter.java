package kke.travelplan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<SearchResultItem> {
    private Context context;

    public SearchResultAdapter(Context context){
        super(context,R.layout.adapter_search_result);
        this.context = context ;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResultItem item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_search_result, parent, false);
        TextView nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
        TextView addressTextView = (TextView) itemView.findViewById(R.id.address_text_view);
        nameTextView.setText(item.getName());
        addressTextView.setText(item.getAddress());
        return itemView;
    }

}
