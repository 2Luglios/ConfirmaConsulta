package br.com.a2luglios.confirmaconsultadroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }).start();

    }

    public void datas () {
        SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        List<Integer> excecoesDosDiasDaSemana = Arrays.asList(1, 7);
        List<Integer> excecoesDosHorariosDoDia = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 13, 19, 20, 21, 22, 23);
        List<Integer> horariosUsadosNoDia = Arrays.asList(10, 11, 12, 15, 16);

        for ( int i = 24 ; i <= 24 ; i++ ) {
            c.set(Calendar.DAY_OF_MONTH, i);
            int diaDaSemana = c.get(Calendar.DAY_OF_WEEK);

            if ( excecoesDosDiasDaSemana.contains(diaDaSemana) ) {
                Log.d("Datas", "Data: " + sdfData.format(c.getTime().getTime()) + " - " + "XX");
            } else {
                Log.d("Datas", "Data: " + sdfData.format(c.getTime().getTime()) + " - " + "OK");
                for ( int j = 0 ; j < 24 ; j ++ ) {
                    c.set(Calendar.HOUR_OF_DAY, j);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    if ( !excecoesDosHorariosDoDia.contains(j) ) {
                        if ( horariosUsadosNoDia.contains(j) ) {
                            Log.d("Datas", "Hora: " + sdfHora.format(c.getTime().getTime()) + " - " + "XX");
                        } else {
                            Log.d("Datas", "Hora: " + sdfHora.format(c.getTime().getTime()) + " - " + "OK");
                        }
                    } else {
                        Log.d("Datas", "Hora: " + sdfHora.format(c.getTime().getTime()) + " - " + "XX");
                    }
                }
            }
        }
    }

}
