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


public class AddPlaceActivity extends Activity {
    private  int REQ_CODE_PICK_PICTURE = 100;

    private Spinner typeSpinner;
    private ImageView galleryImageView;
    private EditText memoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        typeSpinner = (Spinner) findViewById(R.id.place_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("관광");
        adapter.add("숙박");
        adapter.add("음식");
        adapter.add("기타");
        typeSpinner.setAdapter(adapter);
        galleryImageView = (ImageView)findViewById(R.id.gallery_image_View);
        memoText =(EditText)findViewById(R.id.place_memo_text);
    }


    public void galleryButtonOnClick(View view) {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQ_CODE_PICK_PICTURE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                galleryImageView.setImageURI(data.getData());
            }
        }
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_place, menu);
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
        Intent i = new Intent(this,SearchPlaceActivity.class);
        startActivity(i);
    }

    public void addButtonOnClick(View view) {
     Intent i = new Intent (this,AddTransportActivity.class);
     startActivity(i);}

    public void saveButtonOnClick(View view) {
        finish();
    }


}
