package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Editar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Fert_Recurs;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Siembra;
import com.myagro.agrocultivo.R;

import java.util.HashMap;
import java.util.Map;

public class Put_fer_recursos extends AppCompatActivity {

  // String URL_POST_add_terreno ="http://206.189.165.63/cultivo/preparacionterreno/editar/";
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


        salir = (Button) findViewById(R.id.put_salir_recurs);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
                Intent addcultiv = new Intent(getApplication(), Grid_Fert_Recurs.class);
                addcultiv.putExtra("name","Recursos de fertilización");
                startActivity(addcultiv);
                finish();
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
                    editar(time,usd,note);
                    Intent addcultiv = new Intent(getApplication(), Grid_Fert_Recurs.class);
                    addcultiv.putExtra("name","Recursos de fertilización");
                    startActivity(addcultiv);
                    finish();


                }  }
        });


    }


    public  void eliminar() {

        String URL_Delete_terreno ="http://206.189.165.63/cultivo/fertilizacion/eliminar/"+id_recurso;

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




    public  void editar(final String tm, final String in, final String nt){
        String URL_Put_terreno ="http://206.189.165.63/cultivo/fertilizacion/editar/"+id_recurso;

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
