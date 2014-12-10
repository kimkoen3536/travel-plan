package kke.travelplan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

public class AddPlanActivity extends Activity {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private EditText titleText;
    private EditText locationText;
    private TextView startDateText;
    private TextView endDateText;
    private CheckBox isPublicCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        titleText = (EditText) findViewById(R.id.title_text);
        locationText = (EditText) findViewById(R.id.location_text);
        startDateText = (TextView) findViewById(R.id.start_date_text);
        endDateText = (TextView) findViewById(R.id.end_date_text);
        isPublicCheckbox = (CheckBox) findViewById(R.id.is_public_checkbox);
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

    public void startDateButtonOnClick(View view) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(year, monthOfYear, dayOfMonth);
                startDateText.setText(df.format(cal.getTime()));

            }

        };
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE)).show();


    }

    public void endDateButtonOnClick(View view) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(year, monthOfYear, dayOfMonth);
                endDateText.setText(df.format(cal.getTime()));
            }
        };
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();

    }

    public void saveButtonOnClick(final View view) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("계획을 저장 중입니다.");
        dialog.setCancelable(false);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                postPlan(dialog);
            }
        }).start();


    }
    

    public boolean postPlan(final ProgressDialog progressDialog) {
        Plan plan = new Plan();
        plan.setTitle(titleText.getText().toString());
        plan.setLocation(locationText.getText().toString());
        plan.setStartDate(DateFormats.parseDate(startDateText.getText().toString()));
        plan.setEndDate(DateFormats.parseDate(endDateText.getText().toString()));
        plan.setPublic_(!isPublicCheckbox.isChecked());
        String url = App.urlPrefix + "/plan/add.tpg";
        final JsonResponse resp = JsonHttpUtil.post(url, plan.toJson());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (resp.isSuccess()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddPlanActivity.this, "계획을 성공적으로 등록했습니다.", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddPlanActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return resp.isSuccess();
    }
}
