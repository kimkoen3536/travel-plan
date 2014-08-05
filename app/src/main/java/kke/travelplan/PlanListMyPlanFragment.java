package kke.travelplan;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlanListMyPlanFragment extends Fragment {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private ListView planListView;

    public PlanListMyPlanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plan_list_my_plan, container, false);
        planListView = (ListView) rootView.findViewById(R.id.plan_list_view);

        registerForContextMenu(planListView);

        PlanListItemAdapter adapter = new PlanListItemAdapter(getActivity());
        planListView.setAdapter(adapter);
        try {
            adapter.add(new Plan("제주도 여행기", df.parse("2014-07-01"), df.parse("2014-07-20")));
            adapter.add(new Plan("어디", df.parse("2014-08-01")));
        } catch (ParseException e) {
            Log.e("error", "error", e);
        }
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "수정");
        menu.add(0, 2, 0, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
}
