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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.adapter.SpinnerGenericoAdapter;
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

    private boolean selecionouEspecialidades;
    private boolean selecionouMedicos;
    private boolean selecionouConsultorios;

    private ProgressBar progressLoagind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_busca, null);

        progressLoagind = (ProgressBar) v.findViewById(R.id.progressLoading);

        especialidades = (Spinner) v.findViewById(R.id.spinnerEspecialidades);
        especialidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selecionouEspecialidades = true;
                selecionouMedicos = false;
                selecionouConsultorios = false;

                buscarConsultorios(especialidades.getSelectedItem().toString());
                buscarMedicos(especialidades.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        consultorios = (Spinner) v.findViewById(R.id.spinnerConsultorios);
        consultorios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selecionouMedicos = false;
                selecionouConsultorios = true;
                buscarMedicos(especialidades.getSelectedItem().toString(),
                        ((Consultorio)consultorios.getSelectedItem()).getHash());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        medicos = (Spinner) v.findViewById(R.id.spinnerMedicos);
        medicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selecionouMedicos = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buscarEspecialidades();

        Button btnIrParaCalendario = (Button) v.findViewById(R.id.btnIrParaCalendario);
        btnIrParaCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();

                FragmentCalendario calendario = new FragmentCalendario();
                if ( especialidades.getSelectedItem() != null &&
                        ( medicos.getSelectedItem() != null || consultorios.getSelectedItem() != null)) {
                    calendario.setEspecialidade(especialidades.getSelectedItem().toString());
                    calendario.setMedico((Usuario)medicos.getSelectedItem());
                    calendario.setConsultorio((Consultorio)consultorios.getSelectedItem());
                }

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_place, calendario);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    private void buscarEspecialidades() {
        listaEspecialidades = new ArrayList<>();
        new FirebaseUtilDB().readRTDBPlain("especialidades",
                new FirebaseUtilDB.FirebaseRTDBUpdate() {
                    @Override
                    public void updateMensagem(Object obj) {
                        listaEspecialidades.add(obj.toString());
                        especialidades.setAdapter(new SpinnerGenericoAdapter(getContext(),
                                listaEspecialidades));

                        progressLoagind.setIndeterminate(false);
                    }
                });
    }

    private void buscarConsultorios(String especialidade) {
        listaConsultorios = new ArrayList<>();
        new FirebaseUtilDB().readRTDBConsultorios(especialidade, new FirebaseUtilDB.FirebaseRTDBUpdateLista<Consultorio>() {
            @Override
            public void updateConsultas(List lista) {
                listaConsultorios = lista;
                consultorios.setAdapter(new SpinnerGenericoAdapter(getContext(),
                        listaConsultorios));

                progressLoagind.setIndeterminate(false);
            }
        });
    }

    private void buscarMedicos(String especialidades) {
        new FirebaseUtilDB().readRTDBMedicos(null, especialidades,
                new FirebaseUtilDB.FirebaseRTDBUpdateLista<Usuario>() {
            @Override
            public void updateConsultas(List<Usuario> lista) {
                listaMedicos = lista;
                medicos.setAdapter(new SpinnerGenericoAdapter(getContext(), listaMedicos));

                progressLoagind.setIndeterminate(false);
            }
        });
    }

    private void buscarMedicos(String especialidades, String consultorio) {
        new FirebaseUtilDB().readRTDBMedicos(consultorio, especialidades,
                new FirebaseUtilDB.FirebaseRTDBUpdateLista<Usuario>() {
            @Override
            public void updateConsultas(List<Usuario> lista) {
                listaMedicos = lista;
                medicos.setAdapter(new SpinnerGenericoAdapter(getContext(), listaMedicos));

                progressLoagind.setIndeterminate(false);
            }
        });
    }

}
