package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class PlanItemsActivity extends Activity {
    private Spinner dateSpinner;

    private ListView planItemListView;

    private Button contextMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_items);
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
        itemAdapter.add(new PlanItem("한라산", "제주도서귀포시", "관광"));
        itemAdapter.add(new PlanItem("까페베네", "계룡엄사면", "음식"));
        planItemListView.setAdapter(itemAdapter);

        contextMenuButton = (Button) findViewById(R.id.context_menu_button);
        registerForContextMenu(contextMenuButton);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.context_menu_button) {
            menu.add(0, 1, 0, "목적지 추가");
            menu.add(0, 2, 0, "이동수단 추가");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                return true;
            case 2:
                return true;
        }
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                openContextMenu(contextMenuButton);
                return true;
        }
        return true;
    }
}
