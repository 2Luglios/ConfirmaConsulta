package br.com.a2luglios.confirmaconsultadroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("LoginActivity", "Refreshed token: " + refreshedToken);

        //sendRegistrationToServer(refreshedToken);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("idGoogle");

        // Escrevendo no json
//        myRef.setValue(refreshedToken);

    }

}
