package kke.travelplan;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.DateSpinnerHelper;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

/**
 * Created by K.eun on 2014-12-17.
 */
public class FavoritesPlanActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner yearSpinner;

    private Spinner monthSpinner;

    private Spinner daySpinner;

    private ArrayAdapter<Integer> yearAdapter;

    private ArrayAdapter<Integer> monthAdapter;

    private ArrayAdapter<Integer> dayAdapter;
    private ListView planItemListView;
    private ImageButton ic_action;

    private Plan plan;

    DateSpinnerHelper helper;

    Intent intent = getIntent();
    String plan_date;
    //int plan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Application app = this.getApplication();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();
        plan_date = intent.getStringExtra("plan_date");
        final int plan_id = getIntent().getIntExtra("plan_id", -1);
        final int user_id = getIntent().getIntExtra("user_id", -1);
        System.out.println("get_plan_date : " + plan_date);
        System.out.println("get_plan_id : " + plan_id);
        System.out.println("get_plan_user_id : " + user_id);

        setContentView(R.layout.activity_plan_items);
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        yearSpinner.setOnItemSelectedListener(this);
        monthSpinner.setOnItemSelectedListener(this);
        daySpinner.setOnItemSelectedListener(this);


        yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);
        yearSpinner.setAdapter(yearAdapter);

        monthAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);
        monthSpinner.setAdapter(monthAdapter);

        dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);
        daySpinner.setAdapter(dayAdapter);
        System.out.println("dayAdapter ::: " + dayAdapter);
        planItemListView = (ListView) findViewById(R.id.plan_item_list_view);

        final PlanItemListAdapter itemAdapter = new PlanItemListAdapter(this);
        planItemListView.setAdapter(itemAdapter);

        loadPlan(plan_id);

        planItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = (Place) planItemListView.getItemAtPosition(position);
                Intent i = new Intent(app, PlaceCheckActivity.class);
                i.putExtra("id", place.getId());
                startActivity(i);
            }
        });

    }

    public void loadPlan(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("c");
                PlanService service = PlanService.getInstance();
                plan = service.get(id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("d");
                        setTitle(plan.getTitle());
                        helper = new DateSpinnerHelper(plan.getStartDate(),plan.getEndDate());
                        invalidateDateAdapters();
                        refreshListView();

                    }
                });
            }
        }).start();
    }



    public void invalidateDateAdapters(){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        Date selectedDate = helper.getSelectedDate();
        cal.setTime(selectedDate);
        Log.d("PlanItemsActivity", "invalidateDateAdapters(): " + DateFormats.date.format(selectedDate));
        plan_date =  DateFormats.date.format(selectedDate);


        System.out.println("plan_dateaaaaaaa ::::" + plan_date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        yearAdapter.clear();
        for (int i = helper.getYearFrom(); i<=helper.getYearTo(); i++){
            yearAdapter.add(i);
        }
        yearAdapter.notifyDataSetChanged();

        yearSpinner.setSelection(year - helper.getYearFrom());

        monthAdapter.clear();
        for (int i = helper.getMonthFrom(); i<=helper.getMonthTo();i++){
            monthAdapter.add(i+1);
        }
        monthAdapter.notifyDataSetChanged();
        monthSpinner.setSelection(month - helper.getMonthFrom());


        dayAdapter.clear();
        for (int i = helper.getDayFrom(); i<= helper.getDayTo(); i++){
            dayAdapter.add(i);
        }
        dayAdapter.notifyDataSetChanged();
        daySpinner.setSelection(day - helper.getDayFrom());

    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int year = (Integer) yearSpinner.getSelectedItem();
        int month = (Integer) monthSpinner.getSelectedItem();
        int day = (Integer) daySpinner.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day);
        Date date = cal.getTime();
        Log.d("PlanItemsActivity", "onItemSelected(): " + DateFormats.date.format(date));
        helper.setSelectedDate(date);
        invalidateDateAdapters();
        refreshListView();
    }

    private void refreshListView() {
        //    Date selectedDate = helper.getSelectedDate();
        //  plan_date =   DateFormats.date.format(selectedDate);
        //   System.out.println ("plan_date ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_date);
        final PlanItemListAdapter adapter = new PlanItemListAdapter(this);

        // if(adapter.get_plan_date == plan_date);
        planItemListView.setAdapter(adapter);

        // }
        Intent intent = getIntent();
        final int plan_id = intent.getIntExtra("plan_id",0);
        Calendar cal = Calendar.getInstance();

        System.out.println ("plan_id ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_id);
        System.out.println ("plan_date22 ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_date);


        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPlaces(adapter, plan_id ,plan_date);
            }
        }).start();
    }


   public void onResume() {
        super.onResume();


        //refreshListView();
    }




    public List<Place> loadPlaces(final PlanItemListAdapter adapter,int plan_id, String plan_date) {
        Intent intent = getIntent();
        System.out.println("plan_date ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_date);
        System.out.println("plan_id2 ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_id);
        String url = App.urlPrefix + "/place/get.tpg?plan_id=" + plan_id + "&plan_date=" + plan_date;
        System.out.println("url ::::::::: " + url);
        JsonResponse resp = JsonHttpUtil.get(url);
        final List<Place> places = new ArrayList<Place>();
        List<Map<String, Object>> list = (List<Map<String, Object>>) resp.get("place");
        for (Map<String, Object> placeMap : list) {
            Place place = new Place();
            place.setPlan_id((Integer)placeMap.get("plan_id"));
            place.setPlan_date((DateFormats.parseDate((String) placeMap.get("plan_date"))));
            place.setName((String)placeMap.get("name"));
            place.setType((String)placeMap.get("type"));
            place.setId((Integer)placeMap.get("id"));
            place.setAddress((String)placeMap.get("address"));
            place.setRoad_address((String)placeMap.get("road_address"));
            place.setMap_x((Integer)placeMap.get("map_x"));
            place.setMap_y((Integer)placeMap.get("map_y"));
            places.add(place);
            System.out.println("places :::::: " + places);
            System.out.println("places(0) :::::: " + places.get(0).toString());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Place p : places) {
                    adapter.add(p);
                }
                adapter.notifyDataSetChanged();
            }
        });
        return places;
    }

    public boolean loadFavorites(int user_id, int plan_id) {
        String url = App.urlPrefix + "/favorites/get.tpg?user_id=" + user_id + "&plan_id=" + plan_id;
        final JsonResponse resp = JsonHttpUtil.get(url);
        Map<String, Object> map = (Map<String, Object>) resp.get("favorites");
        System.out.println("loadFavorites_map : " + map);
        boolean likes_check = false;
        if (map != null) {
            likes_check = true;
        }
        return likes_check;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void mapButtonOnClick(final View view) {
        Intent i = new Intent(FavoritesPlanActivity.this, PlaceMapActivity.class);
        Intent intent = getIntent();
        int plan_id = intent.getIntExtra("plan_id",0);
        i.putExtra("plan_id", plan_id);
        System.out.println("plan_date???????" + plan_date);
        i.putExtra("plan_date", plan_date);
        startActivity(i);
    }
}