package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import kke.travelplan.R;

public class PlanItemListActivity extends Activity {
    private Spinner dateSpinner;

    private ListView planItemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_item_list);
        dateSpinner = (Spinner) findViewById(R.id.date_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("2014-08-01");
        adapter.add("2014-08-02");
        adapter.add("2014-08-03");
        adapter.add("2014-08-04");
        adapter.add("2014-08-05");
        dateSpinner.setAdapter(adapter);

        planItemListView = (ListView) findViewById(R.id.plan_item_list_view);

        PlanItemListAdapter itemAdapter = new PlanItemListAdapter(this);
        itemAdapter.add(new PlanItem("한라산","제주도서귀포시","관광") );
        itemAdapter.add(new PlanItem("까페베네","계룡엄사면","음식"));
        planItemListView.setAdapter(itemAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plan_item_list, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
