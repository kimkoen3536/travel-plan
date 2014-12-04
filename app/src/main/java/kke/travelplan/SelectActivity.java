package kke.travelplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class SelectActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select, menu);
        return true;
    }



    public void addPlaceButtonOnClick(View view) {

        Intent i = new Intent(SelectActivity.this,AddPlaceActivity.class);
        Intent intent = getIntent();
            int plan_id = intent.getIntExtra("plan_id", 0);
        String plan_date = intent.getStringExtra("plan_date");
        i.putExtra("plan_id", plan_id);
        i.putExtra("plan_date",plan_date);
        System.out.println("planDate :::::::::::::::::::::::::" + plan_date);

        startActivity(i);
    }



    public void addTransportButtonOnClick(View view) {
        Intent i = new Intent(SelectActivity.this,AddTransportActivity.class);
        Intent intent = getIntent();
        int plan_id = intent.getIntExtra("plan_id", 0);
        String plan_date = intent.getStringExtra("plan_date");
        i.putExtra("plan_id", plan_id);
        i.putExtra("plan_date",plan_date);

        startActivity(i);

    }


}
