package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBModel;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class Consultorio implements FirebaseRTDBModel {

    private Long id;
    private String hash;
    private String nome;
    private String endereco;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nome", nome);
        values.put("endereco", endereco);
        values.put("cep", cep);
        values.put("bairro", bairro);
        values.put("cidade", cidade);
        values.put("estado", estado);

        return values;
    }
}
