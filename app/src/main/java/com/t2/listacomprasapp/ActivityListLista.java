package com.t2.listacomprasapp;
//lista as listas do usuario

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.t2.listacomprasapp.Holders.ListViewHolder;
import com.t2.listacomprasapp.databinding.ActivityListListaBinding;
import com.t2.listacomprasapp.models.ListasModel;

import java.util.List;
import java.util.Queue;

public class ActivityListLista extends AppCompatActivity {
    private Button voltarButton;
    private Button novaListaButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String usuarioRefEmail;
    private List<String> listaNomes;
    private ArrayAdapter<String> listaAdapter;
    private CollectionReference listaRef;
    private FirestoreRecyclerAdapter<ListasModel, ListViewHolder> firestoreRecyclerAdapter;
    private ActivityListListaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicialize a instância do FirebaseFirestore
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usuarioRefEmail = mAuth.getCurrentUser().getEmail();

        voltarButton = findViewById(R.id.btnVoltar);
        novaListaButton = findViewById(R.id.btnNovaLista);

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                logout();
            }
        });
        listaRef = db.collection("ListasDeCompras").document(usuarioRefEmail).collection("ListasDeUsuario");


        authStateListener = mAuth -> {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
        };

        novaListaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListLista.this);
                builder.setTitle("Nova Lista");
                EditText edtText = new EditText(ActivityListLista.this);
                edtText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                edtText.setHint("Digite o Nome da Nova Lista");
                edtText.setHintTextColor(Color.BLACK);
                builder.setView(edtText);
                builder.setPositiveButton("Criar", (dialog, which) -> {
                    String nomeLista = edtText.getText().toString().trim();
                    if (nomeLista.matches("")){
                        nomeLista = "Lista sem Nome";
                    }
                    ListasModel lista = new ListasModel(nomeLista);
                    String idLista = listaRef.document().getId();

                //    CollectionReference listaRef = db.collection( "Listas");
                    String finalNomeLista = nomeLista;
                    listaRef.document(idLista).set(lista).addOnSuccessListener(documentReference -> {
                        String listaId = idLista;
                        Log.d("ActivityListLista", "Lista criada com sucesso. ID:" + listaId);
                        Toast.makeText(ActivityListLista.this, "Lista criada com sucesso", Toast.LENGTH_SHORT).show();
                        abrirCriarListaActivity(finalNomeLista);
                    }).addOnFailureListener(e -> {Log.e("ActivityListLista", "Erro na criação da lista"+e.getMessage());
                        Toast.makeText(ActivityListLista.this, "Erro na criação da lista", Toast.LENGTH_SHORT).show();
                    });

                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { dialog.cancel();}
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        //listadelistas
        RecyclerView recyclerView = binding.listLista;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = listaRef.orderBy("nomeListas", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ListasModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ListasModel>().setQuery(query, ListasModel.class).build();
        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<ListasModel, ListViewHolder>(firestoreRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ListViewHolder holder, int position, @NonNull ListasModel model) {
                holder.setList(getApplicationContext(), model);
            }

            @Override
            public void onDataChanged() {
                if (getItemCount()==0){
                    recyclerView.setVisibility(View.GONE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            @NonNull
            @Override
            public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent, false);
                return new ListViewHolder(view);
            };

        };
        recyclerView.setAdapter(firestoreRecyclerAdapter);
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

    private void logout(){
        new android.app.AlertDialog.Builder(this).setTitle("Logout").setMessage("Deseja sair do app?").setPositiveButton("Confirmar", ((dialog, which) -> {
            //  T//ODO:rotina de limpeza de cache do usuario que sair <- parece que nao é possivel com o firebase
            //     Context context = getBaseContext();
            //   deleteCache(context);
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ActivityListLista.this, LoginActivity.class);
            startActivity(intent);
            //finish();
        })).setNegativeButton("Cancelar", null).show();
    }

    private void abrirCriarListaActivity(String nomeLista) {
      Intent intent = new Intent(ActivityListLista.this, ActivityViewLista.class);
      Bundle b = new Bundle();
      b.putString("nomeLista",nomeLista);
      intent.putExtras(b);
      startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        firestoreRecyclerAdapter.startListening();
    }

}