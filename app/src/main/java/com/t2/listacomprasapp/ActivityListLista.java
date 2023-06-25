package com.t2.listacomprasapp;
//lista as listas do usuario

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.t2.listacomprasapp.models.ListasModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityListLista extends AppCompatActivity {

    private ListView listaListView;
    private Button voltarButton;
    private Button novaListaButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String usuarioRefEmail;
    private List<String> listaNomes;
    private ArrayAdapter<String> listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lista);

        // Inicialize a instância do FirebaseFirestore
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usuarioRefEmail = mAuth.getCurrentUser().getEmail();

        listaListView = findViewById(R.id.listLista);
        voltarButton = findViewById(R.id.btnVoltar);
        novaListaButton = findViewById(R.id.btnNovaLista);

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Voltar para a atividade anterior
            }
        });

        novaListaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCriarListaActivity(); // Abrir a atividade para criar uma nova lista
            }
        });

        carregarNomesListas();
    }

    private void carregarNomesListas() {
        DocumentReference docRef = db.collection("ListasDeCompras").document(usuarioRefEmail);
        docRef.get();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }


    private void abrirCriarListaActivity() {
      //  Intent intent = new Intent(ActivityListLista.this, CriarListaActivity.class);
        //startActivity(intent);
    }

}