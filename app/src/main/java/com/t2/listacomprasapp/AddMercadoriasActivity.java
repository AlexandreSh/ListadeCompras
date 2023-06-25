package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.t2.listacomprasapp.models.MercadoriasModel;

public class AddMercadoriasActivity extends AppCompatActivity {

    EditText nome_edttext;
    EditText preco_edttext;
    Button adicionar_btn;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mercadorias);

        nome_edttext = findViewById(R.id.nome_edttext);
        preco_edttext = findViewById(R.id.preco_edttext);
        adicionar_btn = findViewById(R.id.adicionar_button);
        firestore = FirebaseFirestore.getInstance();

        adicionar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarMercadoria();

            }
        });

    }
    private void adicionarMercadoria() {
        String nome = nome_edttext.getText().toString();
        Double preco = Double.parseDouble(preco_edttext.getText().toString());

        // Criar um novo objeto Mercadoria
        MercadoriasModel mercadoria = new MercadoriasModel(nome, preco);

        // Referência para a coleção "Mercadorias"
        CollectionReference mercadoriasRef = firestore.collection("Mercadorias");

        // Adicionar a mercadoria ao Firestore
        mercadoriasRef.add(mercadoria)
                .addOnSuccessListener(documentReference -> {
                    // A mercadoria foi adicionada com sucesso ao Firestore
                    String mercadoriaId = documentReference.getId();
                    Log.d("AddMercadoriasActivity", "Mercadoria adicionada com sucesso. ID: " + mercadoriaId);
                    Toast.makeText(this, "Mercadoria adicionada com sucesso", Toast.LENGTH_SHORT).show();
                    limparCampos();
                    // Voltar para a atividade anterior
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Ocorreu um erro ao adicionar a mercadoria ao Firestore
                    Log.e("AddMercadoriasActivity", "Erro ao adicionar mercadoria ao Firestore: " + e.getMessage());
                    Toast.makeText(this, "Erro ao adicionar mercadoria", Toast.LENGTH_SHORT).show();
                });
    }

    private void limparCampos() {
        nome_edttext.setText("");
        preco_edttext.setText("");
    }

    // Exemplo de chamada para adicionar a mercadoria (por exemplo, quando o botão "Adicionar" é clicado)
    private void onAdicionarButtonClick() {
        adicionarMercadoria();
    }
}