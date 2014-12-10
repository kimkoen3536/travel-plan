package kke.travelplan;

import android.app.Activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.List;

import kke.travelplan.util.LocalItem;
import kke.travelplan.util.NaverApi;

import static android.app.PendingIntent.getActivity;


public class SearchPlaceActivity extends Activity {
    private EditText searchEditText;

    private Button searchButton;

    private LinearLayout recentSearchesLayout;

    private ListView recentSearchesListView;

    private LinearLayout searchResultsLayout;

    private ListView searchResultsListView;

    private SearchResultAdapter srAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_place);

        final Application app = this.getApplication();



        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        recentSearchesLayout = (LinearLayout) findViewById(R.id.recent_searches_layout);
        recentSearchesListView = (ListView) findViewById(R.id.recent_searches_list_view);
        searchResultsLayout = (LinearLayout) findViewById(R.id.search_results_layout);
        searchResultsListView = (ListView) findViewById(R.id.search_results_list_view);

        srAdapter = new SearchResultAdapter(this);

        searchResultsListView.setAdapter(srAdapter);
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResultItem srItem = (SearchResultItem) searchResultsListView.getItemAtPosition(position);
                System.out.println("MapX ::::::::: " + srItem.getMapX());
                Intent intent = getIntent();
                int plan_id = intent.getIntExtra("plan_id",0);
                String plan_date = intent.getStringExtra("plan_date");

                Intent i = new Intent(app, AddPlaceActivity.class);
                i.putExtra("plan_id", plan_id);
                i.putExtra("plan_date",plan_date);
                i.putExtra("Address", srItem.getAddress());
                i.putExtra("RoadAddress", srItem.getRoadAddress());
                i.putExtra("Name", srItem.getName());
                i.putExtra("MapX", srItem.getMapX());
                i.putExtra("MapY", srItem.getMapY());
                startActivity(i);
                finish();
            }
        });

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

        final String s = searchEditText.getText().toString();


           Thread t = new Thread(new Runnable() {
           String title = "";

           @Override
            public void run() {
                List<LocalItem> items = NaverApi.localSearch(s);
               final ArrayList<SearchResultItem> srItem = new ArrayList<SearchResultItem>();

               for(LocalItem item : items){
                    SearchResultItem inputItem = new SearchResultItem();

                    Log.d("searchPlaceActivity", "제목 : " + item.getTitle());
                    Log.d("searchPlaceActivity","주소 : " + item.getAddress());
                    Log.d("searchPlaceACtivity","도로명 :" + item.getRoadAddress());
                    Log.d("searchPlaceActivity" ,"x좌표 : " + item.getMapX());
                    Log.d("searchPlaceACtivity","y좌표 : " + item.getMapY());
                    Log.d("seachPlaceActivity"," ---- ");

                    title = item.getTitle().replace("<b>","");
                    title = title.replace("</b>", "");

                   inputItem.setName(title);
                   inputItem.setAddress(item.getAddress());
                   inputItem.setRoadAddress(item.getRoadAddress());
                   inputItem.setMapX(item.getMapX());
                   inputItem.setMapY(item.getMapY());
                   srItem.add(inputItem);
               }
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       for (SearchResultItem p : srItem) {
                           srAdapter.add(p);
                       }
                       srAdapter.notifyDataSetChanged();
                   }
               });
            }
        });

        t.start();

        recentSearchesLayout.setVisibility(View.GONE);
        searchResultsLayout.setVisibility(View.VISIBLE);

    }
}

