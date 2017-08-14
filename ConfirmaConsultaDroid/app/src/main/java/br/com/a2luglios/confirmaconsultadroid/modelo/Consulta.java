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
    private int horasAntesAviso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

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

    public int getHorasAntesAviso() {
        return horasAntesAviso;
    }

    public void setHorasAntesAviso(int horasAntesAviso) {
        this.horasAntesAviso = horasAntesAviso;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("medico", medico);
        values.put("data", data);
        values.put("confirmacao", confirmacao.name());
        values.put("horasAntesAviso", horasAntesAviso);
        values.put("consultorio", consultorio);
        return values;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }
}
