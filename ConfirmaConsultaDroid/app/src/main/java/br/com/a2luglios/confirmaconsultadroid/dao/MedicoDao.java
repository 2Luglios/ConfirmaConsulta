package br.com.a2luglios.confirmaconsultadroid.dao;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Medico;
import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class MedicoDao {

    private static final String TABELA = "Medico";
    private BancoUtil bancoUtil;
    public static final String CREATE_QUERY = "CREATE TABLE " + TABELA + " (" +
            "id INTEGER PRIMARY KEY," +
            "nome TEXT," +
            "especialidade TEXT," +
            "oQueAtende TEXT," +
            "consultorios TEXT," +
            "googleId INTEGER" +
            ");";
    public static final String UPDATE_QUERY = "";

    public MedicoDao(BancoUtil bancoUtil) {
        this.bancoUtil = bancoUtil;
    }

    public void insertOrUpdate(Medico medico) {
        if ( medico.getId() == null) {
            long id = bancoUtil.getWritableDatabase().insert(TABELA, null, medico.getContentValues());
            medico.setId(id);
        } else {
            bancoUtil.getWritableDatabase().update(TABELA, medico.getContentValues(), "id=?", new String[]{medico.getId().toString()});
        }
    }

    public void delete(Medico medico) {
        bancoUtil.getWritableDatabase().delete(TABELA, "id=?", new String[]{medico.getId().toString()});
    }

    private Medico getMedicoFromCursor(Cursor cursor) {
        Medico medico = new Medico();
        medico.setId(cursor.getLong(cursor.getColumnIndex("id")));
        medico.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        medico.setConsultorios(null);
        medico.setEspecialidade(cursor.getString(cursor.getColumnIndex("especialidade")));
        medico.setGoogleId(cursor.getString(cursor.getColumnIndex("googleId")));
        medico.setoQueAtende(cursor.getString(cursor.getColumnIndex("oQueAtende")));

        List<Consultorio> consultorios = new ArrayList<>();
        String listaConsultorios = cursor.getString(cursor.getColumnIndex("consultorios"));
        try {
            JSONArray o = new JSONArray(listaConsultorios);
            for (int i = 0; i < o.length(); i++) {
                Consultorio consultorio = new Consultorio();
                consultorio.setId(o.getLong(i));
                consultorio = new ConsultorioDao(bancoUtil).getConsultorioPorId(consultorio);
                consultorios.add(consultorio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        medico.setConsultorios(consultorios);

        return medico;
    }

    public List<Medico> list () {
        List<Medico> medicos = new ArrayList<>();

        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            medicos.add(getMedicoFromCursor(cursor));
        }

        return medicos;
    }

    public Medico getMedicoPorId(Medico medico) {
        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, "id=?",
                new String[]{medico.getId().toString()}, null, null, null);
        if (cursor.moveToNext()) return getMedicoFromCursor(cursor);
        else return null;
    }
}
