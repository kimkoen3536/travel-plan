package kke.travelplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.DateSpinnerHelper;

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

    DateSpinnerHelper helper;

    Intent intent = getIntent();
    String plan_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


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

        planItemListView = (ListView) findViewById(R.id.plan_item_list_view);

        PlanItemListAdapter itemAdapter = new PlanItemListAdapter(this);
        itemAdapter.add(new PlanItem("한라산", "제주도서귀포시", "관광"));
        itemAdapter.add(new PlanItem("까페베네", "계룡엄사면", "음식"));
        planItemListView.setAdapter(itemAdapter);

        int id = getIntent().getIntExtra("id", -1);
        loadPlan(id);
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

            Intent i = new Intent(PlanItemsActivity.this, SelectActivity.class);
            Intent intent = getIntent();
                int plan_id = intent.getIntExtra("id",0);
                i.putExtra("plan_id", plan_id);
                i.putExtra("plan_date",plan_date);
            System.out.println("planid2 :::::::::::::::::::::::::" + plan_id);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadPlan(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PlanService service = PlanService.getInstance();
                plan = service.get(id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle(plan.getTitle());
                        helper = new DateSpinnerHelper(plan.getStartDate(),plan.getEndDate());
                        invalidateDateAdapters();
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


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
