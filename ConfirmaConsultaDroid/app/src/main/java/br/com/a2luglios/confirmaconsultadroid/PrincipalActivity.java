package br.com.a2luglios.confirmaconsultadroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        final ListView listaConsultas = (ListView) findViewById(R.id.listaConsultas);

        listaConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        listaConsultas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerForContextMenu(listaConsultas);
                return false;
            }
        });

        Button addConsulta = (Button) findViewById(R.id.addConsulta);
        addConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PrincipalActivity.this, "Novo", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaLista();
    }

    private void carregaLista() {
        BancoUtil bancoUtil = new BancoUtil(this);
        ConsultaDao dao = new ConsultaDao(bancoUtil);
        List<Consulta> consultas = dao.list();
        bancoUtil.close();

        ListView listaConsultas = (ListView) findViewById(R.id.listaConsultas);
        ConsultaAdapter adapter = new ConsultaAdapter(this, consultas);
        listaConsultas.setAdapter(adapter);
    }
}
