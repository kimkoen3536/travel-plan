package kke.travelplan;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;


public class PlaceMapActivity extends NMapActivity implements OnMapStateChangeListener,OnMapViewTouchEventListener,OnCalloutOverlayListener {

    public static final String API_KEY="f6a3d3afbeaa8b2ca66e9573dae65d2a";
    NMapView mMapView = null;
    NMapController mMapController = null;
    LinearLayout MapContainer;
    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager;
    OnStateChangeListener onPOIdataStateChangeListener = null;

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
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        int markerID = NMapPOIflagType.PIN;

        NMapPOIdata poiData = new NMapPOIdata(2,mMapViewerResourceProvider);

        poiData.addPOIitem(127.0630205,37.5091300, "Pizza 777-111",markerID,0);
        poiData.addPOIitem(127.061,37.51,"pizza 123-345",markerID,0);
        poiData.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData,null);

        poiDataOverlay.showAllPOIdata(0);

        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
      //  mOverlayManager.setOnCalloutOverlayListener((OnCalloutOverlayListener)this);


     //   setContentView(R.layout.activity_place_map);
    }


 //   public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay,NMapPOIitem item){
   //     Toast.makeText(PlaceMapActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
    //}

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
       // if(errorInfo == null) {
        //    mMapController.setMapCenter(new NGeoPoint(126.9738371, 37.5666091), 11);
       // }
       // else {
    //            android.util.Log.e("NMap", "onMapInitHandler: error = " + errorInfo.toString());
         //   }
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

    @Override
    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay nMapOverlay, NMapOverlayItem overlayItem, Rect rect) {
        return new NMapCalloutBasicOverlay(nMapOverlay, overlayItem, rect);
    }
}
