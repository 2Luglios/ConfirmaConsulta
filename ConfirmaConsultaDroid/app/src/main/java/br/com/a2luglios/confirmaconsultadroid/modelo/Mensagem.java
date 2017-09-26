package br.com.a2luglios.confirmaconsultadroid.modelo;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;

/**
 * Created by ettoreluglio on 12/08/17.
 */

public class Mensagem implements FirebaseUtilDB.FirebaseRTDBModel {

    private String hash;
    private String origem;
    private String destino;
    private long data;
    private String mensagem;
    private String tipo;
    private long validade;
    private String imagem;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getValidade() {
        return validade;
    }

    public void setValidade(long validade) {
        this.validade = validade;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
