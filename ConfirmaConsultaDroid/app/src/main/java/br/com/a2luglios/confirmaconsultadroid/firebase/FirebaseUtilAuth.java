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

    public void criarUsuario(String email, String senha, String nome) {
        final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome).build();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Splash", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Splash", "User profile updated.");
                                            }
                                        }
                                    });
                        } else {
                            Log.w("Splash", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void signIn(String email, String senha) {
        mAuth.signInWithEmailAndPassword("ettore.tamadrum@gmail.com", "amoramor")
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Splash", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("Splash", "onComplete: " + user.getDisplayName());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Splash", "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Splash", "else: " + null);
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
