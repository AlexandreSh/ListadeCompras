package com.t2.listacomprasapp;
//exibe a lista selecionada
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.t2.listacomprasapp.databinding.ActivityViewListaBinding;

public class ActivityViewLista extends AppCompatActivity {

    private RecyclerView listaItens;
    private ActivityViewListaBinding binding;

    private FloatingActionButton btnVoltar2;
    private FloatingActionButton btnAddItem;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaItens = binding.listaItens;

        btnVoltar2 = binding.btnVoltar2;
        btnVoltar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

        btnAddItem = binding.btnAddItem;
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mgr = getSupportFragmentManager();
                FragmentTransaction transaction = mgr.beginTransaction();

            }
        });

        RecyclerView.LayoutManager col4 = new GridLayoutManager(this,4); //4 colunas, qtdade - nome - preco unitario - total do item
        listaItens.setLayoutManager(col4); //4 colunas na lista, mas como preencher?

    }

}