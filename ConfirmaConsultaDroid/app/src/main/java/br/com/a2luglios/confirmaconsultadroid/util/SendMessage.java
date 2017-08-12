package br.com.a2luglios.confirmaconsultadroid.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by ettoreluglio on 12/08/17.
 */

public class SendMessage {

    private static final String url = "https://us-central1-confirmaconsulta-63f26.cloudfunctions.net/sendMSG?";

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
