package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private boolean showLogin = true;

    private TextView titleTextView;
    private TextView toggleFormTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleTextView = findViewById(R.id.title_textview);
        toggleFormTextView = findViewById(R.id.toggle_form_textview);

        toggleFormTextView.setOnClickListener(new View.OnClickListener() {
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
            toggleFormTextView.setText("Não tem uma conta? Cadastre-se");
        } else {
            titleTextView.setText("Cadastrar Novo Usuário");
            toggleFormTextView.setText("Já tem uma conta? Fazer login");
        }
    }

    private void handleLoginSubmit() {
        // Lógica para processar o login aqui
    }
}