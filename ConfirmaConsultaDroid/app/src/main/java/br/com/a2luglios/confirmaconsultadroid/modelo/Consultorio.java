package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class Consultorio {

    private Long id;
    private String nome;
    private String endereco;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;
    private List<Medico> medicos;

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

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
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

        String listaMedicos = "{";
        for (int i = 0 ; i < medicos.size() ; i++ ){
            listaMedicos += medicos.get(i).getId();
            if ( i < medicos.size() ) listaMedicos += ",";
        }
        listaMedicos += "}";

        values.put("medicos", listaMedicos);
        return values;
    }
}
