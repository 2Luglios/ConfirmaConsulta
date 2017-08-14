package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBModel;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class Medico implements FirebaseRTDBModel{

    private Long id;
    private String hash;
    private String nome;
    private String especialidade;
    private String oQueAtende;
    private List<Consultorio> consultorios;
    private String googleId;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getoQueAtende() {
        return oQueAtende;
    }

    public void setoQueAtende(String oQueAtende) {
        this.oQueAtende = oQueAtende;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public List<Consultorio> getConsultorios() {
        return consultorios;
    }

    public void setConsultorios(List<Consultorio> consultorios) {
        this.consultorios = consultorios;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nome", nome);
        values.put("especialidade", especialidade);
        values.put("oQueAtende", oQueAtende);

        String listaConsultorios = "{";
        for ( int i = 0 ; i < consultorios.size() ; i++ ) {
            listaConsultorios += consultorios.get(i).getId();
            if ( i < consultorios.size() ) listaConsultorios += ",";
        }
        listaConsultorios += "}";
        values.put("consultorios", listaConsultorios);

        values.put("googleId", googleId);
        return values;
    }
}
