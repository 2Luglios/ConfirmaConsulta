package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.modelo.Mensagem;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 13/08/17.
 */

public class FirebaseUtilDB {

    private final FirebaseDatabase database;

    public FirebaseUtilDB() {
        database = FirebaseDatabase.getInstance();
    }

    public void saveOrUpdate(String raiz, FirebaseRTDBInterface modelo, FirebaseRTDBSaved saved) {
        if ( modelo instanceof FirebaseRTDBToken ) {
            ((FirebaseRTDBToken)modelo).setToken(FirebaseInstanceId.getInstance().getToken());
        }
        String hash = ((FirebaseRTDBModel)modelo).getHash();
        if ( hash == null || hash.isEmpty() ) {
            DatabaseReference ref = database.getReference(raiz).push();
            if ( modelo instanceof FirebaseRTDBModel ) {
                ((FirebaseRTDBModel)modelo).setHash(ref.getKey());
            }
            ref.setValue(modelo);
            if ( saved != null ) saved.saved();
        } else {
            DatabaseReference ref = database.getReference(raiz + "/" + hash);
            ref.setValue(modelo);
            if ( saved != null ) saved.saved();
        }
    }

    public void readRTDB(final String raiz, final Class<? extends FirebaseRTDBModel> clazz, final FirebaseRTDBUpdate updateMensagens) {
        final DatabaseReference myRef = database.getReference(raiz);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Log.d("Converte", raiz + " : " + next.getValue().toString());
                    FirebaseRTDBModel model = (FirebaseRTDBModel) next.getValue(clazz);
                    model.setHash(next.getKey());

                    updateMensagens.updateMensagem(model);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("FirebaseDatabase", "Erro ao ler ", error.toException());
            }
        });
    }

    public void readRTDBConsultas(final String raiz, final FirebaseRTDBUpdateConsulta update){
        final DatabaseReference myRef = database.getReference(raiz);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Consulta> consultas = new ArrayList<Consulta>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Consulta model = (Consulta) next.getValue(Consulta.class);
                    model.setHash(next.getKey());

                    consultas.add(model);
                }
                update.updateConsultas(consultas);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("FirebaseDatabase", "Erro ao ler ", error.toException());
            }
        });
    }

    public void readRTDBPlain(String raiz, final FirebaseRTDBUpdate updateMensagens) {
        final DatabaseReference myRef = database.getReference(raiz);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    updateMensagens.updateMensagem(next.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("FirebaseDatabase", "Erro ao ler ", error.toException());
            }
        });
    }

    public void deleteRTDB(String raiz, final FirebaseRTDBSaved firebaseRTDBSaved) {
        final DatabaseReference myRef = database.getReference(raiz);
        myRef.removeValue();
        firebaseRTDBSaved.saved();
    }
}
