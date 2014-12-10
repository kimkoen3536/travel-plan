package kke.travelplan;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

import static android.widget.AdapterView.AdapterContextMenuInfo;

public class MyPlansFragment extends Fragment {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private ListView planListView;

    public MyPlansFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_plans, container, false);
        planListView = (ListView) rootView.findViewById(R.id.plan_list_view);

        registerForContextMenu(planListView);

        planListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plan plan = (Plan) planListView.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), PlanItemsActivity.class);
                System.out.println("plan.getId() ::::::::::::::::::::::::::::::"+plan.getId());
                i.putExtra("id", plan.getId());
                startActivity(i);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListView();
    }

    private void refreshListView() {
        final PlanListAdapter adapter = new PlanListAdapter(getActivity());
        planListView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPlans(adapter);
            }
        }).start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "수정");
        menu.add(0, 2, 0, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Plan plan = (Plan) planListView.getItemAtPosition(info.position);
        final int id = plan.getId();
        switch (item.getItemId()) {
            case 1:
                Intent i = new Intent(getActivity(), EditPlanActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                return true;
            case 2:
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setMessage(plan.getTitle() + " 계획을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        deletePlan(id);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                refreshListView();
                                            }
                                        });
                                    }
                                }).start();

                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.plan_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id==R.id.action_new){
            Intent intent = new Intent(getActivity(), AddPlanActivity.class);
            startActivity(intent);

        }
        return false;
    }

    public List<Plan> loadPlans(final PlanListAdapter adapter) {
        String url = App.urlPrefix + "/plan/list.tpg";
        JsonResponse resp = JsonHttpUtil.get(url);
        final List<Plan> plans = new ArrayList<Plan>();
        List<Map<String, Object>> list = (List<Map<String, Object>>) resp.get("plans");
        for (Map<String, Object> planMap : list) {
            Plan plan = new Plan();
            plan.setId((Integer) planMap.get("id"));
            plan.setTitle((String) planMap.get("title"));
            plan.setLocation((String) planMap.get("location"));
            plan.setStartDate(DateFormats.parseDate((String) planMap.get("startDate")));
            plan.setEndDate(DateFormats.parseDate((String) planMap.get("endDate")));
            plan.setPublic_((Boolean) planMap.get("public_"));
            plan.setNumLikes((Integer) planMap.get("numLikes"));
            plans.add(plan);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Plan p : plans) {
                    adapter.add(p);
                }
                adapter.notifyDataSetChanged();
            }
        });
        return plans;
    }

    public void deletePlan(int id) {
        String url = App.urlPrefix + "/plan/delete.tpg";
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("id", id);
        String json = JsonHttpUtil.json(map);
        JsonResponse resp = JsonHttpUtil.post(url, json);

    }
}
