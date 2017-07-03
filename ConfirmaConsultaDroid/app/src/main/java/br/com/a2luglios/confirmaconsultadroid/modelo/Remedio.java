package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class Remedio {

    private Long id;
    private String nome;
    private Calendar inicio;
    private int periodicidadeEmHoras;
    private String motivo;
    private Medico medico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getInicio() {
        return inicio;
    }

    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    public int getPeriodicidadeEmHoras() {
        return periodicidadeEmHoras;
    }

    public void setPeriodicidadeEmHoras(int periodicidadeEmHoras) {
        this.periodicidadeEmHoras = periodicidadeEmHoras;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nome", nome);
        values.put("inicio", inicio.getTimeInMillis());
        values.put("periodicidadeEmHoras", periodicidadeEmHoras);
        values.put("motivo", motivo);
        values.put("medico", medico.getId());
        return values;
    }
}
