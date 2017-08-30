package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.adapter.ConsultaAdapter;
import br.com.a2luglios.confirmaconsultadroid.adapter.ConsultorioAdapter;
import br.com.a2luglios.confirmaconsultadroid.adapter.EspecialidadeAdapter;
import br.com.a2luglios.confirmaconsultadroid.adapter.MedicoAdapter;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdate;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 25/08/17.
 */

public class FragmentBusca extends Fragment {

    private List<String> listaEspecialidades;
    private List<Usuario> listaMedicos;
    private List<Consultorio> listaConsultorios;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_busca, null);

        final Spinner especialidades = (Spinner) v.findViewById(R.id.spinnerEspecialidades);
        final Spinner medicos = (Spinner) v.findViewById(R.id.spinnerMedicos);
        final Spinner consultorios = (Spinner) v.findViewById(R.id.spinnerConsultorios);

        new FirebaseUtilDB().readRTDBPlain("especialidades", new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaEspecialidades.add(obj.toString());
                especialidades.setAdapter(new EspecialidadeAdapter(getContext(), listaEspecialidades));
            }
        });

        new FirebaseUtilDB().readRTDB("usuarios", Usuario.class, new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaMedicos.add((Usuario)obj);
                medicos.setAdapter(new MedicoAdapter(getContext(), listaMedicos));
            }
        });

        new FirebaseUtilDB().readRTDB("consultorios", Consultorio.class, new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaConsultorios.add((Consultorio) obj);
                consultorios.setAdapter(new ConsultorioAdapter(getContext(), listaConsultorios));
            }
        });

        Button btnIrParaCalendario = (Button) v.findViewById(R.id.btnIrParaCalendario);
        btnIrParaCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();

                FragmentCalendario calendario = new FragmentCalendario();

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_place, calendario);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listaEspecialidades = new ArrayList<>();
        listaMedicos = new ArrayList<>();
        listaConsultorios = new ArrayList<>();
    }
}
