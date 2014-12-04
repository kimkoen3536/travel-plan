package kke.travelplan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import kke.travelplan.R;
import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

public class AddTransportActivity extends Activity {

    private EditText desEditText;
    private EditText depEditText;
    private EditText memoEditText;
    private EditText durationEditText;


    private Spinner typeSpinner;

    private String transportType;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport);

         desEditText = (EditText)findViewById(R.id.des_text);
         depEditText = (EditText)findViewById(R.id.dep_text);
         memoEditText = (EditText)findViewById(R.id.trans_memo_text);
         durationEditText = (EditText)findViewById(R.id.duration_text);

      typeSpinner = (Spinner) findViewById(R.id.trans_type_spinner);
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
        transportType = typeSpinner.getSelectedItem().toString();


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

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("계획을 저장 중입니다.");
        dialog.setCancelable(false);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                postTransport(dialog);
            }
        }).start();
    }


    public boolean postTransport(final ProgressDialog progressDialog) {
        Intent intent = getIntent();
        int plan_id = intent.getIntExtra("plan_id",0);
        String plan_date = intent.getStringExtra("plan_date");
        System.out.println("plan_id6 ::::::::::::::::::::" + plan_id);
        Transport transport = new Transport();
        transport.setPlan_id(plan_id);
        transport.setPlan_date(DateFormats.parseDate(plan_date));
        transport.setType(transportType);
        transport.setDeparture(depEditText.getText().toString());
        transport.setDestination(desEditText.getText().toString());
        transport.setDuration(durationEditText.getText().toString());
        transport.setMemo(memoEditText.getText().toString());
        String url = App.urlPrefix+ "/transport/add.tpg";
        final JsonResponse resp = JsonHttpUtil.post(url, transport.toJson());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (resp.isSuccess()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddTransportActivity.this, "계획을 성공적으로 등록했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddTransportActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return resp.isSuccess();
    }
}