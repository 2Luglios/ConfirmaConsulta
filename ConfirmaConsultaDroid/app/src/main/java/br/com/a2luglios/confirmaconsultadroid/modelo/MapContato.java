package br.com.a2luglios.confirmaconsultadroid.modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ettoreluglio on 20/08/17.
 */

public class MapContato implements MapaFirebase {

    private String email;
    private String celular;
    private String telefoneResidencial;
    private String telefoneComercial;
    private String contatoEmergencia;
    private String telefoneEmergencia;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getContatoEmergencia() {
        return contatoEmergencia;
    }

    public void setContatoEmergencia(String contatoEmergencia) {
        this.contatoEmergencia = contatoEmergencia;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }

    @Override
    public Map<String, Object> getMapa() {
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("email", email);
        mapa.put("celular", celular);
        mapa.put("telefoneComercial", telefoneComercial);
        mapa.put("telefoneResidencial", telefoneResidencial);
        mapa.put("contatoEmergencia", contatoEmergencia);
        mapa.put("telefoneEmergencia", telefoneEmergencia);
        return mapa;
    }

    @Override
    public void setMapa(Map<String, Object> mapa) {
        email = (String) mapa.get("email");
        celular = (String) mapa.get("celular");
        telefoneComercial = (String) mapa.get("telefoneComercial");
        telefoneResidencial = (String) mapa.get("telefoneResidencial");
        contatoEmergencia = (String) mapa.get("contatoEmergencia");
        telefoneEmergencia = (String) mapa.get("telefoneEmergencia");
    }

}
