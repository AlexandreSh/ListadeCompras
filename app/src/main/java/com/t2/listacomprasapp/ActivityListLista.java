package com.t2.listacomprasapp;
//lista as listas do usuario

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.t2.listacomprasapp.databinding.ActivityListListaBinding;
import com.t2.listacomprasapp.models.listaModel;

import java.util.List;

public class ActivityListLista extends AppCompatActivity {

    /*FirebaseDatabase db = FirebaseDatabase.getInstance(); //nao to sabendo isso
    DatabaseReference userRef = db.getReference();*/
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore rootRef;
    private FirebaseAuth.AuthStateListener authStateListener;


    private Intent edtIntent;
    private ListView listLista;
    private ActivityListListaBinding binding;

    private List<listaModel> listas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listLista = binding.listLista;
        /*binding.btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
        binding.btnNovaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityListLista.this, ActivityViewLista.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseFirestore.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        /*userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                preencheLista();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        edtIntent = new Intent(this, ActivityViewLista.class);
        preencheLista();
    }

    private void preencheLista(){
        listas = db.getReference().getListas(userRef); //TEM QUE FAZER ESSA FUNCAO
        ArrayAdapter<listaModel> listaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listas);
        listLista.setAdapter(listaAdapter);
        listLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaModel listaSel = listas.get(position);
                edtIntent.putExtra("LISTA_ID", listaSel.getID()); //ESSA TAMBEM
                startActivity(edtIntent);
            }
        });
    }*/
}