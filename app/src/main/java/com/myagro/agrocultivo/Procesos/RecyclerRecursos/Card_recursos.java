package com.myagro.agrocultivo.Procesos.RecyclerRecursos;

public class Card_recursos {

    private String id;
    private String tipo_recurso;
    private String n_horas;
    private String val_inversion;
    private String notas;
    private String created_at;


    public Card_recursos() {
    }

    public Card_recursos(String id, String tipo_recurso, String n_horas, String val_inversion, String notas, String created_at) {
        this.id = id;
        this.tipo_recurso = tipo_recurso;
        this.n_horas = n_horas;
        this.val_inversion = val_inversion;
        this.notas = notas;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_recurso() {
        return tipo_recurso;
    }

    public void setTipo_recurso(String tipo_recurso) {
        this.tipo_recurso = tipo_recurso;
    }

    public String getN_horas() {
        return n_horas;
    }

    public void setN_horas(String n_horas) {
        this.n_horas = n_horas;
    }

    public String getVal_inversion() {
        return val_inversion;
    }

    public void setVal_inversion(String val_inversion) {
        this.val_inversion = val_inversion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
