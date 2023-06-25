package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.t2.listacomprasapp.models.ListasModel;
import com.t2.listacomprasapp.models.UsuariosModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private boolean showLogin = false; // Indica se o formulário de login está sendo exibido (false = exibir formulário de registro)
    private boolean isRegister = false; // Indica se a ação atual é um registro (true = registro, false = login)
    private TextView titleTextView; // TextView para exibir o título do formulário (login ou registro)
    private TextView toggleFormTextView; // TextView para exibir a opção de alternar entre login e registro
    private TextView toggleFormTextView2; // TextView para exibir a opção de alternar entre login e registro (outra posição)
    private TextView emailPasswordInvalidTextView; // TextView para exibir mensagens de erro relacionadas a email/senha inválidos
    private FirebaseAuth mAuth; // Instância do Firebase Authentication
    private FirebaseFirestore rootRef;
    private EditText emailEditText; // EditText para inserção do email
    private EditText passwordEditText; // EditText para inserção da senha
    private String email = ""; // String para obter o email inserido pelo usuário
    private String password = ""; // String para obter a senha inserida pelo usuário
    private FirebaseUser user; // Instância do Firebase User


    @Override
    public void onStart() {
        super.onStart();
        // Verifica se o usuário está logado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, MercadoriasActivity.class);
            startActivity(intent);
            finish();
        }
    }

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
                } else {
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

    private void handleRegistrationSubmit() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Inicialize a instância do FirebaseFirestore
                        rootRef = FirebaseFirestore.getInstance();
                        // Usuário registrado com sucesso
                        user = mAuth.getCurrentUser();
                        UsuariosModel usuarioModel = new UsuariosModel(user.getEmail(), user.getUid());

                        String userDocumentId = user.getEmail();

                        // Referência para a coleção "Usuarios"
                        CollectionReference usuariosRef = rootRef.collection("Usuarios");

                        // Define o documento com o ID do email do usuário
                        DocumentReference userDocumentRef = usuariosRef.document(userDocumentId);

                        // Adiciona o usuário ao Firestore
                        userDocumentRef.set(usuarioModel)
                                .addOnSuccessListener(aVoid -> {
                                    // Usuário adicionado com sucesso ao Firestore
                                    System.out.println("Usuário adicionado com sucesso ao Firestore");
                                })
                                .addOnFailureListener(e -> {
                                    // Ocorreu um erro ao adicionar o usuário ao Firestore
                                    System.out.println("Erro ao adicionar usuário ao Firestore: " + e.getMessage());
                                });
                        Map<String, Object> usuarioData = new HashMap<>();
                        usuarioData.put("email", email);

                        rootRef.collection("ListasDeCompras").document(email)
                                .set(usuarioData)
                                .addOnSuccessListener(aVoid -> Log.d("ActivityListLista", "Usuário adicionado à coleção ListasDeCompras"))
                                .addOnFailureListener(e -> Log.e("ActivityListLista", "Erro ao adicionar usuário à coleção ListasDeCompras", e));
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

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}