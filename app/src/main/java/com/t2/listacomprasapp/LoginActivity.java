package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LoginActivity extends AppCompatActivity {

    private boolean showLogin = false;
    private boolean isRegister = false;
    private TextView titleTextView;
    private TextView toggleFormTextView;
    private TextView toggleFormTextView2;
    private TextView emailPasswordInvalidTextView;
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleTextView = findViewById(R.id.title_textview);
        toggleFormTextView = findViewById(R.id.toggle_form_textview);
        toggleFormTextView2 = findViewById(R.id.toggle_form_textview2);
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        emailPasswordInvalidTextView = findViewById(R.id.emailPasswordInvalid_textview);

        toggleFormTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFormLoginRegister();
            }
        });
        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (isRegister) {
                    createUser();
                }else{
                    handleLoginSubmit();
                }
            }
        });

        toggleFormLoginRegister();
    }

    private void toggleFormLoginRegister() {
        showLogin = !showLogin;
        if (showLogin) {
            titleTextView.setText("Login");
            toggleFormTextView.setText("Não tem uma conta?");
            toggleFormTextView2.setText("Cadastre-se");
            isRegister = false;
        } else {
            titleTextView.setText("Cadastrar Novo Usuário");
            toggleFormTextView.setText("Já tem uma conta?");
            toggleFormTextView2.setText("Fazer Login");
            isRegister = true;
        }
    }

    private void handleLoginSubmit() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        emailPasswordInvalidTextView.setText("Você está logado na sua conta");
                    } else {
                        Exception exception = task.getException();
                        handleCreateUserException(exception);
                    }
                });
    }

    private void createUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        emailPasswordInvalidTextView.setText("Registrado com sucesso");
                    } else {
                        Exception exception = task.getException();
                        handleCreateUserException(exception);
                    }
                });
    }

    private void handleCreateUserException(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            emailPasswordInvalidTextView.setText("A senha precisa ter pelo menos 6 caracteres");
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            emailPasswordInvalidTextView.setText("Esse email já está em uso");
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException
        && !isRegister) {
            if(isRegister) {
                emailPasswordInvalidTextView.setText("Endereço de email inválido");
            } else {
                emailPasswordInvalidTextView.setText("Senha incorreta");
            }
        } else if (exception instanceof  FirebaseAuthInvalidUserException ){
            emailPasswordInvalidTextView.setText("Endereço de email inválido ou usuário não cadastrado");
        } else if (exception instanceof  FirebaseAuthInvalidUserException){
            emailPasswordInvalidTextView.setText("Usuário não encontrado");
        } else if (exception instanceof FirebaseAuthException) {
            emailPasswordInvalidTextView.setText(exception.toString());
        } else {
            emailPasswordInvalidTextView.setText(exception.toString());
        }
    }

}