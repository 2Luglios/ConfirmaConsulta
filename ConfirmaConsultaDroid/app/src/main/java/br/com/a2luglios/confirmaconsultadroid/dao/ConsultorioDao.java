package br.com.a2luglios.confirmaconsultadroid.dao;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Medico;
import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class ConsultorioDao {

    private static final String TABELA = "Consultorio";
    private BancoUtil bancoUtil;
    public static final String CREATE_QUERY = "CREATE TABLE " + TABELA + " (" +
            "id INTEGER PRIMARY KEY," +
            "nome TEXT," +
            "endereco TEXT," +
            "cep TEXT," +
            "bairro TEXT," +
            "cidade TEXT," +
            "estado TEXT," +
            "medicos TEXT" +
            ");";
    public static final String UPDATE_QUERY = "";

    public ConsultorioDao(BancoUtil bancoUtil) {
        this.bancoUtil = bancoUtil;
    }

    public void insertOrUpdate(Consultorio consultorio) {
        if ( consultorio.getId() == null ) {
            long id = bancoUtil.getWritableDatabase().insert(TABELA, null, consultorio.getContentValues());
            consultorio.setId(id);
        } else {
            bancoUtil.getWritableDatabase().update(TABELA, consultorio.getContentValues(), "id=?", new String[]{consultorio.getId().toString()});
        }
    }

    public void delete(Consultorio consultorio) {
        bancoUtil.getWritableDatabase().delete(TABELA, "id=?", new String[]{consultorio.getId().toString()});
    }

    private Consultorio getConsultorioFromCursor(Cursor cursor) {
        Consultorio consultorio = new Consultorio();
        consultorio.setId(cursor.getLong(cursor.getColumnIndex("id")));
        consultorio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        consultorio.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
        consultorio.setCep(cursor.getString(cursor.getColumnIndex("cep")));
        consultorio.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
        consultorio.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
        consultorio.setEstado(cursor.getString(cursor.getColumnIndex("estado")));

        List<Medico> medicos = new ArrayList<>();
        try {
            String listaMedicos = cursor.getString(cursor.getColumnIndex("medicos"));
            JSONArray o = new JSONArray(listaMedicos);
            for (int i = 0; i < o.length(); i++) {
                Medico medico = new Medico();
                medico.setId(o.getLong(i));
                medico = new MedicoDao(bancoUtil).getMedicoPorId(medico);
                medicos.add(medico);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        consultorio.setMedicos(medicos);

        return consultorio;
    }

    public List<Consultorio> list () {
        List<Consultorio> consultorios = new ArrayList<>();

        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            consultorios.add(getConsultorioFromCursor(cursor));
        }

        return consultorios;
    }

    public Consultorio getConsultorioPorId(Consultorio consultorio) {
        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, "id=?",
                new String[]{consultorio.getId().toString()}, null, null, null, null);
        if ( cursor.moveToNext() ) {
            return getConsultorioFromCursor(cursor);
        } else {
            return null;
        }
    }
}
