package com.example.elder.app2prova;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TarefaHolder extends RecyclerView.ViewHolder {
    public TextView descTarefa, catTarefa, prioTarefa, statusTarefaT, statusTarefaF;
    public ImageButton btEditar, btExcluir;

    public TarefaHolder(View view){
        super(view);
        descTarefa = (TextView) view.findViewById(R.id.txtDescLista);
        catTarefa = (TextView) view.findViewById(R.id.txtCatLista);
        prioTarefa = (TextView) view.findViewById(R.id.txtPrioriLista);
        statusTarefaT = (TextView) view.findViewById(R.id.txtStatusListaTrue);
        statusTarefaF = (TextView) view.findViewById(R.id.txtStatusListaFalse);

        btEditar = (ImageButton)  view.findViewById(R.id.btEditLista);
        btExcluir = (ImageButton) view.findViewById(R.id.btDeleteLista);
    }
}

