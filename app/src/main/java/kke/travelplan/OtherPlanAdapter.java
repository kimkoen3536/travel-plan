package kke.travelplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OtherPlanAdapter extends ArrayAdapter<Plan> {
    public OtherPlanAdapter(Context context) {
        super(context, R.layout.adapter_other_plan);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = getContext();
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.adapter_other_plan, parent, false);

        TextView planNameText = (TextView) rootView.findViewById(R.id.plan_name_text);
        TextView periodText = (TextView) rootView.findViewById(R.id.period_text);
        TextView accountNameText = (TextView) rootView.findViewById(R.id.account_name_text);
        TextView likeCountText = (TextView) rootView.findViewById(R.id.like_count_text);

        Plan plan = getItem(position);
        planNameText.setText(plan.getTitle());
        periodText.setText(plan.getPeriodText());
        accountNameText.setText(plan.getAccountName());
        likeCountText.setText(String.valueOf(plan.getNumLikes()));
        return rootView;
    }
}
