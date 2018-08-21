package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Editar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Procesos.CultProcesos;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add.Add_terreno;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Fert_maleza;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Terreno;
import com.myagro.agrocultivo.R;

import java.util.HashMap;
import java.util.Map;

public class Put_pre_terreno extends AppCompatActivity {


   TextView name_recurs;
   EditText horas, inversion, notas;
   Button edite, elim, salir;
   String id_recurso, name, time, usd, note, id_cult;
    private Context contextos;
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_recurs);


        salir = (Button) findViewById(R.id.put_salir_recurs);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        contextos = this;
        name_recurs = (TextView)findViewById(R.id.edit_name_recurs);
        elim = (Button) findViewById(R.id.btn_elim);
        horas = (EditText)findViewById(R.id.edi_horas_recurs);
        inversion = (EditText)findViewById(R.id.edit_inversion_recurs);
        notas = (EditText)findViewById(R.id.edit_not_recurs);
        edite = (Button) findViewById(R.id.btn_edi);


        SharedPreferences preferences = getSharedPreferences("put_recurso", Context.MODE_PRIVATE);
        id_cult = preferences.getString("idCultivo",null);

        Intent intent = getIntent();
        id_recurso = intent.getExtras().getString("id_recurso");
        name = intent.getExtras().getString("name_recurso");
        time = intent.getExtras().getString("hora");
        usd = intent.getExtras().getString("inversion");
        note = intent.getExtras().getString("nota");

        name_recurs.setText(name);
        horas.setText(time);
        inversion.setText(usd);
        notas.setText(note);

        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmareliminar(contextos);

            }

        });

        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (a == 0) {
                    a=1;
                    inversion.setEnabled(true);
                    horas.setEnabled(true);
                    notas.setEnabled(true);
                    edite.setBackground(getResources().getDrawable(R.drawable.oki));
                }else {

                    time = horas.getText().toString();
                    usd = inversion.getText().toString();
                    note = notas.getText().toString();
                    editar_terreno(time,usd,note);

                    Intent addcultiv = new Intent(getApplication(), Grid_Terreno.class);
                    addcultiv.putExtra("name","Preparacion de Terrenos");
                    startActivity(addcultiv);
                    finish();
                }  }
        });


    }




    public int confirmareliminar(Context contexto){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_cerrar_sesion);
        dialog.show();

        TextView title = (TextView) dialog.findViewById(R.id.txt_title_cerar);
        Button cerrar = (Button) dialog.findViewById(R.id.btn_cerrar_sesion);
        Button cancelar = (Button) dialog.findViewById(R.id.btn_cerar_cancelar);

        title.setText("Esta seguro de eliminar?");
        cerrar.setText("Eliminar");

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar_terreno();
                dialog.dismiss();
                Intent addcultiv = new Intent(getApplication(), Grid_Terreno.class);
                addcultiv.putExtra("name","Preparacion de Terrenos");
                startActivity(addcultiv);
                finish();
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return 0;
    }







    public  void eliminar_terreno() {

        String URL_Delete_terreno ="http://206.189.165.63/cultivo/preparacionterreno/eliminar/"+id_recurso;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL_Delete_terreno,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplication(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), error+ " " , Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    public  void editar_terreno(final String tm, final String in, final String nt){
        String URL_Put_terreno ="http://206.189.165.63/cultivo/preparacionterreno/editar/"+id_recurso;

        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL_Put_terreno, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplication() , error+"", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String hor = tm;
                String val= in;
                String not = nt ;
                params.put("n_horas",hor);
                params.put("val_inversion",val );
                params.put("notas", not);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
