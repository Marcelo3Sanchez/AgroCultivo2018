package com.myagro.agrocultivo.Procesos;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Almacenamiento;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Cosecha;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Fert_Recurs;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Fert_maleza;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Siembra;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Terreno;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Venta;
import com.myagro.agrocultivo.R;
import com.myagro.agrocultivo.RecyclreMisCultivos.MisCultivosGrid;

public class CultProcesos extends AppCompatActivity {

    CardView pre_terreno, siembra, fertilizacion, cosecha, venta;
    TextView nombre, tipo, metodo,  fecha;
    String idCultivo;
    int val = 0;
    private Context contextos;

    @Override
    public void onBackPressed() {


        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesos);
        contextos = this;
        pre_terreno = (CardView)findViewById(R.id.pre_terreno);
        siembra = (CardView)findViewById(R.id.siembra);
        fertilizacion = (CardView)findViewById(R.id.fertilizacion);
        cosecha = (CardView)findViewById(R.id.cosecha);
        venta = (CardView)findViewById(R.id.venta);

        nombre = (TextView)findViewById(R.id.name_id);
        tipo =(TextView)findViewById(R.id.tipo_arroz);
        metodo = (TextView)findViewById(R.id.metodo);
        fecha = (TextView)findViewById(R.id.created);


        Intent intent = getIntent();
        idCultivo = intent.getExtras().getString("idCultivo");
        String name_cult = intent.getExtras().getString("name_cult");
        String tipo_arroz = intent.getExtras().getString("tipo_arroz");
        String tipo_siembra = intent.getExtras().getString("tipo_siembra");
        String created_ad = intent.getExtras().getString("created_ad");


        nombre.setText(name_cult);
        tipo.setText("Tipo: "+tipo_arroz);
        metodo.setText("Metodo: "+tipo_siembra);
        fecha.setText("Sembrio: "+created_ad);



        pre_terreno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(CultProcesos.this, Grid_Terreno.class);
                home.putExtra("name","Preparacion de Terrenos");
                home.putExtra("idcult",idCultivo);
                startActivity(home);
            }
        });

        siembra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent siembra = new Intent(CultProcesos.this, Grid_Siembra.class);
                siembra.putExtra("name","Siembra");
                siembra.putExtra("idcult",idCultivo);
                startActivity(siembra);
            }
        });

        fertilizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {fertilizacionDialogo(contextos);
            }
        });

        cosecha.setOnClickListener(new View.OnClickListener() { 
            @Override
            public void onClick(View view) {cosechaDialogo(contextos);  }
        });

        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ventaDialogo(contextos);}
        });

    }


    public int fertilizacionDialogo(Context contexto){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_rutas);
        dialog.show();

        CardView recur = (CardView) dialog.findViewById(R.id.btn_recurs);
        CardView malez = (CardView)dialog.findViewById(R.id.btn_maleza);

        recur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(),"Fertilizacion Recursos".toString(),Toast.LENGTH_SHORT).show();
                Intent fert_recurs= new Intent(CultProcesos.this, Grid_Fert_Recurs.class);
                fert_recurs.putExtra("name","Fertilizacion Recursos");
                fert_recurs.putExtra("idcult",idCultivo);
                startActivity(fert_recurs);
                dialog.dismiss();
            }
        });
        malez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(),"Fertilizacion Maleza".toString(),Toast.LENGTH_SHORT).show();
                Intent fert_maleza= new Intent(CultProcesos.this, Grid_Fert_maleza.class);
                fert_maleza.putExtra("name","Fertilizacion Maleza");
                fert_maleza.putExtra("idcult",idCultivo);
                startActivity(fert_maleza);
                dialog.dismiss();

            }
        });

        return 0;
    }


    public int cosechaDialogo(Context contexto){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_rutas);
        dialog.show();

        TextView ruta1 = (TextView) dialog.findViewById(R.id.txt_ruta1);
        TextView ruta2 = (TextView) dialog.findViewById(R.id.txt_ruta2);
        CardView recur = (CardView) dialog.findViewById(R.id.btn_recurs);
        CardView cosecha = (CardView)dialog.findViewById(R.id.btn_maleza);
        ruta1.setText("Recursos");
        ruta2.setText("Nueva cosecha");
        recur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Cosecha Recursos".toString(),Toast.LENGTH_SHORT).show();
                Intent cosecha= new Intent(CultProcesos.this, Grid_Cosecha.class);
                cosecha.putExtra("name","Recurso de cosecha");
                cosecha.putExtra("idcult",idCultivo);
                startActivity(cosecha);
                dialog.dismiss();
            }
        });
        cosecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(),"Nueva cosecha".toString(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        return 0;
    }

    public int ventaDialogo(Context contexto){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_rutas);
        dialog.show();

        TextView ruta1 = (TextView) dialog.findViewById(R.id.txt_ruta1);
        TextView ruta2 = (TextView) dialog.findViewById(R.id.txt_ruta2);
        CardView almacenar = (CardView) dialog.findViewById(R.id.btn_recurs);
        CardView vender = (CardView)dialog.findViewById(R.id.btn_maleza);
        ruta1.setText("Almacenar");
        ruta2.setText("Vender");

        almacenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Almacenar Cultivos".toString(),Toast.LENGTH_SHORT).show();
                Intent almacen= new Intent(CultProcesos.this, Grid_Almacenamiento.class);
                almacen.putExtra("name","Almacenar Cultivos");
                almacen.putExtra("idcult",idCultivo);
                startActivity(almacen);
                dialog.dismiss();
            }
        });

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Reguistrar una venta".toString(),Toast.LENGTH_SHORT).show();
                Intent venta= new Intent(CultProcesos.this, Grid_Venta.class);
                venta.putExtra("name","Reguistrar una venta");
                venta.putExtra("idcult",idCultivo);
                startActivity(venta);
                dialog.dismiss();
            }
        });

        return 0;
    }



}
