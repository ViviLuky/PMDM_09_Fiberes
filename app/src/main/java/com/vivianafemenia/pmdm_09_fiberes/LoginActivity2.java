package com.vivianafemenia.pmdm_09_fiberes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity2 extends AppCompatActivity {

    private EditText txtEmail;
    private  EditText txtPassword;
    private Button btnoRegister;
    private Button btnLogin;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        txtEmail = findViewById(R.id.txtemail);
        txtPassword = findViewById(R.id.txtPassword);
        btnoRegister = findViewById(R.id.btnReesgitro);
        btnLogin = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        btnoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String pasword = txtPassword.getText().toString();

                if(!email.isEmpty() && !pasword.isEmpty()){
                    doRegister(email,pasword);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String pasword = txtPassword.getText().toString();

                if(!email.isEmpty() && !pasword.isEmpty()){
                    doLogin(email,pasword);
                }
            }
        });

    }

    private void doLogin(String email, String pasword) {

        auth.signInWithEmailAndPassword(email,pasword)
                .addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = auth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                })
                .addOnFailureListener(LoginActivity2.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity2.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void doRegister(String email, String pasword) {

        auth.createUserWithEmailAndPassword(email,pasword)
                .addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = auth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                })
                .addOnFailureListener(LoginActivity2.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity2.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private  void updateUI(FirebaseUser user) {
        if(user != null) {
            startActivity(new Intent(LoginActivity2.this,MainActivity.class));
            finish();
        }
    }

}