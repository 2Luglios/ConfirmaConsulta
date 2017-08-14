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
import br.com.a2luglios.confirmaconsultadroid.modelo.Medico;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 13/08/17.
 */

public class FirebaseUtilDB {

    private final FirebaseDatabase database;

    public FirebaseUtilDB() {
        database = FirebaseDatabase.getInstance();
    }

    public void saveOrUpdateUsuario(Usuario usuario) {
        usuario.setToken(FirebaseInstanceId.getInstance().getToken());
        DatabaseReference usuarios = database.getReference("usuarios").push();
        usuarios.setValue(usuario);
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
}











//        FirebaseUtilDB firebaseUtil = new FirebaseUtilDB();

//        firebaseUtil.readRTDB("usuarios", Usuario.class, new FirebaseRTDBUpdate() {
//            @Override
//            public void updateMensagem(Object obj) {
//                Usuario usuario = (Usuario) obj;
//                Log.d("usuarios", usuario.getHash() + " " + usuario.getNome());
//            }
//        });

//        firebaseUtil.readRTDB("mensagens", Mensagem.class, new FirebaseRTDBUpdate() {
//            @Override
//            public void updateMensagem(Object obj) {
//                Mensagem mensagem = (Mensagem) obj;
//                Log.d("mensagem", mensagem.getHash() + " " + mensagem.getMensagem());
//            }
//        });

//        firebaseUtil.readRTDB("consultorios", Consultorio.class, new FirebaseRTDBUpdate() {
//            @Override
//            public void updateMensagem(Object obj) {
//                Consultorio consultorio = (Consultorio) obj;
//                Log.d("consultorio", consultorio.getHash() + " " + consultorio.getNome());
//            }
//        });

//        firebaseUtil.readRTDB("consultas", Consulta.class, new FirebaseRTDBUpdate() {
//            @Override
//            public void updateMensagem(Object obj) {
//                Consulta consulta = (Consulta) obj;
//                Calendar data = Calendar.getInstance();
//                data.setTimeInMillis(consulta.getData());
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
//                Log.d("consultorio", consulta.getHash() + " " + sdf.format(data.getTime()));
//            }
//        });

//        firebaseUtil.readRTDB("medicos", Medico.class, new FirebaseRTDBUpdate() {
//            @Override
//            public void updateMensagem(Object obj) {
//                Medico medico = (Medico) obj;
//                Log.d("consultorio", medico.getHash() + " " + medico.getNome());
//            }
//        });