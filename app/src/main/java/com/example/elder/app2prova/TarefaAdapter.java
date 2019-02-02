package com.example.elder.app2prova;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaHolder> {

    private final List<Tarefa> tarefas;

    public TarefaAdapter(List<Tarefa> tarefas){
        this.tarefas = tarefas;
    }

    public void atualizarTarefa(Tarefa tarefa){
        tarefas.set(tarefas.indexOf(tarefa), tarefa);
        notifyItemChanged(tarefas.indexOf(tarefa));
    }


    @NonNull
    @Override
    public TarefaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TarefaHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaHolder tarefaHolder, int i) {
        // quem vai sair nesse tv é a descricao aqui eu falo isso
        tarefaHolder.descTarefa.setText(tarefas.get(i).getDescricao());
        tarefaHolder.catTarefa.setText(tarefas.get(i).getCategoria());
        tarefaHolder.prioTarefa.setText("Prioridade: "+tarefas.get(i).getPrioridade());

        //Log.d(" xxx  ", tarefa)
        //tarefaHolder.Tarefa.setText(tarefas.get(i).getDescricao());
        //tarefaHolder.catTarefa.setText(tarefas.get(i).getCategoria());
        //tarefaHolder.prioTarefa.setText(tarefas.get(i).getPrioridade());

        if(tarefas.get(i).getStatus()==true){
            tarefaHolder.statusTarefaT.setText("O");
            tarefaHolder.statusTarefaF.setText("");
        }else{
            //tarefaHolder.descTarefa.setTextColor();
            tarefaHolder.statusTarefaT.setText("");
            tarefaHolder.statusTarefaF.setText("X");
        }


        final Tarefa tarefa = tarefas.get(i);

        tarefaHolder.btExcluir.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir esta tarefa?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int index) {
                                Tarefa t = tarefa;
                                Log.d("   tarefa exc ",t.getId()+"");
                                TarefaDAO dao = new TarefaDAO(view.getContext());
                                boolean sucesso = dao.excluir(t.getId());
                                if(sucesso) {
                                    removerTarefa(t);
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir o cliente!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

        tarefaHolder.btEditar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Activity activity = getActivity(view);
                Intent i = activity.getIntent();
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                i.putExtra("tarefa", tarefa);// acho q essa é a tarefa do inicio do OnBindViewHolder

                activity.finish();
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tarefas != null ? tarefas.size() : 0;
    }

    public void adicionar(Tarefa tarefa){
        tarefas.add(tarefa);
        notifyItemInserted(getItemCount());
    }

    public void removerTarefa(Tarefa tarefa){
        int position = tarefas.indexOf(tarefa);
        tarefas.remove(position);
        notifyItemRemoved(position);
    }

    private Activity getActivity(View view){
        Context context = view.getContext();
        while (context instanceof ContextWrapper){
            if(context instanceof  Activity){
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

}

