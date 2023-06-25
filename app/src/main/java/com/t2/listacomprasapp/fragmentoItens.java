package com.t2.listacomprasapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.t2.listacomprasapp.models.ListasModel;
import com.t2.listacomprasapp.models.MercadoriasModel;

public class fragmentoItens extends Fragment {
    FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentoItensView = inflater.inflate(R.layout.fragment_fragmento_itens, container, false);

        firestore = FirebaseFirestore.getInstance();

        ImageButton addButton = fragmentoItensView.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Novo item");
                EditText edtText = new EditText(getContext());
                edtText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                edtText.setHint("Digite o Nome do Item");
                edtText.setHintTextColor(Color.BLACK);
                builder.setView(edtText);
                EditText edtTextpreco = new EditText(getContext());
                edtTextpreco.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                edtTextpreco.setHint("Digite o Valor do Item");
                edtTextpreco.setHintTextColor(Color.BLACK);
                builder.setView(edtTextpreco);
                builder.setPositiveButton("Criar", (dialog, which) -> {
                    String nomeProd = edtText.getText().toString().trim();
                    Double valor = Double.parseDouble(edtTextpreco.getText().toString());
                    adicionarMercadoria(nomeProd, valor);
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { dialog.cancel();}
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return fragmentoItensView;
    }
    private void adicionarMercadoria(String nomeProd, Double valor) {
        String nome = nomeProd;
        Double preco = valor;

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
                })
                .addOnFailureListener(e -> {
                    // Ocorreu um erro ao adicionar a mercadoria ao Firestore
                    Log.e("AddMercadoriasActivity", "Erro ao adicionar mercadoria ao Firestore: " + e.getMessage());
                });
    }
}