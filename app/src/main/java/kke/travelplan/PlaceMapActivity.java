package kke.travelplan;


import android.content.Intent;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kke.travelplan.util.GeoTrans;
import kke.travelplan.util.GeoTransPoint;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;


public class PlaceMapActivity extends NMapActivity implements OnMapStateChangeListener,OnMapViewTouchEventListener,
        OnCalloutOverlayListener,LocationListener {

    public static final String API_KEY="f6a3d3afbeaa8b2ca66e9573dae65d2a";
    NMapView mMapView = null;
    NMapController mMapController = null;
    LinearLayout MapContainer;
    NMapViewerResourceProvider mMapViewerResourceProvider = null;

    NMapOverlayManager mOverlayManager;
    OnStateChangeListener onPOIdataStateChangeListener = null;

    NGeoPoint map = new NGeoPoint();

    Double map_x[];
    Double [] map_y;
    int size = 0;
    String [] address = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        final int plan_id = intent.getIntExtra("plan_id",0);
        System.out.println("map_plan_id :::::: " + plan_id);
        final String plan_date = intent.getStringExtra("plan_date");
        System.out.println("map_plan_date :::::: " + plan_date);

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

//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,(LocationListener)this);

        System.out.println("a");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("b");
                loadPlaces(plan_id ,plan_date);
            }
        }).start();
        System.out.println("c");

//
//       poiData.addPOIitem(126.908045, "Pizza 777-111",markerID,0);
//        poiData.addPOIitem(126.90347501,35.1688680,"pizza 123-345",markerID,1);
//        poiData.addPOIitem(127.3457442,36.3666380,"pizza 123-345",markerID,2);
        System.out.println("d");
        System.out.println("e");



        System.out.println("f");

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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void loadPlaces(int plan_id, String plan_date) {
        int markerID = NMapPOIflagType.PIN;
        Intent intent = getIntent();
        System.out.println ("plan_date ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_date);
        System.out.println ("plan_id2 ;;;;;;;;;;;;;;;;;;;;;;;;" + plan_id);
        String url = App.urlPrefix + "/place/get.tpg?plan_id=" + plan_id + "&plan_date=" + plan_date;
        System.out.println("url ::::::::: " + url);
        JsonResponse resp = JsonHttpUtil.get(url);
        final List<Place> places = new ArrayList<Place>();
        List<Map<String, Object>> list = (List<Map<String, Object>>) resp.get("place");

        size = list.size();
        NMapPOIdata poiData = new NMapPOIdata(size,mMapViewerResourceProvider);
        poiData.beginPOIdata(size);

        System.out.println("list사이즈 : " + list.size());
        map_x = new Double[size];
        map_y = new Double[size];
        address = new String[size];

        Double [] lat = new Double[size];
        Double [] lng = new Double[size];

        for(int i=0; i < list.size(); i++) {
            map_x[i] = Double.parseDouble(list.get(i).get("map_x").toString());
            map_y[i] = Double.parseDouble(list.get(i).get("map_y").toString());
            System.out.println("map_x : " + map_x[i]);
            GeoTransPoint oKA = new GeoTransPoint(map_x[i],map_y[i]);
            GeoTransPoint oGeo = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, oKA);
            lat[i] = oGeo.getX() * 1E6;
            lng[i] = oGeo.getY() * 1E6;
            address[i] = list.get(i).get("name").toString();

            System.out.println("list_내용 ::::" + lat[i] +" | " + lng[i] + " | " + address[i]);
        }


        NMapPOIdataOverlay poiDataOverlay = null;

        for(int i = 0; i < list.size(); i++) {
            System.out.println("poidataCount : " + poiData.count());
            map.set(lat[i].intValue(), lng[i].intValue());
            poiData.addPOIitem(map, address[i], markerID, i);
            System.out.println("poiData (" + i + ")" + poiData.getPOIitem(i));
                System.out.println("poidataCount1 : " + poiData.count());
            for(int j = 0; j < poiData.count(); j++) {
                System.out.println("poiData1 (" + j + ")" + poiData.getPOIitem(j));
            }
            poiData.endPOIdata();
            poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData,null);
        }




       // poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData,null);

        poiDataOverlay.showAllPOIdata(0);

        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

        //mOverlayManager.setOnCalloutOverlayListener((OnCalloutOverlayListener)this);



    }
}
