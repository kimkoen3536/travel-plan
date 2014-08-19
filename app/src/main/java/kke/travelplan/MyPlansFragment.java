package kke.travelplan;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

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

        final PlanListAdapter adapter = new PlanListAdapter(getActivity());
        planListView.setAdapter(adapter);
        planListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), PlanItemsActivity.class);
                startActivity(i);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPlans(adapter);
            }
        }).start();
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

    public List<Plan> loadPlans(final PlanListAdapter adapter) {
        String url = App.urlPrefix + "/plan/list.tpg";
        JsonResponse resp = JsonHttpUtil.get(url);
        List<Plan> plans = new ArrayList<Plan>();
        List<Map<String, Object>> list = (List<Map<String, Object>>) resp.get("plans");
        for (Map<String, Object> planMap : list) {
            Plan plan = new Plan();
            plan.setId((Integer) planMap.get("id"));
            plan.setName((String) planMap.get("title"));
            plan.setLocation((String) planMap.get("location"));
            plan.setStartDate(DateFormats.parseDate((String) planMap.get("startDate")));
            plan.setEndDate(DateFormats.parseDate((String) planMap.get("endDate")));
            plan.setPublic((Boolean) planMap.get("public_"));
            plan.setLikeCount((Integer) planMap.get("numLikes"));
            plans.add(plan);
        }
        for (Plan p : plans) {
            adapter.add(p);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        return plans;
    }
}
