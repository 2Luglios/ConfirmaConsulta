package br.com.a2luglios.confirmaconsultadroid.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Context ctx;

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        ctx = getApplicationContext();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("LoginActivity", "Refreshed token: " + refreshedToken);

        if (remoteMessage.getNotification() != null) {
            Log.d("Message", remoteMessage.getNotification().getBody());
        }
    }

}
