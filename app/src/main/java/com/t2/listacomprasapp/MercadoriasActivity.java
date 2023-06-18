package com.t2.listacomprasapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercadorias);
        itensListView = findViewById(R.id.itemLista);
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
                                String nome = document.getString("Nome");
                                double preco = document.getDouble("Preço");

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
    }
}