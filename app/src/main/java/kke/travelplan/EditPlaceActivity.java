    package kke.travelplan;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.CheckBox;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.io.ByteArrayOutputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;

    import kke.travelplan.util.DateFormats;
    import kke.travelplan.util.JsonHttpUtil;
    import kke.travelplan.util.JsonResponse;


    /**
     * Created by K.eun on 2014-12-07.
     */

    public class EditPlaceActivity extends Activity {
        private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        private Place place = new Place();
        private TextView addressText;
        private  int REQ_CODE_PICK_PICTURE = 100;

        private Spinner typeSpinner;
        private ImageView galleryImageView;

        private String placeType;
        private EditText placeMemoText;
        private Uri imageUrl;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_place);

            Intent intent = getIntent();
            String Address = "";
            Address = intent.getStringExtra("Address");
            String RoadAddress = "";
            if(intent.getStringExtra("RoadAddress") != null) {
                RoadAddress = intent.getStringExtra("RoadAddress");
            }
            String Name = "";
            if(intent.getStringExtra("Name") != null) {
                Name = intent.getStringExtra("Name");
            }
            int MapX = 0;
            MapX = intent.getIntExtra("MapX", 0);
            int MapY = 0;
            MapY = intent.getIntExtra("MapY", 0);
            System.out.println("plan_id8 ::::::::::::::::::::" + intent.getIntExtra("plan_id",0));
            System.out.println("MapX :::::::::::::::::::::::::::::::::::::::::::" + MapX);

            String Address_result = "";
            if (intent.getStringExtra("RoadAddress") != null && intent.getStringExtra("Name") != null ) {
                Address_result = Name+"("+RoadAddress+")";
            }

            addressText = (TextView) findViewById(R.id.address_text_view);
            typeSpinner = (Spinner) findViewById(R.id.place_type_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
            adapter.add("관광");
            adapter.add("숙박");
            adapter.add("음식");
            adapter.add("기타");
            typeSpinner.setAdapter(adapter);
            placeType = typeSpinner.getSelectedItem().toString();


            galleryImageView = (ImageView)findViewById(R.id.gallery_image_View);
            placeMemoText =(EditText)findViewById(R.id.place_memo_text);
            addressText.setText(Address_result);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int id = getIntent().getIntExtra("id", -1);
                    loadPlace(id);
                }
            }).start();
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.edit_place, menu);
            return true;
        }

        public void galleryButtonOnClick(View view) {

            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, REQ_CODE_PICK_PICTURE);


        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQ_CODE_PICK_PICTURE) if (resultCode == Activity.RESULT_OK) {
                //               galleryImageView.setImageURI(data.getData());
                imageUrl = data.getData();
                galleryImageView.setImageURI(imageUrl);


            }
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
            Intent i = new Intent(this,SearchPlaceActivity.class);
            Intent intent = getIntent();
            int plan_id = intent.getIntExtra("plan_id",0);
            String plan_date = intent.getStringExtra("plan_date");
            i.putExtra("plan_id", plan_id);
            i.putExtra("plan_date",plan_date);
            startActivity(i);
            finish();
        }

        public void editButtonOnClick(final View view) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setMessage("계획을 저장 중입니다.");
            dialog.setCancelable(false);
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    postPlace(dialog);
                }
            }).start();
        }

        public boolean postPlace(final ProgressDialog progressDialog) {
            Intent intent = getIntent();
            int plan_id = intent.getIntExtra("plan_id",0);
            System.out.println("plan_id5 ::::::::::::::::::::" + plan_id);
            int MapX = 0;
            MapX = intent.getIntExtra("MapX", 0);
            int MapY = 0;
            MapY = intent.getIntExtra("MapY", 0);
            String Address;
            Address = intent.getStringExtra("Address");
            String RoadAddress;
            RoadAddress = intent.getStringExtra("RoadAddress");
            String Name;
            Name = intent.getStringExtra("Name");
            String plan_date = intent.getStringExtra("plan_date");

            byte[] b = new byte[0];

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrl);
                System.out.println("imageUrl ::::::::::: " + imageUrl);
                System.out.println("Bitmap ::::::::::: " + bm);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                b = byteArray.toByteArray();
                System.out.println("byte bitmap : ::::::::::::: " + b);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Place place = new Place();
            place.setPlan_id(plan_id);
            place.setPlan_date(DateFormats.parseDate(plan_date));
            place.setName(Name);
            place.setAddress(Address);
            place.setRoad_address(RoadAddress);
            place.setMap_x(MapX);
            place.setMap_y(MapY);
            place.setType(placeType);
            place.setPicture(b);
            place.setMemo(placeMemoText.getText().toString());
            String url = App.urlPrefix + "/place/add.tpg";
            final JsonResponse resp = JsonHttpUtil.post(url, place.toJson());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (resp.isSuccess()) {
                        progressDialog.dismiss();
                        Toast.makeText(EditPlaceActivity.this, "계획을 성공적으로 등록했습니다.", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(EditPlaceActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return resp.isSuccess();
        }



        public void loadPlace(int id) {
            PlaceService service = PlaceService.getInstance();
            place = service.get(id);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                 //   byte[] b = place.getPicture();
                 //   Bitmap image = BitmapFactory.decodeByteArray(b,0,b.length);
                    addressText.setText(place.getRoad_address());
                    placeMemoText.setText(place.getMemo());
                    addressText.setText(place.getName()+"("+place.getRoad_address()+")");
                //    galleryImageView.setImageBitmap(image);


                }
            });

        }
    }
