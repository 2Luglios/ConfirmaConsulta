package br.com.a2luglios.confirmaconsultadroid.firebase;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;

/**
 * Created by ettoreluglio on 13/08/17.
 */

public interface FirebaseRTDBUpdateConsulta {

    public void updateConsultas(List<Consulta> consulta);

}
