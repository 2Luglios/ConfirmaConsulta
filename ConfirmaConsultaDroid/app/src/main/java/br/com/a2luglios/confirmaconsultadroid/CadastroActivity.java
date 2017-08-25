package br.com.a2luglios.confirmaconsultadroid;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.a2luglios.confirmaconsultadroid.fragment.FragmentAgenda;
import br.com.a2luglios.confirmaconsultadroid.fragment.FragmentMeusDados;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_layout);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_place, new FragmentMeusDados());
        transaction.commit();

    }
}
