package com.example.elder.app2prova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.elder.app2prova.DbGateway;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private final String TABLE_TAREFAS = "Tarefa";
    private DbGateway gw;

    public TarefaDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    /*public boolean salvar(String desc, String cat, int prior, Boolean status){

        ContentValues cv = new ContentValues();
        //cv.put("Id", id);
        cv.put("Descricao", desc);
        cv.put("Categoria", cat);
        cv.put("Prioridade", prior);
        cv.put("Status ", status);

        retornaTodos();

        return gw.getDatabase().insert(TABLE_TAREFAS, null, cv) > 0;

    }*/
    public boolean salvar(String desc, String cat, int prior, boolean status){
        return salvar(0, desc, cat, prior, status);
    }

    public boolean salvar(int id, String desc, String cat, int prior, boolean status ){
        ContentValues cv = new ContentValues();
        cv.put("Descricao", desc);
        cv.put("Categoria", cat);
        cv.put("Prioridade", prior);
        cv.put("Status ", status);
        if(id>0)
            return gw.getDatabase().update(TABLE_TAREFAS, cv, "ID=?", new String[]{id+""})>0;
        else
            return gw.getDatabase().insert(TABLE_TAREFAS, null, cv)>0;
    }

    public List<Tarefa> retornaTodos(){
        List<Tarefa> tarefas =  new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Tarefa", null);
        while (cursor.moveToNext()){
            int     id   = cursor.getInt(cursor.getColumnIndex("ID"));
            String  desc  = cursor.getString(cursor.getColumnIndex("DESCRICAO"));
            String  cat  = cursor.getString(cursor.getColumnIndex("CATEGORIA"));
            int     prio  = cursor.getInt(cursor.getColumnIndex("PRIORIDADE"));
            boolean status = cursor.getInt(cursor.getColumnIndex("STATUS"))>0;
            tarefas.add(new Tarefa(id, desc, cat, prio, status));
            //Log.d("  select  ",desc);
        }
        cursor.close();
        return tarefas;
    }


    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_TAREFAS, "ID=?", new String[]{id+"" })>0;
    }


    public Tarefa retornaUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Tarefa ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int     id   = cursor.getInt(cursor.getColumnIndex("ID"));
            String  desc  = cursor.getString(cursor.getColumnIndex("DESCRICAO"));
            String  cat  = cursor.getString(cursor.getColumnIndex("CATEGORIA"));
            int     prio  = cursor.getInt(cursor.getColumnIndex("PRIORIDADE"));
            boolean status = cursor.getInt(cursor.getColumnIndex("STATUS"))>0;
            cursor.close();
            return  new Tarefa(id, desc, cat, prio, status);
        }
        return null;
    }

}
