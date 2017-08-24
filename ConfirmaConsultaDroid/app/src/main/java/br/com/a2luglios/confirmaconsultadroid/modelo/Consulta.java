package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.Calendar;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBModel;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class Consulta implements FirebaseRTDBModel{

    private Long id;
    private String hash;
    private String medico;
    private long data;
    private Confirmacao confirmacao;
    private String consultorio;
    private int alarme;
    private String procedimento;
    private String chavemedicoconsultorio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public Confirmacao getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(Confirmacao confirmacao) {
        this.confirmacao = confirmacao;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public int getAlarme() {
        return alarme;
    }

    public void setAlarme(int alarme) {
        this.alarme = alarme;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getChavemedicoconsultorio() {
        return chavemedicoconsultorio;
    }

    public void setChavemedicoconsultorio(String chavemedicoconsultorio) {
        this.chavemedicoconsultorio = chavemedicoconsultorio;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("medico", medico);
        values.put("data", data);
        values.put("confirmacao", confirmacao.name());
        values.put("alarme", alarme);
        values.put("consultorio", consultorio);
        return values;
    }

}
