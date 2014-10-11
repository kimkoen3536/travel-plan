package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import kke.travelplan.R;

public class AddTransportActivity extends Activity {

    private Spinner typeSpinner;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport);
        typeSpinner = (Spinner) findViewById(R.id.place_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.adapter_spinner_item_18);
        adapter.add("버스");
        adapter.add("자동차");
        adapter.add("도보");
        adapter.add("기차");
        adapter.add("비행기");
        adapter.add("배");
        adapter.add("지하철");
        adapter.add("기타");
        typeSpinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_transport, menu);
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

    public void saveButtonOnClick(View view) {
        finish();
    }
}
