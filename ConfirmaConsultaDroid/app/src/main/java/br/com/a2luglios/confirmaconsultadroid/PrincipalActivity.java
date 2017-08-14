package br.com.a2luglios.confirmaconsultadroid;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.adapter.ConsultaAdapter;
import br.com.a2luglios.confirmaconsultadroid.dao.ConsultaDao;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_layout);

        PrincipalActivity.this.setTitle("Home");

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_place, new FragmentHome());
        transaction.commit();

        ImageButton btnHome = (ImageButton) findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrincipalActivity.this.setTitle("Home");

                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, new FragmentHome());
                transaction.commit();
            }
        });

        ImageButton btnMeusDados = (ImageButton) findViewById(R.id.meus_dados);
        btnMeusDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrincipalActivity.this.setTitle("Meus Dados");

                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, new FragmentMeusDados());
                transaction.commit();
            }
        });

        ImageButton btnAgenda = (ImageButton) findViewById(R.id.agenda);
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrincipalActivity.this.setTitle("Agenda");

                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, new FragmentAgenda());
                transaction.commit();
            }
        });

        ImageButton btnProfissionais = (ImageButton) findViewById(R.id.profissionais);
        btnProfissionais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrincipalActivity.this.setTitle("Profissionais");

                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, new FragmentProfissionais());
                transaction.commit();
            }
        });

        ImageButton btnNotificacoes = (ImageButton) findViewById(R.id.notificacoes);
        btnNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrincipalActivity.this.setTitle("Notificações");

                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, new FragmentNotificacoes());
                transaction.commit();
            }
        });

    }


}
