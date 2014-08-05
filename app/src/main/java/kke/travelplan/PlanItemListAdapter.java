package kke.travelplan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlanItemListAdapter extends ArrayAdapter<PlanItem> {
    private final Context context;

    public PlanItemListAdapter(Context context) {
        super(context, R.layout.adapter_plan_item);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       PlanItem item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_plan_item, parent, false);
        ImageView typeImageView = (ImageView)itemView.findViewById(R.id.type_image_view);
        TextView nameTextView = (TextView)itemView.findViewById(R.id.name_text_view);
        TextView addressTextView = (TextView)itemView.findViewById(R.id.address_text_view);
        typeImageView.setImageResource(R.drawable.ic_launcher);
        nameTextView.setText(item.getName());
        addressTextView.setText(item.getAddress());
        return itemView;
    }
}
