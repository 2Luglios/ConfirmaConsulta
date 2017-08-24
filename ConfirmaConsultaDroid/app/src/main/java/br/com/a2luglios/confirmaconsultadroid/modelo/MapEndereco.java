package br.com.a2luglios.confirmaconsultadroid.modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ettoreluglio on 20/08/17.
 */

public class MapEndereco implements MapaFirebase {

    private String cep;
    private String numero;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String complemento;
    private double latitude;
    private double longitude;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public Map<String, Object> getMapa() {
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("cep", cep);
        mapa.put("numero", numero);
        mapa.put("logradouro", logradouro);
        mapa.put("bairro", bairro);
        mapa.put("cidade", cidade);
        mapa.put("estado", estado);
        mapa.put("pais", pais);
        mapa.put("complemento", complemento);
        mapa.put("latitude", latitude);
        mapa.put("longitude", longitude);
        return mapa;
    }

    @Override
    public void setMapa(Map<String, Object> mapa) {
        cep = (String) mapa.get("cep");
        numero = (String) mapa.get("numero");
        logradouro = (String) mapa.get("logradouro");
        bairro = (String) mapa.get("bairro");
        cidade = (String) mapa.get("cidade");
        estado = (String) mapa.get("estado");
        pais = (String) mapa.get("pais");
        complemento = (String) mapa.get("complemento");
        latitude = (double) mapa.get("latitude");
        longitude = (double) mapa.get("longitude");
    }

}
