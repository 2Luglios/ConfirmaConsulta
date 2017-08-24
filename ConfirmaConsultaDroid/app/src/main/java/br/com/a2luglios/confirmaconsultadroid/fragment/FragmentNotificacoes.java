package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.a2luglios.confirmaconsultadroid.R;

/**
 * Created by ettoreluglio on 14/08/17.
 */

public class FragmentNotificacoes extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View agenda =
                inflater.inflate(R.layout.fragment_notificacoes,
                        container, false);

        return agenda;
    }
}

//    @Override
//    protected void onResume() {
//        super.onResume();
//
////        carregaLista();
//    }

//    private void carregaLista() {
//        BancoUtil bancoUtil = new BancoUtil(this);
//        ConsultaDao dao = new ConsultaDao(bancoUtil);
//        List<Consulta> consultas = dao.list();
//        bancoUtil.close();
//
//        ListView listaConsultas = (ListView) findViewById(R.id.listaConsultas);
//        ConsultaAdapter adapter = new ConsultaAdapter(this, consultas);
//        listaConsultas.setAdapter(adapter);
//    }


//
//        setContentView(R.layout.activity_backup_layout);
//
//        final ListView listaConsultas = (ListView) findViewById(R.id.listaConsultas);
//
//        listaConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//        listaConsultas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                registerForContextMenu(listaConsultas);
//                return false;
//            }
//        });
//
//        Button addConsulta = (Button) findViewById(R.id.addConsulta);
//        addConsulta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PrincipalActivity.this, "Novo", Toast.LENGTH_LONG).show();
//            }
//        });