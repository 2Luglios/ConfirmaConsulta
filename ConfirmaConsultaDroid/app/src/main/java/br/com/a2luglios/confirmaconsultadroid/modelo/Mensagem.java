package br.com.a2luglios.confirmaconsultadroid.modelo;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBModel;

/**
 * Created by ettoreluglio on 12/08/17.
 */

public class Mensagem implements FirebaseRTDBModel {

    private Long id;
    private String hash;
    private String origem;
    private String destino;
    private String data;
    private String mensagem;
    private String tipo;
    private String estado;
    private long validade;
    private String imagem;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
