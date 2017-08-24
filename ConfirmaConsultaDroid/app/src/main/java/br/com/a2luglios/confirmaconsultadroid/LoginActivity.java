package br.com.a2luglios.confirmaconsultadroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseLoginInterface;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUtilAuth firebaseUtilAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText) findViewById(R.id.campoEmail);
                EditText senha = (EditText) findViewById(R.id.campoSenha);

                firebaseUtilAuth.signIn(email.getEditableText().toString(),
                        senha.getEditableText().toString(), new FirebaseLoginInterface() {
                            @Override
                            public void toDo(FirebaseUser user) {
                                startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                                finish();
                            }

                            @Override
                            public void erro(String txt) {

                            }
                        });
            }
        });

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        firebaseUtilAuth = new FirebaseUtilAuth(this);
        if ( firebaseUtilAuth.isConnected() ) {
            startActivity(new Intent(this, PrincipalActivity.class));
        }
    }

}
