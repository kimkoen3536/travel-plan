package kke.travelplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;


public class PlaceMapActivity extends NMapActivity implements NMapView.OnMapStateChangeListener,NMapView.OnMapViewTouchEventListener {

    public static final String API_KEY="f6a3d3afbeaa8b2ca66e9573dae65d2a";
    NMapView mMapView = null;
    NMapController mMapController = null;
    LinearLayout MapContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapContainer = (LinearLayout)findViewById(R.id.map);
        mMapView = new NMapView(this);
        mMapView.setApiKey(API_KEY);
        setContentView(mMapView);
        mMapView.setClickable(true);
        mMapView.setOnMapStateChangeListener(this);
        mMapView.setOnMapViewTouchEventListener(this);
        mMapView.setBuiltInZoomControls(true, null);
        mMapController=mMapView.getMapController();


     //   setContentView(R.layout.activity_place_map);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.place_map, menu);
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

    @Override
    public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
        if(errorInfo == null) {
            mMapController.setMapCenter(new NGeoPoint(126.9738371, 37.5666091), 11);
        }
        else {
                android.util.Log.e("NMap", "onMapInitHandler: error = " + errorInfo.toString());
            }
        }



    @Override
    public void onMapCenterChange(NMapView mapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView mapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView mapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView mapView, int i, int i2) {

    }

    @Override
    public void onLongPress(NMapView mapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView mapView) {

    }

    @Override
    public void onTouchDown(NMapView mapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView mapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView mapView, MotionEvent motionEvent, MotionEvent motionEvent2) {

    }

    @Override
    public void onSingleTapUp(NMapView mapView, MotionEvent motionEvent) {

    }
}
