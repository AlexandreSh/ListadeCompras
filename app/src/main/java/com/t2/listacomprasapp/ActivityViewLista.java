package com.t2.listacomprasapp;
//exibe a lista selecionada
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.t2.listacomprasapp.databinding.ActivityViewListaBinding;
import com.t2.listacomprasapp.models.ListasModel;
import com.t2.listacomprasapp.models.MercadoriasModel;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class ActivityViewLista extends AppCompatActivity {

    private ListView lvItems;
    private ActivityViewListaBinding binding;

    private FloatingActionButton btnVoltar2;
    private FloatingActionButton btnAddItem;
    private FloatingActionButton btnDel;
    private TextView tvNomeLista;
    private ListasModel lista;
    private String stringNome;
    private List<MercadoriasModel> Mercadorias;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lvItems = binding.lvItems;
        tvNomeLista = binding.tvNomeLista;
        btnVoltar2 = binding.btnVoltar2;
        btnDel = binding.btnDel;
        Bundle b = getIntent().getExtras();
        if (b != null) {  //caso iniciando uma nova lista com o nome recebido de abrirCriarListaActivity
            stringNome = b.getString("nomeLista");
            tvNomeLista.setText(stringNome);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            lista = (ListasModel) getIntent().getSerializableExtra("listasModel");
        }else{
            lista = (ListasModel) getIntent().getSerializableExtra("listasModel"); //
        }
        stringNome = lista.getNomeListas();
        btnVoltar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
        btnAddItem = binding.btnAddItem;
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FragmentManager mgr = getSupportFragmentManager();
               // FragmentTransaction transaction = mgr.beginTransaction();
                startActivity(new Intent(ActivityViewLista.this, MercadoriasActivity.class));
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del();
            }
        });



        //RecyclerView.LayoutManager col4 = new GridLayoutManager(this,4); //4 colunas, qtdade - nome - preco unitario - total do item
        //listaItens.setLayoutManager(col4); //4 colunas na lista, mas como preencher?
    }
    private void preencheItems(){
      //  Mercadorias = ;//<<<<<<TODO:recebe as mercadorias da lista do DB
        ArrayAdapter<MercadoriasModel> mercadoriasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Mercadorias);
        lvItems.setAdapter(mercadoriasAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //quando o usuario clica num dos items de uma lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:rotina de exibição de um item especifico
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Deleção de Item");
                alertDialog.setMessage("Deseja remover este item?");
                alertDialog.setButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:rotina de deleção de um item especifico da lista
                    }
                });
                alertDialog.setButton("Não", (DialogInterface.OnClickListener) null);
                alertDialog.setIcon(R.drawable.baseline_question_mark_24);
                alertDialog.show();
                return false;
            }
        });

    }
    private void del(){
        new Builder(this).setTitle("Excluir lista").setMessage("Confirma a exclusão da lista?").setPositiveButton("Confirmar", ((dialog, which) -> {
            //TODO:rotina de excluir lista do db
            finish();
        })).setNegativeButton("Cancelar", null).show();
    }

}
