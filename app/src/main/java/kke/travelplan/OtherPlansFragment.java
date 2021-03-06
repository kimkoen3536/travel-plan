package kke.travelplan;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

public class OtherPlansFragment extends Fragment implements ListView.OnItemClickListener {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private SearchView searchView;

    private ListView listView;
    private int getUser_id = 0;

    public OtherPlansFragment(int user_id) {
        // Required empty public constructor
        this.getUser_id = user_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other_plans, container, false);
        searchView = (SearchView) rootView.findViewById(R.id.search_view);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        OtherPlanFragmentAdapter adapter = new OtherPlanFragmentAdapter(getActivity());
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plan plan = (Plan) listView.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), OtherPlanActivity.class);
                System.out.println("plan.getId() ::::::::::::::::::::::::::::::"+plan.getId());
                i.putExtra("user_id", getUser_id);
                i.putExtra("favor_plan_id", plan.getId());
                i.putExtra("favor_user_id", plan.getUser_id());
                startActivity(i);
            }
        });

        searchView.setQueryHint("검색어를 입력하세요.");
        searchView.setOnQueryTextListener(queryTextListener);

        return rootView;
    }


    public void onResume() {
        super.onResume();
        refreshListView();
    }

    private void refreshListView() {
        final OtherPlanFragmentAdapter adapter = new OtherPlanFragmentAdapter(getActivity());
        listView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPlans(adapter, getUser_id);
            }
        }).start();
    }

    private void searchRefreshListView(String query) {
        final OtherPlanFragmentAdapter adapter = new OtherPlanFragmentAdapter(getActivity());
        listView.setAdapter(adapter);
        final String get_query = query;

        new Thread(new Runnable() {
            @Override
            public void run() {
                searchPlans(adapter, get_query);
            }
        }).start();
    }

    public List<Plan> loadPlans(final OtherPlanFragmentAdapter adapter, int user_id) {
        String url = App.urlPrefix + "/plan/list2.tpg?user_id=" + user_id;
        JsonResponse resp = JsonHttpUtil.get(url);
        final List<Plan> plans = new ArrayList<Plan>();
        List<Map<String, Object>> list2 = (List<Map<String, Object>>) resp.get("plans");
        for (Map<String, Object> planMap : list2) {
            Plan plan = new Plan();
            plan.setId((Integer) planMap.get("id"));
            plan.setUser_id((Integer) planMap.get("user_id"));
            plan.setTitle((String) planMap.get("title"));
            plan.setLocation((String) planMap.get("location"));
            plan.setStartDate(DateFormats.parseDate((String) planMap.get("startDate")));
            plan.setEndDate(DateFormats.parseDate((String) planMap.get("endDate")));
            plan.setPublic_((Boolean) planMap.get("public_"));
            plan.setAccountName((String)planMap.get("accountName"));
            System.out.println("account_name_other;;;;" +(String)planMap.get("account_name") );
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

    public Date parse(String s) {
        try {
            return df.parse(s);
        } catch (ParseException e) {
            Log.e("OtherPlansFragment", "날짜 문자열 변환 실패", e);
            return new Date();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), OtherPlanActivity.class);
        startActivity(i);
    }

    private OnQueryTextListener queryTextListener = new OnQueryTextListener(){

        @Override
        public boolean onQueryTextSubmit(String query) {
            searchRefreshListView(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    public List<Plan> searchPlans(final OtherPlanFragmentAdapter adapter, String query) {
        String url = App.urlPrefix + "/plan/search.tpg?query=" + query;
        System.out.println("query1 : " + query);
        JsonResponse resp = JsonHttpUtil.get(url);
        System.out.println("search_url : " + url);
        final List<Plan> plans = new ArrayList<Plan>();
        List<Map<String, Object>> list2 = (List<Map<String, Object>>) resp.get("plan");
        for (Map<String, Object> planMap : list2) {
            Plan plan = new Plan();
            plan.setId((Integer) planMap.get("id"));
            plan.setUser_id((Integer) planMap.get("user_id"));
            plan.setTitle((String) planMap.get("title"));
            plan.setLocation((String) planMap.get("location"));
            plan.setStartDate(DateFormats.parseDate((String) planMap.get("startDate")));
            plan.setEndDate(DateFormats.parseDate((String) planMap.get("endDate")));
            plan.setPublic_((Boolean) planMap.get("public_"));
            plan.setAccountName((String)planMap.get("accountName"));
            System.out.println("account_name_other;;;;" +(String)planMap.get("account_name") );
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
}
