package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private boolean showLogin = false; // Indica se o formulário de login está sendo exibido (false = exibir formulário de registro)
    private boolean isRegister = false; // Indica se a ação atual é um registro (true = registro, false = login)
    private TextView titleTextView; // TextView para exibir o título do formulário (login ou registro)
    private TextView toggleFormTextView; // TextView para exibir a opção de alternar entre login e registro
    private TextView toggleFormTextView2; // TextView para exibir a opção de alternar entre login e registro (outra posição)
    private TextView emailPasswordInvalidTextView; // TextView para exibir mensagens de erro relacionadas a email/senha inválidos
    private FirebaseAuth mAuth; // Instância do Firebase Authentication
    private EditText emailEditText; // EditText para inserção do email
    private EditText passwordEditText; // EditText para inserção da senha
    private String email=""; // String para obter o email inserido pelo usuário
    private String password=""; // String para obter a senha inserida pelo usuário
    private FirebaseUser user; // Instância do Firebase User

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialização das TextViews e EditText
        titleTextView = findViewById(R.id.title_textview);
        toggleFormTextView = findViewById(R.id.toggle_form_textview);
        toggleFormTextView2 = findViewById(R.id.toggle_form_textview2);
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        emailPasswordInvalidTextView = findViewById(R.id.emailPasswordInvalid_textview);

        // Configurar o clique para alternar entre os formulários de login e registro
        toggleFormTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFormLoginRegister();
            }
        });
        // Obter uma instância do FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.login_button);

        // Configurar o clique do botão de login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter email e senha digitados pelo usuário
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                // Verificar se é um registro ou um login e executar a ação apropriada
                if (isRegister) {
                    handleRegistrationSubmit();
                }else{
                    handleLoginSubmit();
                }
            }
        });
        // Alternar para o formulário de login ou registro com base na configuração atual
        toggleFormLoginRegister();
    }

    private void toggleFormLoginRegister() {
        // Alternar entre exibir o formulário de login ou registro
        showLogin = !showLogin;
        if (showLogin) {
            // Exibir formulário de login
            titleTextView.setText("Login");
            toggleFormTextView.setText("Não tem uma conta?");
            toggleFormTextView2.setText("Cadastre-se");
            isRegister = false;
        } else {
            // Exibir formulário de registro
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
                        // Login realizado com sucesso
                        user = mAuth.getCurrentUser();
                        Intent intent = new Intent(this, MercadoriasActivity.class);
                        startActivity(intent);
                    } else {
                        // Ocorreu um erro durante o login
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidUserException) {
                            // Usuário não existe ou foi desativado, informe ao usuário
                            emailPasswordInvalidTextView.setText("O usuário não existe. Verifique suas credenciais.");
                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            // Credenciais inválidas, informe ao usuário
                            emailPasswordInvalidTextView.setText("Verifique seu email e senha.");
                        } else {
                            // Outro erro, informe ao usuário de forma genérica
                            emailPasswordInvalidTextView.setText("Ocorreu um erro durante o login. Tente novamente mais tarde.");
                        }
                    }
                });
    }

    private void handleRegistrationSubmit(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Usuário registrado com sucesso
                        user = mAuth.getCurrentUser();
                    } else {
                        // Ocorreu um erro durante o registro
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthWeakPasswordException) {
                            // Senha fraca, informe ao usuário
                            emailPasswordInvalidTextView.setText("A senha é muito fraca.\n Mínimo 6 caracteres.");
                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            // Email inválido, informe ao usuário
                            emailPasswordInvalidTextView.setText("O email inserido é inválido. Tente novamente.");
                        } else if (exception instanceof FirebaseAuthUserCollisionException) {
                            // O usuário já existe, informe ao usuário
                            emailPasswordInvalidTextView.setText("O usuário já existe. Faça login em vez de registrar.");
                        } else {
                            // Outro erro, informe ao usuário de forma genérica
                            emailPasswordInvalidTextView.setText("Ocorreu um erro durante o registro. Tente novamente mais tarde.");
                        }
                    }
                });
    }
}