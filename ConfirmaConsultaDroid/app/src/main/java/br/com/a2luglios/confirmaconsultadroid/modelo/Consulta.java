package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class Consulta {

    private Long id;
    private Medico medico;
    private Calendar data;
    private Confirmacao confirmacao;
    private int horasAntesAviso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
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
        values.put("medico", medico.getId());
        values.put("data", data.getTimeInMillis());
        values.put("confirmacao", confirmacao.name());
        values.put("horasAntesAviso", horasAntesAviso);
        return values;
    }
}
