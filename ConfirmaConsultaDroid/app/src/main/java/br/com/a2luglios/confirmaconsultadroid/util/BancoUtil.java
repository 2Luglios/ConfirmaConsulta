package br.com.a2luglios.confirmaconsultadroid.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.a2luglios.confirmaconsultadroid.dao.MedicoDao;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class BancoUtil extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String BANCO = "ConfirmaConsulta";

    public BancoUtil(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(MedicoDao.CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(MedicoDao.UPDATE_QUERY);

    }
}
