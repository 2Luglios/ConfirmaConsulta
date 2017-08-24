package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * Created by ettoreluglio on 14/08/17.
 */

public class FirebaseUtilAuth {

    private FirebaseAuth mAuth;
    private Activity activity;

    public FirebaseUtilAuth(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public void criarUsuario(String email, String senha, String nome, final FirebaseLoginInterface loginInterface) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginInterface.toDo(mAuth.getCurrentUser());
                        } else {
                            loginInterface.erro("Falha ao criar usuário: " + task.getException().getMessage());
                        }
                    }
                });
    }

    public void signIn(String email, String senha, final FirebaseLoginInterface loginInterface) {
        mAuth.signInWithEmailAndPassword("ettore.tamadrum@gmail.com", "amoramor")
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginInterface.toDo(mAuth.getCurrentUser());
                        } else {
                            loginInterface.erro("Falha de autenticao: " + task.getException().getMessage());
                        }
                    }
                });
    }

    public void logout() {
        mAuth.signOut();
    }

    public boolean isConnected() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }
}
