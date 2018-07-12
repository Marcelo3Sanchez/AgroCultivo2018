package com.myagro.agrocultivo.Entidades;

public class Pro_ctrl_maleza {
    private String id;
    private String tipo_componente;
    private String cantidad;
    private String val_inversion;
    private String notas;
    private String created_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_componente() {
        return tipo_componente;
    }

    public void setTipo_componente(String tipo_componente) {
        this.tipo_componente = tipo_componente;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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
