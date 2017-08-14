package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by ettoreluglio on 12/08/17.
 */

public class FirebaseUtilMessage {

    private static final String url = "https://us-central1-confirmaconsulta-63f26.cloudfunctions.net/sendMSG?";

    public void inscrever() {
        FirebaseMessaging.getInstance().subscribeToTopic("propaganda");
    }

    public void sendMessageToFirebase(final String origem, final String destino, final String mensagem) {

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    InputStream is = new URL(url +
                            "origem=" + origem +
                            "&destino=" + destino +
                            "&mensagem=" + URLEncoder.encode(mensagem)).openStream();
                    Scanner s = new Scanner(is);
                    while(s.hasNextLine()) {
                        Log.d("Linhas", s.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }
}






//        new FirebaseUtilMessage().sendMessageToFirebase("dNXRTYiFZBA:APA91bEtlWsQcGQhRwAQTzCvUBrKelzxCEv9H9v4AVR33vg3VVNhDzl7COdWTPgqZA2EDLmYiSxovvhh5ss4ka3DwjNSaHgu3cJbBk6I2DFUjSYHYY34jBkj-tUiDnCls0NoypzZv6-8%20dNXRTYiFZBA:APA91bEtlWsQcGQhRwAQTzCvUBrKelzxCEv9H9v4AVR33vg3VVNhDzl7COdWTPgqZA2EDLmYiSxovvhh5ss4ka3DwjNSaHgu3cJbBk6I2DFUjSYHYY34jBkj-tUiDnCls0NoypzZv6-8",
//                "foLPh5EATlg:APA91bGAHwLSmNOrzlyG7BUQ1xIU9jK1vquCgm5q1VkAknz7zmcJhXdrwlo_Xjy87XPzIzLlJFjz2n2djSlCsNO2HDgBxgcCfnexVxA-8rdDUEzU7BUhAK1mtrkErWAYkSH1QqqkQ6uk",
//                "Uma mensagem qualquer");