package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.Calendar;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBModel;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class Consulta implements FirebaseRTDBModel{

    private String hashUsuario;
    private String hash;
    private String chavemedicoconsultorio;
    private Confirmacao confirmacao;
    private int alarme;
    private String procedimento;
    private long dataInicio;
    private long dataTermino;
    private String medico;
    private String consultorio;
    private String idCalendario;
    private long dataMarcada;
    private long dataSolicitado;
    private long dataConfirmado;
    private long dataCancelado;

    public String getHashUsuario() {
        return hashUsuario;
    }

    public void setHashUsuario(String hashUsuario) {
        this.hashUsuario = hashUsuario;
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

    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(long dataTermino) {
        this.dataTermino = dataTermino;
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

    public String getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(String idCalendario) {
        this.idCalendario = idCalendario;
    }

    public long getDataMarcada() {
        return dataMarcada;
    }

    public void setDataMarcada(long dataMarcada) {
        this.dataMarcada = dataMarcada;
    }

    public long getDataSolicitado() {
        return dataSolicitado;
    }

    public void setDataSolicitado(long dataSolicitado) {
        this.dataSolicitado = dataSolicitado;
    }

    public long getDataConfirmado() {
        return dataConfirmado;
    }

    public void setDataConfirmado(long dataConfirmado) {
        this.dataConfirmado = dataConfirmado;
    }

    public long getDataCancelado() {
        return dataCancelado;
    }

    public void setDataCancelado(long dataCancelado) {
        this.dataCancelado = dataCancelado;
    }
}
