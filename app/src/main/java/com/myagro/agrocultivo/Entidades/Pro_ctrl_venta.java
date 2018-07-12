package com.myagro.agrocultivo.Entidades;

public class Pro_ctrl_venta {
    private String id;
    private String tipo_recurso;
    private String cantidad;
    private String val_venta;
    private String notas;
    private String created_at;


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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getVal_venta() {
        return val_venta;
    }

    public void setVal_venta(String val_venta) {
        this.val_venta = val_venta;
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