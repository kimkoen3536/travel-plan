package kke.travelplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class OtherPlanFragmentAdapter extends ArrayAdapter<Plan> {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private final Context context;

    public OtherPlanFragmentAdapter(Context context) {
        super(context, R.layout.adapter_other_plan_fragment);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Plan plan = getItem(position);
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.adapter_other_plan_fragment, parent, false);

        TextView planNameText = (TextView) rootView.findViewById(R.id.plan_name_text);
        planNameText.setText(plan.getTitle());
        TextView periodText = (TextView) rootView.findViewById(R.id.period_text);
        String startDate = df.format(plan.getStartDate());
        String endDate = df.format(plan.getEndDate());
        if (startDate.equals(endDate)) {
            periodText.setText(startDate);
        } else {
            periodText.setText(startDate + " ~ " + endDate);
        }
        TextView accountNameText = (TextView) rootView.findViewById(R.id.account_name_text);
        accountNameText.setText(plan.getAccountName());
        System.out.println("account_name ;" + plan.getAccountName());
        TextView likeCountText = (TextView) rootView.findViewById(R.id.like_count_text);
        likeCountText.setText(String.valueOf(plan.getNumLikes()));
        return rootView;
    }
}
