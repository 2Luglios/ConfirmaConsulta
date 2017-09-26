package br.com.a2luglios.confirmaconsultadroid;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Set;

import br.com.a2luglios.confirmaconsultadroid.fragment.FragmentAgenda;
import br.com.a2luglios.confirmaconsultadroid.fragment.FragmentMeusDados;
import br.com.a2luglios.confirmaconsultadroid.fragment.FragmentNotificacoes;

public class PrincipalActivity extends AppCompatActivity {

    private static final String[] permissions = {Manifest.permission.WRITE_CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_layout);

        // Cadastra no topico propaganda
        FirebaseMessaging.getInstance().subscribeToTopic("propaganda");

        // Autorização do APP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 101);
            }
        }

        chamaFragmentComTitulo("Agenda", new FragmentAgenda());

        ImageButton btnMeusDados = (ImageButton) findViewById(R.id.btnMeusDados);
        btnMeusDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chamaFragmentComTitulo("Meus Dados", new FragmentMeusDados());
            }
        });

        ImageButton btnAgenda = (ImageButton) findViewById(R.id.btnCalendario);
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaFragmentComTitulo("Agenda", new FragmentAgenda());
            }
        });

        ImageButton btnNotificacoes = (ImageButton) findViewById(R.id.btnNotificacoes);
        btnNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaFragmentComTitulo("Notificações", new FragmentNotificacoes());
            }
        });

    }

    private void chamaFragmentComTitulo(String titulo, Fragment fragment) {
        PrincipalActivity.this.setTitle(titulo);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_place, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
