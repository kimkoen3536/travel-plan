package kke.travelplan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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



public class PlanItemsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String[] actionNewTypes = {"목적지 추가"};

    private Spinner yearSpinner;

    private Spinner monthSpinner;

    private Spinner daySpinner;

    private  ArrayAdapter<Integer> yearAdapter;

    private ArrayAdapter<Integer> monthAdapter;

    private ArrayAdapter<Integer> dayAdapter;




    private ListView planItemListView;

    private Plan plan;
    private Place place;

    DateSpinnerHelper helper;

    Intent intent = getIntent();
    String plan_date;
    int plan_id;
    Date get_plan_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Application app = this.getApplication();

        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();
        plan_date = intent.getStringExtra("plan_date");
        System.out.println("get_plan_date : " + plan_date);



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
        System.out.println("a");
        int id = getIntent().getIntExtra("id", -1);
        System.out.println("b");
        loadPlan(id);
        System.out.println("e");
        planItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("f");
                Place place = (Place) planItemListView.getItemAtPosition(position);
                Intent i = new Intent(app,EditPlaceActivity.class);
                System.out.println("plan.editgetId() ::::::::::::::::::::::::::::::"+place.getId());
                i.putExtra("id", place.getId());
                startActivity(i);
            }
        });

        planItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                Place place = (Place) planItemListView.getItemAtPosition(position);
                final int i = place.getId();
                System.out.println("plan.deletegetId() ::::::::::::::::::::::::::::::"+i);

                AlertDialog.Builder dialog = new AlertDialog.Builder(PlanItemsActivity.this);
                dialog.setMessage(place.getName() + "계획을 삭제하시겟습니까?");
                //dialog.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                   // public void onClick(DialogInterface dialog, int which){
                    //    dialog.dismiss();
                    //}
                //});
                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                deletePlace(i);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshListView();
                                    }
                                });
                            }
                        }).start();
                    }
                });
                dialog.setNegativeButton("취소", null);
                dialog.show();

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plan_items, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_new) {

            Intent i = new Intent(PlanItemsActivity.this, AddPlaceActivity.class);
            Intent intent = getIntent();
                plan_id = intent.getIntExtra("id",0);
                i.putExtra("plan_id", plan_id);
            System.out.println("plan_date???????" + plan_date);
                i.putExtra("plan_date", plan_date);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        plan_id = intent.getIntExtra("id",0);
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


    public void deletePlace(int id) {
        String url = App.urlPrefix + "/place/delete.tpg";
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("id", id);
        String json = JsonHttpUtil.json(map);
        JsonResponse resp = JsonHttpUtil.post(url, json);

    }


    public void onResume() {
        super.onResume();


        //refreshListView();
    }




    public List<Place> loadPlaces(final PlanItemListAdapter adapter,int plan_id, String plan_date) {
        Intent intent = getIntent();
        System.out.println ("plan_date ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_date);
        System.out.println ("plan_id2 ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_id);
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



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void mapButtonOnClick(final View view) {
        Intent i = new Intent(PlanItemsActivity.this, PlaceMapActivity.class);
        Intent intent = getIntent();
        plan_id = intent.getIntExtra("id",0);
        i.putExtra("plan_id", plan_id);
        System.out.println("plan_date???????" + plan_date);
        i.putExtra("plan_date", plan_date);
        startActivity(i);


    }
}
