package br.com.a2luglios.confirmaconsultadroid;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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
                        senha.getEditableText().toString(),
                        new FirebaseLoginInterface() {
                            @Override
                            public void toDo(FirebaseUser user) {
                                if ( firebaseUtilAuth.isConnected() ) {
                                    Intent logado = new Intent(LoginActivity.this,
                                            PrincipalActivity.class);
                                    startActivity(logado);
                                    finish();
                                }
                            }

                            @Override
                            public void erro(String txt) {
                                showSnack("Impossivel conectar...");
                            }
                        });
            }
        });

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logado = new Intent(LoginActivity.this,
                        CadastroActivity.class);
                startActivity(logado);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        firebaseUtilAuth = new FirebaseUtilAuth(this);
        if ( firebaseUtilAuth.isConnected() ) {
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();
        }
    }

    public void showSnack(String txt) {
        final Snackbar bar = Snackbar.make(findViewById(R.id.main_layout),
                txt, Snackbar.LENGTH_INDEFINITE);

//        bar.setActionTextColor(Color.WHITE);
//        View snackbarView = bar.getView();
//        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.RED);
//        textView.setAllCaps(true);
//        textView.setTextSize(20);
//        snackbarView.setBackgroundColor(Color.BLUE);

        bar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
            }
        }).show();
    }

}
