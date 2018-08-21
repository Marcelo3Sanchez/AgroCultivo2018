package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Terreno;
import com.myagro.agrocultivo.R;
import com.myagro.agrocultivo.RecyclreMisCultivos.Add_cultivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_terreno extends AppCompatActivity {


    String idCultivo;
    View focusView = null;
    Spinner item_terreno;
    TextView titulo;
    String recurso;
    int recurs=0;
    CardView add;
    Button salir;
    EditText horas, inversion, nota;
    int a=1;

    @Override
    public void onBackPressed() {
        Intent addcultiv = new Intent(Add_terreno.this, Grid_Terreno.class);
        addcultiv.putExtra("name","Preparacion de Terrenos");
        startActivity(addcultiv);
        finish();
    }

    private boolean ishoraValid_a(String hora) {
        //TODO: Replace this with your own logic
        return hora.contains(":");
    }

    private boolean ishoraValid_b(String hora) {
        //TODO: Replace this with your own logic
        return hora.length() >= 5 && hora.length()<6;
    }

    private boolean isinversionValid_a(String inver) {
        //TODO: Replace this with your own logic
        return inver.contains(".");
    }
    private boolean isinversionValid_b(String inver) {
        //TODO: Replace this with your own logic
        return inver.length() >= 5;
    }



    public void validar(){

        if(horas.getText().toString().isEmpty()){
            horas.setError("Ingrese un numero de horas");
            focusView = horas;
            a = 0;
        }else a=1;

        if(inversion.getText().toString().isEmpty() ){
            inversion.setError("Ingrese una cantidd");
            focusView = inversion;
            a = 0;
        }else a=1;

        if(!ishoraValid_a(horas.getText().toString())){
            horas.setError("Formato no valido: hh:mm");
            focusView = horas;
            a = 0;
        }else a=1;

        if(!isinversionValid_a(inversion.getText().toString())){
            inversion.setError("Formato no valido: 00.00");
            focusView = inversion;
            a = 0;
        }else a=1;

        if(!ishoraValid_b(horas.getText().toString())){
            horas.setError("Campo Incompleto: hh:mm");
            focusView = horas;
            a = 0;
        }else a=1;

        if(!isinversionValid_b(inversion.getText().toString())){
            inversion.setError("Campo  no valido: 00.00");
            focusView = inversion;
            a = 0;
        }else a=1;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recurs);

        Intent intent = getIntent();
        idCultivo = intent.getExtras().getString("idCultivo");
        Toast.makeText(getApplication(), idCultivo,Toast.LENGTH_SHORT).show();

        item_terreno = (Spinner) findViewById(R.id.add_spinner_recurs);
        titulo = (TextView)findViewById(R.id.add_title_recurs);
        add = (CardView)findViewById(R.id.add_btnadd_recurs);
        horas = (EditText)findViewById(R.id.add_tiempo_recurs);
        inversion = (EditText)findViewById(R.id.add_inversion_recurs);
        nota = (EditText)findViewById(R.id.add_not_recurs);


        ArrayList<String> item_recurso = new ArrayList<String>();
        item_recurso.add("Estudio de suelo \n");
        item_recurso.add("Maquinaria \n");
        item_recurso.add("Mano de obra \n");
        item_recurso.add("Riego \n");
        item_recurso.add("Transporte \n");


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item,item_recurso);
        item_terreno.setAdapter(adapter);


        titulo.setText("agregar terreno");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
                if(a==1){
                    insertar_terreno();
                    Intent addcultiv = new Intent(Add_terreno.this, Grid_Terreno.class);
                    addcultiv.putExtra("name","Preparacion de Terrenos");
                    startActivity(addcultiv);
                    finish();
                }
            }
        });

        item_terreno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            recurs = adapterView.getSelectedItemPosition();
            recurso = adapterView.getItemAtPosition(i).toString();
            }
            @Override  public void onNothingSelected(AdapterView<?> adapterView) {   }  });

        salir = (Button) findViewById(R.id.add_salir_recurs);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    //Volley Inserta terreno


    public  void insertar_terreno(){
        String URL_POST_add_terreno ="http://206.189.165.63/cultivo/preparacionterreno/anadir";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_add_terreno, new Response.Listener<String>() {
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
                String tipo= Integer.toString(recurs);
                String hor = horas.getText().toString();
                String val= inversion.getText().toString();
                String not = nota.getText().toString() ;
                String cultid = idCultivo.toString();
                params.put("tipo_recurso",tipo);
                params.put("n_horas",hor);
                params.put("val_inversion",val );
                params.put("notas", not);
                params.put("cultivos_id", cultid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
