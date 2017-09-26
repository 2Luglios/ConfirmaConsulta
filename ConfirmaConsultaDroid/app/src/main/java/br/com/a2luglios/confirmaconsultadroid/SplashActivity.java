package br.com.a2luglios.confirmaconsultadroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        getSupportActionBar().hide();

        //        // Habilita Persistência
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bundle bundle = getIntent().getExtras();
//                if ( bundle != null ) {
//                    Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
//                    intent.putExtra("from", bundle.getString("from"));
//                    intent.putExtra("body", bundle.getString("body"));
//                    startActivity(intent);
//                } else {
                    Intent principal = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(principal);
//                }
                finish();
            }
        }).start();

    }
}
