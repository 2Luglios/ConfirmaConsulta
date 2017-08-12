package br.com.a2luglios.confirmaconsultadroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Iterator;

import br.com.a2luglios.confirmaconsultadroid.util.SendMessage;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_layout);

        setContentView(R.layout.telacomabas);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
//            }
//        }).start();

        FirebaseMessaging.getInstance().subscribeToTopic("teste");

//        new SendMessage().sendMessageToFirebase("dNXRTYiFZBA:APA91bEtlWsQcGQhRwAQTzCvUBrKelzxCEv9H9v4AVR33vg3VVNhDzl7COdWTPgqZA2EDLmYiSxovvhh5ss4ka3DwjNSaHgu3cJbBk6I2DFUjSYHYY34jBkj-tUiDnCls0NoypzZv6-8%20dNXRTYiFZBA:APA91bEtlWsQcGQhRwAQTzCvUBrKelzxCEv9H9v4AVR33vg3VVNhDzl7COdWTPgqZA2EDLmYiSxovvhh5ss4ka3DwjNSaHgu3cJbBk6I2DFUjSYHYY34jBkj-tUiDnCls0NoypzZv6-8",
//                "foLPh5EATlg:APA91bGAHwLSmNOrzlyG7BUQ1xIU9jK1vquCgm5q1VkAknz7zmcJhXdrwlo_Xjy87XPzIzLlJFjz2n2djSlCsNO2HDgBxgcCfnexVxA-8rdDUEzU7BUhAK1mtrkErWAYkSH1QqqkQ6uk",
//                "Uma mensagem qualquer");

        /// Database Firebase

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("mensagens");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Mensagem m = next.getValue(Mensagem.class);
                    Log.d("" + next.getKey() , "" + m.getMensagem());

                    //Mensagem m = nome.getRef().getValue(Mensagem.class);
                    //Log.d("Splash", "Value is: " + m.getMensagem());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Splash", "Failed to read value.", error.toException());
            }
        });

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab Three");
        host.addTab(spec);
    }
}
