package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.adapter.ConsultaAdapter;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdate;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;

/**
 * Created by ettoreluglio on 14/08/17.
 */

public class FragmentAgenda extends Fragment {

    private List<Consulta> consultas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View agenda =
                inflater.inflate(R.layout.fragment_agenda,
                        container, false);

        final ListView listConsultas = (ListView) agenda.findViewById(R.id.listConsultas);
        carregaLista(listConsultas);

        listConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Ir para detalher", Toast.LENGTH_LONG).show();
            }
        });

        registerForContextMenu(listConsultas);

        listConsultas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });

        return agenda;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        new MenuInflater(getContext()).inflate(R.menu.menu_long_agenda, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_long_cancel:
                Toast.makeText(getContext(), "Apertou o cancelar...", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void carregaLista(final ListView listConsultas) {
        new FirebaseUtilDB().readRTDB("consultas", Consulta.class, new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                Consulta consulta = (Consulta) obj;
                if ( consultas == null ) consultas = new ArrayList<>();
                consultas.add(consulta);
                listConsultas.setAdapter(new ConsultaAdapter(getContext(), consultas));
            }
        });
    }
}
