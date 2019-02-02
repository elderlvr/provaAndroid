package com.example.elder.app2prova;

import java.io.Serializable;
import java.util.Objects;

public class Tarefa implements Serializable {

    private int id;
    private String descricao;
    private String categoria;
    private int prioridade;
    private Boolean status;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Tarefa(int id, String descricao, String categoria, int prioridade, Boolean status) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id &&
                prioridade == tarefa.prioridade &&
                Objects.equals(descricao, tarefa.descricao) &&
                Objects.equals(categoria, tarefa.categoria);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, descricao, categoria, prioridade);
    }
}
