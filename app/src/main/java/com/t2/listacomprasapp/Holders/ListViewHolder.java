package com.t2.listacomprasapp.Holders;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t2.listacomprasapp.ActivityViewLista;
import com.t2.listacomprasapp.R;
import com.t2.listacomprasapp.models.ListasModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ListViewHolder extends RecyclerView.ViewHolder {
    private TextView nomeLista, tvCriador, tvData;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeLista = itemView.findViewById(R.id.tv_nome);
        tvData = itemView.findViewById(R.id.tv_data);
        tvCriador = itemView.findViewById(R.id.tv_criador);
    }
    public void setList(Context context, ListasModel listasModel){
        String nome = listasModel.getNomeListas();
        nomeLista.setText(nome);
        if (nome==null){
            nomeLista.setVisibility(View.GONE);
        }
   //     String criador = "Criada por: " +listasModel.getCriador();

        if (listasModel.getCriador()==null){
            tvCriador.setVisibility(View.GONE);
        }

        Date data = listasModel.getDate();
        if (data != null){
            DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);//Não achei portugues
            String dataString = dateFormat.format(data);
            tvData.setText(dataString);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), ActivityViewLista.class);
                intent.putExtra("listasModel", listasModel);
                itemView.getContext().startActivity(intent);
            }
        });
    }

}
