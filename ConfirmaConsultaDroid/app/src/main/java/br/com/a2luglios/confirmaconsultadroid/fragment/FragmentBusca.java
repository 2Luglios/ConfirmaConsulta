package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.adapter.ConsultaAdapter;
import br.com.a2luglios.confirmaconsultadroid.adapter.SpinnerGenericoAdapter;
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

    private Spinner especialidades;
    private Spinner medicos;
    private Spinner consultorios;

    private RadioGroup radioGroupBuscaPor;

    private ProgressBar progressLoagind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_busca, null);

        progressLoagind = (ProgressBar) v.findViewById(R.id.progressLoading);

        especialidades = (Spinner) v.findViewById(R.id.spinnerEspecialidades);
        especialidades.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });

        medicos = (Spinner) v.findViewById(R.id.spinnerMedicos);
        medicos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });

        consultorios = (Spinner) v.findViewById(R.id.spinnerConsultorios);
        consultorios.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });

        buscarEspecialidades();

        radioGroupBuscaPor = (RadioGroup) v.findViewById(R.id.radioGroupBuscaPor);
        radioGroupBuscaPor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int radioId) {
                progressLoagind.setIndeterminate(true);

                switch(radioId) {
                    case R.id.radioEspecialidade:
                        especialidades.setEnabled(true);
                        medicos.setEnabled(false);
                        consultorios.setEnabled(false);

                        buscarEspecialidades();

                        break;
                    case R.id.radioMedico:
                        especialidades.setEnabled(true);
                        medicos.setEnabled(true);
                        consultorios.setEnabled(false);

                        buscarMedicos();

                        break;
                    case R.id.radioConsultorio:
                        especialidades.setEnabled(true);
                        medicos.setEnabled(true);
                        consultorios.setEnabled(true);

                        buscarConsultorios();

                        break;
                }
            }
        });

        Button btnIrParaCalendario = (Button) v.findViewById(R.id.btnIrParaCalendario);
        btnIrParaCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();

                FragmentCalendario calendario = new FragmentCalendario();
                calendario.setEspecialidade(especialidades.getSelectedItem().toString());
                calendario.setMedico((Usuario)medicos.getSelectedItem());
                calendario.setConsultorio((Consultorio)consultorios.getSelectedItem());

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_place, calendario);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    private void buscarConsultorios() {
        listaConsultorios = new ArrayList<>();
        new FirebaseUtilDB().readRTDB("consultorios", Consultorio.class, new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaConsultorios.add((Consultorio) obj);
                consultorios.setAdapter(new SpinnerGenericoAdapter(getContext(), listaConsultorios));

                progressLoagind.setIndeterminate(false);
            }
        });
    }

    private void buscarMedicos() {
        listaMedicos = new ArrayList<>();
        new FirebaseUtilDB().readRTDB("usuarios", Usuario.class, new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaMedicos.add((Usuario)obj);
                medicos.setAdapter(new SpinnerGenericoAdapter(getContext(), listaMedicos));

                progressLoagind.setIndeterminate(false);
            }
        });
    }

    private void buscarEspecialidades() {
        listaEspecialidades = new ArrayList<>();
        new FirebaseUtilDB().readRTDBPlain("especialidades", new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaEspecialidades.add(obj.toString());
                especialidades.setAdapter(new SpinnerGenericoAdapter(getContext(), listaEspecialidades));

                progressLoagind.setIndeterminate(false);
            }
        });
    }

}












