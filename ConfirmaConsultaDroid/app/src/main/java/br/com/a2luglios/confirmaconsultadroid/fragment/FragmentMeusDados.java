package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.PrincipalActivity;
import br.com.a2luglios.confirmaconsultadroid.R;
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

    private List<String> especialidades = new ArrayList<>();
    private List<String> planos = new ArrayList<>();
    private List<String> consultorios = new ArrayList<>();

    private List<String> selectedEspecialidadesItems;
    private List<String> selectedPlanosItems;
    private List<String> selectedConsultoriosItems;

    private Usuario usuario;

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
                showDialog(especialidades, selectedEspecialidadesItems);
            }
        });

        btnPlanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(planos, selectedPlanosItems);
            }
        });

        btnConsultorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(consultorios, selectedConsultoriosItems);
            }
        });

        usuario = new Preferencias(getContext()).getUsuario();
        if ( usuario.getNome() != null && !usuario.getNome().isEmpty() ) {
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
            if (usuario.getSexo() == Sexo.Masculino) sexo.check(R.id.sexoMasculino);
            else sexo.check(R.id.sexoFeminino);
            dataNascimento.init(nascimento.get(Calendar.YEAR), nascimento.get(Calendar.MONTH),
                    nascimento.get(Calendar.DAY_OF_MONTH), null);
            campoCPF.setText(usuario.getCpf());
            campoRG.setText(usuario.getRg());
            checkSouMedico.setChecked(usuario.isEhMedico());
            campoCRM.setText(usuario.getCRM());
            miniCurriculum.setText(usuario.getMiniCurriculum());

            selectedEspecialidadesItems = usuario.getEspecialidades();
            selectedPlanosItems = usuario.getPlanos();
            selectedConsultoriosItems = usuario.getConsultorios();

            mudaEstado(checkSouMedico.isChecked());
        } else {
            usuario = new Usuario();

            selectedConsultoriosItems = new ArrayList<>();
            selectedPlanosItems = new ArrayList<>();
            selectedEspecialidadesItems = new ArrayList<>();
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

        new FirebaseUtilDB().readRTDBPlain("especialidades", new FirebaseUtilDB.FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                especialidades.add((String) obj);
            }
        });

        new FirebaseUtilDB().readRTDBPlain("planos", new FirebaseUtilDB.FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                planos.add((String) obj);
            }
        });

        new FirebaseUtilDB().readRTDB("consultorios", Consultorio.class, new FirebaseUtilDB.FirebaseRTDBUpdate() {
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
        switch (item.getItemId()) {
            case R.id.menu_meus_dados_options_salvar:
                String senha = campoSenha.getEditableText().toString();
                String repitaSenha = campoRepitaSenha.getEditableText().toString();
                String email = campoEmail.getEditableText().toString();
                if (usuario.getHash() != null) {
                    salvarUsuario();
                    if ( senha != null && senha.equals(repitaSenha) && senha.length() > 0) {
                        new FirebaseUtilAuth(getActivity()).alteraSenha(senha);
                    }
                } else {
                    if (senha.equals(repitaSenha)) {
                        new FirebaseUtilAuth(getActivity()).criarUsuario(email, senha,
                                new FirebaseUtilAuth.FirebaseLoginInterface() {
                                    @Override
                                    public void onSuccess(FirebaseUser user) {
                                        salvarUsuario();
                                        getActivity().finish();
                                    }

                                    @Override
                                    public void onError(String txt) {
                                        Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "As senhas devem ser iguais...", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.menu_meus_dados_options_logout:
                new FirebaseUtilAuth(getActivity()).logout();
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarUsuario() {
        usuario.setNome(campoNome.getEditableText().toString());

        MapContato contato = new MapContato();
        contato.setEmail(campoEmail.getEditableText().toString());
        usuario.setContato(contato.getMapa());

        MapEndereco endereco = new MapEndereco();
        endereco.setCep("");
        usuario.setEndereco(endereco.getMapa());

        usuario.setLembrete(lembrete.getEditableText().toString());
        usuario.setEstadoCivil(estadoCivil.getEditableText().toString());
        usuario.setProfissao(profissao.getEditableText().toString());
        usuario.setNomeMae(nomeMae.getEditableText().toString());
        usuario.setNomePai(nomePai.getEditableText().toString());

        switch(sexo.getCheckedRadioButtonId()) {
            case R.id.sexoMasculino:
                usuario.setSexo(Sexo.Masculino);
                break;
            case R.id.sexoFeminino:
                usuario.setSexo(Sexo.Feminino);
                break;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, dataNascimento.getYear());
        calendar.set(Calendar.MONTH, dataNascimento.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, dataNascimento.getDayOfMonth());

        usuario.setNascimento(calendar.getTimeInMillis());
        usuario.setCpf(campoCPF.getEditableText().toString());
        usuario.setRg(campoRG.getEditableText().toString());
        usuario.setEhMedico(checkSouMedico.isChecked());
        usuario.setCRM(campoCRM.getEditableText().toString());

        usuario.setEspecialidades(selectedEspecialidadesItems);
        usuario.setPlanos(selectedPlanosItems);
        usuario.setConsultorios(selectedConsultoriosItems);

        new FirebaseUtilDB().saveOrUpdate("usuarios", usuario, new FirebaseUtilDB.FirebaseRTDBSaved() {
            @Override
            public void saved() {
                new Preferencias(getActivity()).setUsuario(usuario);

                startActivity(new Intent(getActivity(), PrincipalActivity.class));
            }
        });
    }

    private void showDialog(List<String> listToShow, final List<String> selected) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Lista");
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_generic_list, null);
        final ListView lista = (ListView) v.findViewById(R.id.lista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_multiple_choice, listToShow));

        for (int i = 0; i < listToShow.size(); i++) {
            if (selected.contains(listToShow.get(i))) {
                lista.setItemChecked(i, true);
            }
        }

        dialog.setView(v);
        dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int a) {
                SparseBooleanArray checked = lista.getCheckedItemPositions();
                selected.clear();
                for (int i = 0; i < checked.size(); i++) {
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i)) {
                        selected.add((String) lista.getAdapter().getItem(position));
                    }
                }
            }
        });
        dialog.show();
    }
}