package kke.travelplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class PlanListItemAdapter extends ArrayAdapter<Plan> {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private final Context context;

    public PlanListItemAdapter(Context context) {
        super(context, R.layout.plan_list_item_view);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Plan plan = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.plan_list_item_view, parent, false);
        TextView planNameTextView = (TextView) itemView.findViewById(R.id.plan_name_textview);
        planNameTextView.setText(plan.getName());
        TextView planDateRangeTextView = (TextView) itemView.findViewById(R.id.plan_date_range_textview);
        String startDate = df.format(plan.getStartDate());
        String endDate = df.format(plan.getEndDate());
        if (startDate.equals(endDate)) {
            planDateRangeTextView.setText(startDate);
        } else {
            planDateRangeTextView.setText(startDate + " ~ " + endDate);
        }
        return itemView;
    }
}
