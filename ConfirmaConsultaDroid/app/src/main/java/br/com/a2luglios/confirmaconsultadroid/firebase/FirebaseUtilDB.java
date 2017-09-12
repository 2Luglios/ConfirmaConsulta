package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Mensagem;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
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

    public void readRTDBConsultorios(final String especialidades, final FirebaseRTDBUpdateLista<Consultorio> listaUpdate){
        final DatabaseReference myRef = database.getReference("consultorios");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Consultorio> consultorios = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Consultorio model = (Consultorio) next.getValue(Consultorio.class);
                    if ( model.getEspecialidades().contains(especialidades) ) {
                        consultorios.add(model);
                    }
                }
                listaUpdate.updateConsultas(consultorios);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("FirebaseDatabase", "Erro ao ler ", error.toException());
            }
        });
    }

    public void readRTDBMedicos(final String consultorio, final String especialidade, final FirebaseRTDBUpdateLista<Usuario> listaUpdate){
        final DatabaseReference myRef = database.getReference("usuarios");

        myRef.orderByChild("ehMedico").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Usuario> medicos = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Usuario model = (Usuario) next.getValue(Usuario.class);
                    boolean temConsultorio = false;
                    boolean temEspecialidade = false;
                    if ( especialidade != null ) {
                        temEspecialidade = model.getEspecialidades().contains(especialidade);
                        temConsultorio = true;
                    }
                    if ( consultorio != null ) {
                        temConsultorio = model.getConsultorios().contains(consultorio);
                    }
                    if ( temEspecialidade & temConsultorio ) medicos.add(model);
                }
                listaUpdate.updateConsultas(medicos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("FirebaseDatabase", "Erro ao ler ", error.toException());
            }
        });
    }

    public void readRTDBConsultas(final FirebaseRTDBUpdateLista<Consulta> listaUpdate){
        final DatabaseReference myRef = database.getReference("consultas");

        myRef.orderByChild("hashUsuario").equalTo("-KswNEAkQ5TuLJomCBGn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Consulta> consultas = new ArrayList<Consulta>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Consulta model = (Consulta) next.getValue(Consulta.class);

                    consultas.add(model);
                }
                listaUpdate.updateConsultas(consultas);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("FirebaseDatabase", "Erro ao ler ", error.toException());
            }
        });
    }

    public void readRTDBMensagens(String filtro, final FirebaseRTDBUpdateLista<Mensagem> listaUpdate){
        Log.d("FirebaseDB", "Buscando: " + filtro);
        final DatabaseReference myRef = database.getReference("mensagens");
        Query q = myRef.orderByChild("destino");
        q.equalTo(filtro);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Mensagem> consultas = new ArrayList<Mensagem>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Iterator<DataSnapshot> i = children.iterator();
                while(i.hasNext()) {
                    DataSnapshot next = i.next();
                    Mensagem model = (Mensagem) next.getValue(Mensagem.class);
                    model.setHash(next.getKey());

                    consultas.add(model);
                }
                listaUpdate.updateConsultas(consultas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void readRTDBPlain(String raiz, final FirebaseRTDBUpdate updateMensagens) {
        final DatabaseReference myRef = database.getReference(raiz);

        myRef.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
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

    public interface FirebaseRTDBUpdate {
        public void updateMensagem(Object obj);
    }

    public interface FirebaseRTDBUpdateLista<T> {
        public void updateConsultas(List<T> lista);
    }

    public interface FirebaseRTDBSaved {
        public void saved();
    }

    public interface FirebaseRTDBInterface {}

    public interface FirebaseRTDBToken extends FirebaseRTDBInterface {
        public void setToken(String token);
        public String getToken();
    }

    public interface FirebaseRTDBModel extends FirebaseRTDBInterface {
        public void setHash(String hash);
        public String getHash();
    }

}
