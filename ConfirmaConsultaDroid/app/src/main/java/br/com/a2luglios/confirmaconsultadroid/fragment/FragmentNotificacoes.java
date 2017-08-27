package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

        ListView listNotificacoes = (ListView) agenda.findViewById(R.id.listNotificacoes);

        listNotificacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        listNotificacoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });


        return agenda;
    }
}
