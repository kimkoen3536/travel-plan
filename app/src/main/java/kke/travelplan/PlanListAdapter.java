package kke.travelplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class PlanListAdapter extends ArrayAdapter<Plan> {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private final Context context;

    public PlanListAdapter(Context context) {
        super(context, R.layout.adapter_plan);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Plan plan = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_plan, parent, false);
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
