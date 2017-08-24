//package br.com.a2luglios.confirmaconsultadroid.dao;
//
//import android.database.Cursor;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import br.com.a2luglios.confirmaconsultadroid.modelo.Confirmacao;
//import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
//import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
//import br.com.a2luglios.confirmaconsultadroid.util.BancoUtil;
//
///**
// * Created by ettoreluglio on 19/06/17.
// */
//
//public class ConsultaDao {
//
//    private static final String TABELA = "Consulta";
//    private BancoUtil bancoUtil;
//    public static final String CREATE_QUERY = "CREATE TABLE " + TABELA + " (" +
//            "id INTEGER PRIMARY KEY," +
//            "medicoId INTEGER," +
//            "consultorioId INTEGER," +
//            "data INTEGER," +
//            "horasAntesAviso INTEGER," +
//            "confirmacao TEXT" +
//            ");";
//    public static final String UPDATE_QUERY = "";
//
//    public ConsultaDao(BancoUtil bancoUtil) {
//        this.bancoUtil = bancoUtil;
//    }
//
//    public void insertOrUpdate(Consulta consulta) {
//        if ( consulta.getId() == null ) {
//            long id = bancoUtil.getWritableDatabase().insert(TABELA, null, consulta.getContentValues());
//            consulta.setId(id);
//        } else {
//            bancoUtil.getWritableDatabase().update(TABELA, consulta.getContentValues(), "id=?", new String[]{consulta.getId().toString()});
//        }
//    }
//
//    public void delete (Consulta consulta) {
//        bancoUtil.getWritableDatabase().delete(TABELA, "id=?", new String[]{consulta.getId().toString()});
//    }
//
//    public List<Consulta> list() {
//        List<Consulta> consultas = new ArrayList<>();
//
//        Cursor cursor = bancoUtil.getWritableDatabase().query(TABELA, null, null, null, null, null, null);
//
//        while(cursor.moveToNext()) {
//            Consulta consulta = new Consulta();
//
//            consulta.setId(cursor.getLong(cursor.getColumnIndex("id")));
//
//            consulta.setMedico(cursor.getString(cursor.getColumnIndex("medico")));
//
//            consulta.setData(cursor.getLong(cursor.getColumnIndex("data")));
//
//
//            Consultorio consultorio = new Consultorio();
//            consultorio.setId(cursor.getLong(cursor.getColumnIndex("consultorio")));
//            consultorio = new ConsultorioDao(bancoUtil).getConsultorioPorId(consultorio);
//
//            consulta.setConfirmacao(Confirmacao.valueOf(cursor.getString(cursor.getColumnIndex("confirmacao"))));
//
//            consulta.setAlarme(cursor.getInt(cursor.getColumnIndex("alarme")));
//
//            consultas.add(consulta);
//        }
//
//        return consultas;
//    }
//}
