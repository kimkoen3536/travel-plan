package kke.travelplan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kke.travelplan.R;

public class AddPlanActivity extends Activity {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private TextView startDateTextView;
    private TextView endDateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        startDateTextView = (TextView) findViewById(R.id.start_date_textview);
        endDateTextView = (TextView) findViewById(R.id.end_date_textview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_plan, menu);
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
    public void startDateButtonOnClick(View view){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(year,monthOfYear,dayOfMonth);
                startDateTextView.setText(df.format(cal.getTime()));

            }

        };
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE)).show();


    }
    public void endDateButtonOnClick(View view){

    }
    public void saveButtonOnClick(View view){
        finish();
    }
}
