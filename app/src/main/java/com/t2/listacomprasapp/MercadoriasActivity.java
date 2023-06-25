package com.t2.listacomprasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MercadoriasActivity extends AppCompatActivity {

    ListView itensListView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> dadosDaColecao = new ArrayList<>();
    ImageButton addButton;
    Button minhasListasBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercadorias);

        itensListView = findViewById(R.id.itemLista);
        addButton = findViewById(R.id.add_button);
        minhasListasBtn = findViewById(R.id.listas_button);

        db.collection("Mercadorias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Recupera os documentos da coleção "Mercadorias"
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();

                            for (DocumentSnapshot document : documents) {
                                // Extrai os dados do documento
                                String nome = document.getString("nome");
                                double preco = document.getDouble("preco");

                                // Faz o que for necessário com os dados (exemplo: adicionar a uma lista)
                                dadosDaColecao.add(nome + " - R$" + preco);
                            }
                            // Atualiza o ArrayAdapter com os dados recuperados
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MercadoriasActivity.this,
                                    android.R.layout.simple_list_item_1, dadosDaColecao);

                            // Configura o adaptador para a ListView
                            itensListView.setAdapter(adapter);
                        } else {
                            System.out.printf("A tarefa não foi um sucesso");
                        }
                    }
                });
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MercadoriasActivity.this, AddMercadoriasActivity.class);
                startActivity(intent);
            }
        });
        minhasListasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MercadoriasActivity.this, ActivityListLista.class);
                startActivity(intent);
            }
        });
    }
}