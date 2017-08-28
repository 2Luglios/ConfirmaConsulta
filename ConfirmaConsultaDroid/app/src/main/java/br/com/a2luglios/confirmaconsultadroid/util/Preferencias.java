package br.com.a2luglios.confirmaconsultadroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import br.com.a2luglios.confirmaconsultadroid.modelo.MapContato;
import br.com.a2luglios.confirmaconsultadroid.modelo.MapEndereco;
import br.com.a2luglios.confirmaconsultadroid.modelo.Sexo;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class Preferencias {

    private static final String USUARIO = "USUARIO";

    private final Context ctx;

    public Preferencias(Context ctx) {
        this.ctx = ctx;
    }

    public void setUsuario (Usuario usuario) {
        SharedPreferences.Editor edit = ctx.getSharedPreferences(USUARIO, Context.MODE_PRIVATE).edit();
        //edit.putLong("id", usuario.getId());
        edit.putString("hash", usuario.getHash());
        edit.putString("nome", usuario.getNome());
        edit.putString("lembrete", usuario.getLembrete());
        edit.putBoolean("ehMedico", usuario.isEhMedico());
        edit.putLong("nascimento", usuario.getNascimento());
        edit.putString("sexo", usuario.getSexo().name());
        edit.putString("estadoCivil", usuario.getEstadoCivil());
        edit.putString("profissao", usuario.getProfissao());
        edit.putString("nomeMae", usuario.getNomeMae());
        edit.putString("nomePai", usuario.getNomePai());
        edit.putString("token", usuario.getToken());
        edit.putString("cpf", usuario.getCpf());
        edit.putString("rg", usuario.getRg());

        MapContato contato = new MapContato();
        contato.setMapa(usuario.getContato());
        edit.putString("email", contato.getEmail());
        edit.putString("celular", contato.getCelular());
        edit.putString("telefoneComercial", contato.getTelefoneComercial());
        edit.putString("telefoneResidencial", contato.getTelefoneResidencial());
        edit.putString("contatoEmergencia", contato.getContatoEmergencia());
        edit.putString("telefoneEmergencia", contato.getTelefoneEmergencia());

        MapEndereco endereco = new MapEndereco();
        endereco.setMapa(usuario.getEndereco());
        edit.putString("cep", endereco.getCep());
        edit.putString("bairro", endereco.getBairro());
        edit.putString("cidade", endereco.getCidade());
        edit.putString("complemento", endereco.getComplemento());
        edit.putString("estado", endereco.getEstado());
        edit.putString("logradouro", endereco.getLogradouro());
        edit.putString("numero", endereco.getNumero());
        edit.putString("pais", endereco.getPais());
        edit.putString("latitude", Double.toString(endereco.getLatitude()));
        edit.putString("longitude", Double.toString(endereco.getLongitude()));

        edit.putString("crm", usuario.getCRM());
        edit.putStringSet("especialidades", converteListStringToSetString(usuario.getEspecialidades()));
        edit.putStringSet("planos", converteListStringToSetString(usuario.getPlanos()));
        edit.putStringSet("consultorios", converteListStringToSetString(usuario.getConsultorios()));
        edit.putString("miniCurriculum", usuario.getMiniCurriculum());

        edit.commit();
    }

    public Usuario getUsuario () {
        Usuario usuario = new Usuario () ;
        SharedPreferences prefs = ctx.getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
       //usuario.setId(prefs.getLong("id", -1));
        usuario.setHash(prefs.getString("hash", null));
        usuario.setNome(prefs.getString("nome", null));
        usuario.setLembrete(prefs.getString("lembrete", ""));
        usuario.setEhMedico(prefs.getBoolean("ehMedico", false));
        usuario.setNascimento(prefs.getLong("nascimento", 0));
        usuario.setSexo(Sexo.valueOf(prefs.getString("sexo", Sexo.Masculino.name())));
        usuario.setEstadoCivil(prefs.getString("estadoCivil", ""));
        usuario.setProfissao(prefs.getString("profissao", ""));
        usuario.setNomeMae(prefs.getString("nomeMae", ""));
        usuario.setNomePai(prefs.getString("nomePai", ""));
        usuario.setToken(prefs.getString("token", ""));
        usuario.setCpf(prefs.getString("cpf", ""));
        usuario.setRg(prefs.getString("rg", ""));

        MapContato contato = new MapContato();
        contato.setEmail(prefs.getString("email", ""));
        contato.setCelular(prefs.getString("celular", ""));
        contato.setTelefoneComercial(prefs.getString("telefoneComercial", ""));
        contato.setTelefoneResidencial(prefs.getString("telefoneResidencial", ""));
        contato.setContatoEmergencia(prefs.getString("contatoEmergencia", ""));
        contato.setTelefoneEmergencia(prefs.getString("telefoneEmergencia", ""));
        usuario.setContato(contato.getMapa());

        MapEndereco endereco = new MapEndereco();
        endereco.setCep(prefs.getString("cep", ""));
        endereco.setBairro(prefs.getString("bairro", ""));
        endereco.setCidade(prefs.getString("cidade", ""));
        endereco.setComplemento(prefs.getString("complemento", ""));
        endereco.setEstado(prefs.getString("estado", ""));
        endereco.setLatitude(Double.parseDouble(prefs.getString("latitude", "0")));
        endereco.setLongitude(Double.parseDouble(prefs.getString("longitude", "0")));
        endereco.setLogradouro(prefs.getString("logradouro", ""));
        endereco.setNumero(prefs.getString("numero", ""));
        endereco.setPais(prefs.getString("pais", ""));
        usuario.setEndereco(endereco.getMapa());

        usuario.setCRM(prefs.getString("crm", ""));
        usuario.setEspecialidades(converteSetStringToListString(prefs.getStringSet("especialidades", null)));
        usuario.setPlanos(converteSetStringToListString(prefs.getStringSet("planos", null)));
        usuario.setConsultorios(converteSetStringToListString(prefs.getStringSet("consultorios", null)));
        usuario.setCRM(prefs.getString("miniCurriculum", ""));

        return usuario;
    }

    public Set<String> converteListStringToSetString(List<String> lista) {
        Set<String> set = new HashSet<>();
        for(String s : lista) {
            set.add(s);
        }
        return set;
    }

    public List<String> converteSetStringToListString(Set<String> set) {
        List<String> lista = new ArrayList<>();
        if ( set != null ) {
            for(String s : set) {
                lista.add(s);
            }
        }
        return lista;
    }
}
