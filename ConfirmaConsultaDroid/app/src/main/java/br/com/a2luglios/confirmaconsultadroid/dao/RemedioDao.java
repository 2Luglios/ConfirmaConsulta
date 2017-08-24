package br.com.a2luglios.confirmaconsultadroid.dao;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import br.com.a2luglios.confirmaconsultadroid.modelo.Remedio;
import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class RemedioDao {

    private static final String TABELA = "Remedio";
    private BancoUtil bancoUtil;
    public static final String CREATE_QUERY = "CREATE TABLE " + TABELA + " (" +
            "id INTEGER PRIMARY KEY," +
            "nome TEXT," +
            "inicio INTEGER," +
            "periodicidadeEmHoras INTEGER," +
            "motivo TEXT," +
            "medico INTEGER" +
            ");";
    public static final String UPDATE_QUERY = "";

    public RemedioDao(BancoUtil bancoUtil) {
        this.bancoUtil = bancoUtil;
    }

    public void insertOrUpdate(Remedio remedio) {
        if ( remedio.getId() == null ) {
            long id = bancoUtil.getWritableDatabase().insert(TABELA, null, remedio.getContentValues());
        } else {
            bancoUtil.getWritableDatabase().update(TABELA, remedio.getContentValues(), "id=?", new String[]{remedio.getId().toString()});
        }
    }

    public void delete(Remedio remedio) {
        bancoUtil.getWritableDatabase().delete(TABELA, "id=?", new String[]{remedio.getId().toString()});
    }

    public List<Remedio> list() {
        List<Remedio> remedios = new ArrayList<>();

        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            Remedio remedio = new Remedio ();
            remedio.setId(cursor.getLong(cursor.getColumnIndex("id")));
            remedio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            remedio.setPeriodicidadeEmHoras(cursor.getInt(cursor.getColumnIndex("periodicidadeEmHoras")));
            remedio.setMotivo(cursor.getString(cursor.getColumnIndex("motivo")));

            Calendar inicio = Calendar.getInstance();
            inicio.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("inicio")));
            remedio.setInicio(inicio);


            remedios.add(remedio);
        }

        return remedios;
    }

}
