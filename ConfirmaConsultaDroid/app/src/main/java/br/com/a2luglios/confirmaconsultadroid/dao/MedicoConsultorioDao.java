package br.com.a2luglios.confirmaconsultadroid.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Medico;
import br.com.a2luglios.confirmaconsultadroid.modelo.MedicoConsultorio;
import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;

/**
 * Created by ettoreluglio on 22/06/17.
 */

public class MedicoConsultorioDao {

    private static final String TABELA = "MedicoConsultorio";
    private BancoUtil bancoUtil;
    public static final String CREATE_QUERY = "CREATE TABLE " + TABELA + " (" +
            "id INTEGER PRIMARY KEY," +
            "idMedico INTEGER," +
            "idConsultorio INTEGER" +
            ");";
    public static final String UPDATE_QUERY = "";

    public MedicoConsultorioDao(BancoUtil bancoUtil) {
        this.bancoUtil = bancoUtil;
    }

    public void insertOrUpdate(MedicoConsultorio medicoConsultorio) {
        if ( medicoConsultorio.getId() == null ) {
            long id = bancoUtil.getWritableDatabase().insert(TABELA, null, medicoConsultorio.getContentValues());
        } else {
            bancoUtil.getWritableDatabase().update(TABELA, medicoConsultorio.getContentValues(), "id=?", new String[]{medicoConsultorio.getId().toString()});
        }

    }

    public void delete(long id) {
        bancoUtil.getWritableDatabase().delete(TABELA, "id=?", new String[]{String.valueOf(id)});
    }

    public List<Medico> getMedicos(long idConsultorio) {
        List<Medico> medicos = new ArrayList<>();

        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, "idConsultorio=?",
                new String[]{String.valueOf(idConsultorio)}, null, null, null, null);

        while(cursor.moveToNext()) {
            Medico medico = new Medico();
            medico.setId(cursor.getLong(cursor.getColumnIndex("idMedico")));

            medicos.add(medico);
        }

        return medicos;
    }

    public List<Consultorio> getConsultorios(long idMedico) {
        List<Consultorio> consultorios = new ArrayList<>();

        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, "idMedico=?",
                new String[]{String.valueOf(idMedico)}, null, null, null, null);

        while(cursor.moveToNext()) {
            Consultorio consultorio = new Consultorio();
            consultorio.setId(cursor.getLong(cursor.getColumnIndex("idConsultorio")));

            consultorios.add(consultorio);
        }

        return consultorios;
    }
}
