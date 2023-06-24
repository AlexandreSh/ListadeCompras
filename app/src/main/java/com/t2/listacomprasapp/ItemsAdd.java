package com.t2.listacomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t2.listacomprasapp.databinding.ActivityListListaBinding;

import java.util.List;

public class ItemsAdd extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference userRef = db.getReference();


    private Intent edtIntent;
    private ListView listLista;
    private ActivityListListaBinding binding;

    private List<LoginActivity.itemModel> itens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_add);
    }
}