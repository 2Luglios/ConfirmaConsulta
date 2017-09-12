package br.com.a2luglios.confirmaconsultadroid.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

import javax.crypto.spec.GCMParameterSpec;

import br.com.a2luglios.confirmaconsultadroid.MessageActivity;
import br.com.a2luglios.confirmaconsultadroid.modelo.Mensagem;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Context ctx;

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        AudioTrack track = generateTone(440, 500);
        track.play();
    }

    private AudioTrack generateTone(double freqHz, int durationMs)
    {
        int count = (int)(44100.0 * 2.0 * (durationMs / 1000.0)) & ~1;
        short[] samples = new short[count];
        for(int i = 0; i < count; i += 2){
            short sample = (short)(Math.sin(2 * Math.PI * i / (44100.0 / freqHz)) * 0x7FFF);
            samples[i + 0] = sample;
            samples[i + 1] = sample;
        }
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                count * (Short.SIZE / 8), AudioTrack.MODE_STATIC);
        track.write(samples, 0, count);
        return track;
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        ctx = getApplicationContext();

        /*
        *************************
        [google.c.a.c_l] - APELIDO
        [google.c.a.udt] - 0
        [google.sent_time] - 1505038034096
        [gcm.notification.android_channel_id] - CANAL
        [gcm.notification.e] - 1
        [google.c.a.c_id] - 5469712832079840235
        [google.c.a.ts] - 1505038034
        [gcm.notification.sound] - default
        [gcm.notification.title] - TITULO
        [gcm.n.e] - 1
        [from] - /topics/propaganda
        [from] - 42148264085
        [gcm.notification.sound2] - default
        [google.message_id] - 0:1505038034418379%7ca378bb7ca378bb
        [gcm.notification.body] - MENSAGEM
        [google.c.a.e] - 1
        [collapse_key] - br.com.a2luglios.confirmaconsultadroid
        [CHAVE1] - VALOR
        [CHAVE2] - 10
        *************************
         */

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("LoginActivity", "Refreshed token: " + refreshedToken);

        if (remoteMessage.getNotification() != null) {
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("from", remoteMessage.getFrom());
            intent.putExtra("body", remoteMessage.getNotification().getBody());
            startActivity(intent);
        }
    }

}
