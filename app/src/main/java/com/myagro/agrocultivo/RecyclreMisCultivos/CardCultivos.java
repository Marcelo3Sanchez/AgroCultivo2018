package com.myagro.agrocultivo.RecyclreMisCultivos;

public class CardCultivos {

    private String id_cult;
    private String name_cult;
    private String tipo_arroz;
    private String tipo_siembra;
    private String created_ad;
    private int Thumbnail;


    public CardCultivos() {
    }

    public CardCultivos(String id_cult, String name_cult, String tipo_arroz, String tipo_siembra, String created_ad, int thumbnail) {
        this.id_cult = id_cult;
        this.name_cult = name_cult;
        this.tipo_arroz = tipo_arroz;
        this.tipo_siembra = tipo_siembra;
        this.created_ad = created_ad;
        Thumbnail = thumbnail;
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

    public String getCreated_ad() {
        return created_ad;
    }

    public void setCreated_ad(String created_ad) {
        this.created_ad = created_ad;
    }

    public String getId_cult() {
        return id_cult;
    }

    public void setId_cult(String id_cult) {
        this.id_cult = id_cult;
    }

    public String getName_cult() {
        return name_cult;
    }

    public void setName_cult(String name_cult) {
        this.name_cult = name_cult;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
