package br.com.a2luglios.confirmaconsultadroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

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
        edit.putLong("id", usuario.getId());
        edit.putString("email", usuario.getEmail());
        edit.putString("nome", usuario.getNome());
        edit.putString("senha", usuario.getSenha());
        edit.putString("lembrete", usuario.getLembrete());
        edit.putString("token", usuario.getToken());
        edit.commit();
    }

    private Usuario getUsuario () {
        Usuario usuario = new Usuario () ;
        SharedPreferences prefs = ctx.getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
        usuario.setId(prefs.getLong("id", -1));
        usuario.setEmail(prefs.getString("email", ""));
        usuario.setNome(prefs.getString("nome", ""));
        usuario.setSenha(prefs.getString("senha", ""));
        usuario.setLembrete(prefs.getString("lembrete", ""));
        usuario.setToken(prefs.getString("token", ""));

        return usuario;
    }
}
