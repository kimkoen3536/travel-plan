package kke.travelplan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kke.travelplan.util.DateFormats;


public class OtherPlanAdapter extends ArrayAdapter<Place> {
    private final Context context;

    String get_plan_date;

    public OtherPlanAdapter(Context context) {
        super(context, R.layout.adapter_other__plan);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Place place = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_other__plan, parent, false);
        TextView typeTextView = (TextView)itemView.findViewById(R.id.type_text_view);
        TextView nameTextView = (TextView)itemView.findViewById(R.id.name_text_view);
        TextView addressTextView = (TextView)itemView.findViewById(R.id.address_text_view);
        //    typeImageView.setImageResource(R.drawable.ic_launcher);
        nameTextView.setText(place.getName());
        addressTextView.setText(place.getRoad_address());
        typeTextView.setText(place.getType());
        get_plan_date = DateFormats.date.format(place.getPlan_date());
        return itemView;
    }



}