package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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

    public void criarUsuario(String email, String senha, final FirebaseLoginInterface loginInterface) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginInterface.onSuccess(mAuth.getCurrentUser());
                        } else {
                            loginInterface.onError("Falha ao criar usuário: " + task.getException().getMessage());
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
                            loginInterface.onSuccess(mAuth.getCurrentUser());
                        } else {
                            loginInterface.onError("Falha de autenticao: " + task.getException().getMessage());
                        }
                    }
                });
    }

    public void logout() {
        mAuth.signOut();
        Log.d("Auth", "LOGOUT");
    }

    public boolean isConnected() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public void alteraSenha(String novaSenha) {
        FirebaseUser user = mAuth.getCurrentUser();

        user.updatePassword(novaSenha)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
    }

    public interface FirebaseLoginInterface {

        public void onSuccess(FirebaseUser user);
        public void onError(String txt);

    }
}