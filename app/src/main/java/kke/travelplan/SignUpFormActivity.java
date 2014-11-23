package kke.travelplan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

public class SignUpFormActivity extends Activity {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText passwordConfirmText;
    private EditText accountNameText;
    private TextView birthText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        emailText = (EditText) findViewById(R.id.email_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        passwordConfirmText = (EditText) findViewById(R.id.password_confirm_text);
        accountNameText = (EditText) findViewById(R.id.account_name_text);
        nameText = (EditText) findViewById(R.id.name_text);
        birthText = (TextView) findViewById(R.id.birth_text);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up_form, menu);
        return true;
    }

    public void birthButtonOnClick(View view) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(year, monthOfYear, dayOfMonth);
                birthText.setText(df.format(cal.getTime()));

            }

        };
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE)).show();
    }


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

    public void confirmButtonOnClick(final View view) {
        final ProgressDialog dialog = new ProgressDialog(this);
        if (passwordText != passwordConfirmText)
        {dialog.setMessage("비밀번호 입력이 같지 않습니다.");}
        else {
            dialog.setIndeterminate(true);
            dialog.setMessage("회원가입중입니다.");
            dialog.setCancelable(false);
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    postUser(dialog);
                }
            }).start();}}

    public boolean postUser(final ProgressDialog progressDialog) {
        User user = new User();
        user.setEmail(emailText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.setAccountName(accountNameText.getText().toString());
        user.setName(nameText.getText().toString());
        user.setBirthDate(DateFormats.parseDate(birthText.getText().toString()));
        String url = App.urlPrefix + "/user/add.tpg";
        final JsonResponse resp = JsonHttpUtil.post(url, user.toJson());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (resp.isSuccess()) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpFormActivity.this, "계획을 성공적으로 등록했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpFormActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return resp.isSuccess();
    }
}


