package kke.travelplan;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FavoritesFragment extends Fragment implements ListView.OnItemClickListener {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private SearchView searchView;

    private ListView listView;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other_plans, container, false);
        searchView = (SearchView) rootView.findViewById(R.id.search_view);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        OtherPlanAdapter adapter = new OtherPlanAdapter(getActivity());
        adapter.add(new Plan("제주도 여행", parse("2014-01-01"), parse("2015-03-17"), "purluno", 100));
        adapter.add(new Plan("제도 여행", parse("2013-07-01"), parse("2015-03-17"), "purluo", 10));
        adapter.add(new Plan("제주 여행", parse("2012-12-31"), parse("2015-03-17"), "purlno", 1));
        adapter.add(new Plan("주도 여행", parse("2011-05-05"), parse("2015-03-17"), "puruno", 0));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    public Date parse(String s) {
        try {
            return df.parse(s);
        } catch (ParseException e) {
            Log.e("OtherPlansFragment", "날짜 문자열 변환 실패", e);
            return new Date();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), OtherPlanActivity.class);
        startActivity(i);
    }
}
