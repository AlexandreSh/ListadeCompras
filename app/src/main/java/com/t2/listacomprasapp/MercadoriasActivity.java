package com.t2.listacomprasapp;

import static com.t2.listacomprasapp.LoginActivity.deleteCache;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
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
import com.google.firebase.ktx.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MercadoriasActivity extends AppCompatActivity {

    ListView itensListView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> dadosDaColecao = new ArrayList<>();
    ImageButton addButton;
    Button minhasListasBtn;
    Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercadorias);

        itensListView = findViewById(R.id.itemLista);
        addButton = findViewById(R.id.add_button);
        minhasListasBtn = findViewById(R.id.listas_button);
        btnSair = findViewById(R.id.btnSair);

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
                            System.out.print("A tarefa não foi um sucesso");
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
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    private void logout(){
        new AlertDialog.Builder(this).setTitle("Logout").setMessage("Deseja sair do app?").setPositiveButton("Confirmar", ((dialog, which) -> {
            //  T//ODO:rotina de limpeza de cache do usuario que sair <- parece que nao é possivel com o firebase
       //     Context context = getBaseContext();
         //   deleteCache(context);
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MercadoriasActivity.this, LoginActivity.class);
            startActivity(intent);
            //finish();
        })).setNegativeButton("Cancelar", null).show();
    }
}