package br.com.a2luglios.confirmaconsultadroid.modelo;

import java.util.List;
import java.util.Map;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class Consultorio implements FirebaseUtilDB.FirebaseRTDBModel, FirebaseUtilDB.FirebaseRTDBToken {

    private Long id;
    private String hash;
    private String token;
    private String nome;
    private Map<String, Object> contato;
    private Map<String, Object> endereco;
    private List<String> especialidades;
    private List<String> planos;
    private String obs;

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

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Map<String, Object> getContato() {
        return contato;
    }

    public void setContato(Map<String, Object> contato) {
        this.contato = contato;
    }

    public Map<String, Object> getEndereco() {
        return endereco;
    }

    public void setEndereco(Map<String, Object> endereco) {
        this.endereco = endereco;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }

    public List<String> getPlanos() {
        return planos;
    }

    public void setPlanos(List<String> planos) {
        this.planos = planos;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
