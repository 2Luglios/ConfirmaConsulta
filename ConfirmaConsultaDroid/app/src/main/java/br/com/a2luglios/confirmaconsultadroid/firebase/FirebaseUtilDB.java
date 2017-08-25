package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

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

    public void saveOrUpdate(String raiz, FirebaseRTDBInterface modelo) {
        if ( modelo instanceof FirebaseRTDBToken ) {
            ((FirebaseRTDBToken)modelo).setToken(FirebaseInstanceId.getInstance().getToken());
        }
        DatabaseReference ref = database.getReference(raiz).push();
        if ( modelo instanceof FirebaseRTDBModel ) {
            ((FirebaseRTDBModel)modelo).setHash(ref.getKey());
        }
        ref.setValue(modelo);
    }

    public void readRTDB(String raiz, final Class<? extends FirebaseRTDBModel> clazz, final FirebaseRTDBUpdate updateMensagens) {
        final DatabaseReference myRef = database.getReference(raiz);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();

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
}
