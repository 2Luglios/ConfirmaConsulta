package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.PrincipalActivity;
import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseLoginInterface;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdate;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilAuth;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.MapContato;
import br.com.a2luglios.confirmaconsultadroid.modelo.MapEndereco;
import br.com.a2luglios.confirmaconsultadroid.modelo.Sexo;
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
    private EditText lembrete;
    private EditText estadoCivil;
    private EditText profissao;
    private EditText nomeMae;
    private EditText nomePai;
    private RadioGroup sexo;
    private DatePicker dataNascimento;
    private EditText campoCPF;
    private EditText campoRG;
    private CheckBox checkSouMedico;
    private EditText campoCRM;
    private Button btnEspecialidades;
    private Button btnPlanos;
    private Button btnConsultorios;
    private EditText miniCurriculum;

    private List<String> especialistas = new ArrayList<>();
    private List<String> planos = new ArrayList<>();
    private List<String> consultorios = new ArrayList<>();

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
        lembrete = (EditText) meusDados.findViewById(R.id.campoLembrete);
        estadoCivil = (EditText) meusDados.findViewById(R.id.campoEstadoCivil);
        profissao = (EditText) meusDados.findViewById(R.id.campoProfissao);
        nomeMae = (EditText) meusDados.findViewById(R.id.campoNomeMae);
        nomePai = (EditText) meusDados.findViewById(R.id.campoNomePai);
        sexo = (RadioGroup) meusDados.findViewById(R.id.radioSexo);
        dataNascimento = (DatePicker) meusDados.findViewById(R.id.dateNascimento);
        campoCPF = (EditText) meusDados.findViewById(R.id.campoCPF);
        campoRG = (EditText) meusDados.findViewById(R.id.campoRG);
        checkSouMedico = (CheckBox) meusDados.findViewById(R.id.checkSouMedico);
        campoCRM = (EditText) meusDados.findViewById(R.id.campoCRM);
        btnEspecialidades = (Button) meusDados.findViewById(R.id.btnEspecialidades);
        btnPlanos = (Button) meusDados.findViewById(R.id.btnPlanos);
        btnConsultorios = (Button) meusDados.findViewById(R.id.btnConsultorios);
        miniCurriculum = (EditText) meusDados.findViewById(R.id.campoMiniCurriculum);

        btnEspecialidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(especialistas);
            }
        });

        btnPlanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(planos);
            }
        });

        btnConsultorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(consultorios);
            }
        });

        Usuario usuario = new Preferencias(getContext()).getUsuario();
        if ( usuario.getToken().equals("") ) {
            MapContato contato = new MapContato();
            contato.setMapa(usuario.getContato());

            MapEndereco endereco = new MapEndereco();
            endereco.setMapa(usuario.getEndereco());

            Calendar nascimento = Calendar.getInstance();
            nascimento.setTimeInMillis(usuario.getNascimento());

            campoNome.setText(usuario.getNome());
            campoEmail.setText(contato.getEmail());
            lembrete.setText(usuario.getLembrete());
            estadoCivil.setText(usuario.getEstadoCivil());
            profissao.setText(usuario.getProfissao());
            nomeMae.setText(usuario.getNomeMae());
            nomePai.setText(usuario.getNomePai());
            if ( usuario.getSexo() == Sexo.Masculino ) sexo.check(R.id.sexoMasculino); else sexo.check(R.id.sexoFeminino);
            dataNascimento.init(nascimento.get(Calendar.YEAR), nascimento.get(Calendar.MONTH), nascimento.get(Calendar.DAY_OF_MONTH), null);
            campoCPF.setText(usuario.getCpf());
            campoRG.setText(usuario.getRg());
            checkSouMedico.setChecked(usuario.isEhMedico());
            campoCRM.setText(usuario.getCRM());
            miniCurriculum.setText(usuario.getMiniCurriculum());

            mudaEstado(checkSouMedico.isChecked());
        }

        checkSouMedico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mudaEstado(b);
            }
        });

        setHasOptionsMenu(true);

        return meusDados;
    }

    private void mudaEstado(boolean estado) {
        campoCRM.setEnabled(estado);
        btnEspecialidades.setEnabled(estado);
        btnPlanos.setEnabled(estado);
        btnConsultorios.setEnabled(estado);
        miniCurriculum.setEnabled(estado);
    }

    @Override
    public void onResume() {
        super.onResume();

        new FirebaseUtilDB().readRTDBPlain("especialidades", new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                especialistas.add((String)obj);
            }
        });

        new FirebaseUtilDB().readRTDBPlain("planos", new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                planos.add((String)obj);
            }
        });

        new FirebaseUtilDB().readRTDB("consultorios", Consultorio.class,new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                final Consultorio consultorio = (Consultorio) obj;
                consultorios.add(consultorio.getNome());
            }
        });
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

                                    MapEndereco endereco = new MapEndereco();
                                    endereco.setCep("");
                                    usuario.setEndereco(endereco.getMapa());

                                    usuario.setCpf(campoCPF.getEditableText().toString());
                                    usuario.setRg(campoRG.getEditableText().toString());
                                    usuario.setEhMedico(checkSouMedico.isChecked());
                                    usuario.setCRM(campoCRM.getEditableText().toString());

                                    usuario.setEspecialidades(new ArrayList<String>());
                                    usuario.getEspecialidades().add("lalalalala");

                                    usuario.setPlanos(new ArrayList<String>());
                                    usuario.getPlanos().add("lalalalala");

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

    private void showDialog(List<String> listToShow) {
        // TODO tem que trazer populado com o que o cara já escreveu...

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Lista");
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_generic_list, null);
        final ListView lista = (ListView) v.findViewById(R.id.lista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setAdapter(new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_multiple_choice, listToShow));
        dialog.setView(v);
        dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int a) {
                SparseBooleanArray checked = lista.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i)) selectedItems.add((String) lista.getAdapter().getItem(position));
                }

                String[] outputStrArr = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                }
                Toast.makeText(getContext(), "Selecionou: " + selectedItems.size() + " itens", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

}
