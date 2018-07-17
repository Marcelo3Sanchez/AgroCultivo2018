package com.myagro.agrocultivo.Entidades;

public class Mis_cultivos {
    private String id;
    private String name_cult;
    private String tipo_arroz;
    private String tipo_siembra;
    private String created_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_cult() {
        return name_cult;
    }

    public void setName_cult(String name_cult) {
        this.name_cult = name_cult;
    }

    public String getTipo_arroz() {
        return tipo_arroz;
    }

    public void setTipo_arroz(String tipo_arroz) {
        this.tipo_arroz = tipo_arroz;
    }

    public String getTipo_siembra() {
        return tipo_siembra;
    }

    public void setTipo_siembra(String tipo_siembra) {
        this.tipo_siembra = tipo_siembra;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}