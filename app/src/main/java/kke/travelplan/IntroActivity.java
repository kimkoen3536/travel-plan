package kke.travelplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IntroActivity extends Activity {
    private Runnable irun = new Runnable() {
        public void run() {
            Intent i = new Intent(IntroActivity.this, LoginActivity.class);
            finish();
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        Handler h = new Handler();
        h.postDelayed(irun, 3000);
    }

}
