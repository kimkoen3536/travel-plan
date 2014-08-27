package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class OtherPlanActivity extends Activity {
    private Spinner dateSpinner;

    private ListView planItemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_plan);
        dateSpinner = (Spinner) findViewById(R.id.year_spinner);
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
    }
}
