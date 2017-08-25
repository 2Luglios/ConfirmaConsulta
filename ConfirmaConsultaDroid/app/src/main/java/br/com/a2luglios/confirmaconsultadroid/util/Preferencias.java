package br.com.a2luglios.confirmaconsultadroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

import br.com.a2luglios.confirmaconsultadroid.modelo.MapContato;
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
        edit.putString("nome", usuario.getNome());

        MapContato contato = new MapContato();
        contato.setMapa(usuario.getContato());
        edit.putString("email", contato.getEmail());

        edit.putString("cpf", usuario.getCpf());
        edit.putString("rg", usuario.getRg());
        edit.putBoolean("souMedico", usuario.isEhMedico());
        edit.putString("crm", usuario.getCRM());
        edit.putString("token", usuario.getToken());
        edit.commit();
    }

    public Usuario getUsuario () {
        Usuario usuario = new Usuario () ;
        SharedPreferences prefs = ctx.getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
       //usuario.setId(prefs.getLong("id", -1));
        usuario.setNome(prefs.getString("nome", ""));

        MapContato contato = new MapContato();
        contato.setEmail(prefs.getString("email", ""));
        usuario.setContato(contato.getMapa());

        usuario.setCpf(prefs.getString("cpf", ""));
        usuario.setRg(prefs.getString("rg", ""));
        usuario.setEhMedico(prefs.getBoolean("souMedico", false));
        usuario.setCRM(prefs.getString("crm", ""));
        usuario.setToken(prefs.getString("token", ""));

        return usuario;
    }
}
