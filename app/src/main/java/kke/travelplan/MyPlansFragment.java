package kke.travelplan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyPlansFragment extends Fragment {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private ListView planListView;

    public MyPlansFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_plans, container, false);
        planListView = (ListView) rootView.findViewById(R.id.plan_list_view);

        registerForContextMenu(planListView);

        PlanListAdapter adapter = new PlanListAdapter(getActivity());
        planListView.setAdapter(adapter);
        try {
            adapter.add(new Plan("제주도 여행기", df.parse("2014-07-01"), df.parse("2014-07-20")));
            adapter.add(new Plan("어디", df.parse("2014-08-01")));
        } catch (ParseException e) {
            Log.e("error", "error", e);
        }
        planListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), PlanItemsActivity.class);
                startActivity(i);
            }
        });
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
        switch (item.getItemId()) {
            case 1:
                Intent i = new Intent(getActivity(), EditPlanActivity.class);
                startActivity(i);
                return true;
            case 2:
                new AlertDialog.Builder(getActivity())
                        .setMessage("'이거'를 정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제", null)
                        .setNegativeButton("취소", null)
                        .show();
        }
        return true;
    }
}
