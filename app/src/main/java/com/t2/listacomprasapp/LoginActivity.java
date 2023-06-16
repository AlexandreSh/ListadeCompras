package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private boolean showLogin = false;

    private TextView titleTextView;
    private TextView toggleFormTextView;

    private  TextView toggleFormTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleTextView = findViewById(R.id.title_textview);
        toggleFormTextView = findViewById(R.id.toggle_form_textview);
        toggleFormTextView2 = findViewById(R.id.toggle_form_textview2);

        toggleFormTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleForm();
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginSubmit();
            }
        });

        toggleForm();
    }

    private void toggleForm() {
        showLogin = !showLogin;
        if (showLogin) {
            titleTextView.setText("Login");
            toggleFormTextView.setText("Não tem uma conta?");
            toggleFormTextView2.setText("Cadastre-se");
        } else {
            titleTextView.setText("Cadastrar Novo Usuário");
            toggleFormTextView.setText("Já tem uma conta?");
            toggleFormTextView2.setText("Fazer Login");
        }
    }

    private void handleLoginSubmit() {
        // Lógica para processar o login aqui
    }
}