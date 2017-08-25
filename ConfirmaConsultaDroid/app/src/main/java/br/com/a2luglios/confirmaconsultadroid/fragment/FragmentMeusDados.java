package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import br.com.a2luglios.confirmaconsultadroid.PrincipalActivity;
import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseLoginInterface;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilAuth;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.MapContato;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;
import br.com.a2luglios.confirmaconsultadroid.util.Preferencias;

/**
 * Created by ettoreluglio on 14/08/17.
 */

public class FragmentMeusDados extends Fragment {

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoRepitaSenha;
    private EditText campoCPF;
    private EditText campoRG;
    private CheckBox checkSouMedico;
    private EditText campoCRM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View meusDados =
                inflater.inflate(R.layout.fragment_meus_dados,
                        container, false);

        campoNome = (EditText) meusDados.findViewById(R.id.campoNome);
        campoEmail = (EditText) meusDados.findViewById(R.id.campoEmail);
        campoSenha = (EditText) meusDados.findViewById(R.id.campoSenha);
        campoRepitaSenha = (EditText) meusDados.findViewById(R.id.campoRepitaSenha);
        campoCPF = (EditText) meusDados.findViewById(R.id.campoCPF);
        campoRG = (EditText) meusDados.findViewById(R.id.campoRG);
        checkSouMedico = (CheckBox) meusDados.findViewById(R.id.checkSouMedico);
        campoCRM = (EditText) meusDados.findViewById(R.id.campoCRM);

        Usuario usuario = new Preferencias(getContext()).getUsuario();
        if ( usuario.getToken().equals("") ) {
            campoNome.setText(usuario.getNome());

            MapContato contato = new MapContato();
            contato.setMapa(usuario.getContato());

            campoEmail.setText(contato.getEmail());
            campoCPF.setText(usuario.getCpf());
            campoRG.setText(usuario.getRg());
            checkSouMedico.setChecked(usuario.isEhMedico());
            campoCRM.setText(usuario.getCRM());

            if ( checkSouMedico.isChecked() ) {
                campoCRM.setEnabled(true);
            } else {
                campoCRM.setEnabled(false);
            }
        }

        checkSouMedico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( b ) {
                    campoCRM.setEnabled(true);
                    Toast.makeText(getContext(), "Sou Médico", Toast.LENGTH_LONG).show();
                } else {
                    campoCRM.setEnabled(false);
                    Toast.makeText(getContext(), "Não Sou Médico", Toast.LENGTH_LONG).show();
                }
            }
        });

        setHasOptionsMenu(true);

        return meusDados;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_meus_dados_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_meus_dados_options_salvar:
                if ( campoSenha.getEditableText().toString().equals(
                        campoRepitaSenha.getEditableText().toString())) {
                    new FirebaseUtilAuth(getActivity()).criarUsuario(
                            campoEmail.getEditableText().toString(),
                            campoSenha.getEditableText().toString(),
                            new FirebaseLoginInterface() {
                                @Override
                                public void toDo(FirebaseUser user) {
                                    Usuario usuario = new Usuario();
                                    usuario.setNome(campoNome.getEditableText().toString());
                                    MapContato contato = new MapContato();
                                    contato.setEmail(campoEmail.getEditableText().toString());
                                    usuario.setContato(contato.getMapa());
                                    usuario.setCpf(campoCPF.getEditableText().toString());
                                    usuario.setRg(campoRG.getEditableText().toString());
                                    usuario.setEhMedico(checkSouMedico.isChecked());
                                    usuario.setCRM(campoCRM.getEditableText().toString());

                                    usuario.setConsultorios(new ArrayList<String>());
                                    usuario.getConsultorios().add("lalalalala");

                                    new Preferencias(getActivity()).setUsuario(usuario);

                                    new FirebaseUtilDB().saveOrUpdate("usuarios", usuario);

                                    startActivity(new Intent(getActivity(), PrincipalActivity.class));

                                    getActivity().finish();
                                }

                                @Override
                                public void erro(String txt) {
                                    Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "As senhas devem ser iguais...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_meus_dados_options_logout:
                new FirebaseUtilAuth(getActivity()).logout();
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
