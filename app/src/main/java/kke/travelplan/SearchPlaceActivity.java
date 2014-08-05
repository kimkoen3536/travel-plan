package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import kke.travelplan.R;

public class SearchPlaceActivity extends Activity {
    private EditText searchEditText;

    private Button searchButton;

    private LinearLayout recentSearchesLayout;

    private LinearLayout searchResultsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        recentSearchesLayout = (LinearLayout) findViewById(R.id.recent_searches_layout);
        searchResultsLayout = (LinearLayout) findViewById(R.id.search_results_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_place, menu);
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

    public void searchButtonOnClick(View view) {
        recentSearchesLayout.setVisibility(View.GONE);
        searchResultsLayout.setVisibility(View.VISIBLE);
    }
}
