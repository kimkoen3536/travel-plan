package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import kke.travelplan.R;
import kke.travelplan.util.LocalItem;
import kke.travelplan.util.NaverApi;

public class SearchPlaceActivity extends Activity {
    private EditText searchEditText;

    private Button searchButton;

    private LinearLayout recentSearchesLayout;

    private ListView recentSearchesListView;

    private LinearLayout searchResultsLayout;

    private ListView searchResultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        recentSearchesLayout = (LinearLayout) findViewById(R.id.recent_searches_layout);
        recentSearchesListView = (ListView) findViewById(R.id.recent_searches_list_view);
        searchResultsLayout = (LinearLayout) findViewById(R.id.search_results_layout);
        searchResultsListView = (ListView) findViewById(R.id.search_results_list_view);

        RecentSearchAdapter recentAdapter = new RecentSearchAdapter(this);
        recentAdapter.add(new SearchItem("한라산"));
        recentAdapter.add(new SearchItem("백두산"));
        recentAdapter.add(new SearchItem("북한산"));
        recentAdapter.add(new SearchItem("남해"));
        recentAdapter.add(new SearchItem("서해"));
        recentSearchesListView.setAdapter(recentAdapter);

        SearchResultAdapter resultAdapter = new SearchResultAdapter(this);
        resultAdapter.add(new SearchResultItem("한라산","제주도 땡땡땡땡 땡땡땡땡 1234"));
        resultAdapter.add(new SearchResultItem("백두산","백두산에있음"));
        resultAdapter.add(new SearchResultItem("남해","남해 좋아요"));
        searchResultsListView.setAdapter(resultAdapter);
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
            @Override
            public void run() {
                List<LocalItem> items = NaverApi.localSearch(s);
                for(LocalItem item : items){
                    Log.d("searchPlaceActivity","제목 : "+item.getTitle());
                    Log.d("searchPlaceActivity","주소 : " + item.getAddress());
                    Log.d("searchPlaceACtivity","도로명 :" + item.getRoadAddress());
                    Log.d("searchPlaceActivity" ,"x좌표 : " + item.getMapX());
                    Log.d("searchPlaceACtivity","y좌표 : " + item.getMapY());
                    Log.d("seachPlaceActivity"," ---- ");
                }
            }
        });
        t.start();
        recentSearchesLayout.setVisibility(View.GONE);
        searchResultsLayout.setVisibility(View.VISIBLE);
    }
}
