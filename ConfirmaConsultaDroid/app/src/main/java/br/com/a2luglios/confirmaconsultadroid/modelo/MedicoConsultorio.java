package br.com.a2luglios.confirmaconsultadroid.modelo;

import android.content.ContentValues;

/**
 * Created by ettoreluglio on 23/06/17.
 */

public class MedicoConsultorio {

    private Long id;
    private Long idMedico;
    private Long idConsultorio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Long getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(Long idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("idMedico", idMedico);
        values.put("idConsultorio", idConsultorio);

        return values;
    }
}
