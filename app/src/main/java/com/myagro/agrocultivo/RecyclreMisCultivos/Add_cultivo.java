package com.myagro.agrocultivo.RecyclreMisCultivos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.myagro.agrocultivo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_cultivo extends AppCompatActivity {

    String URL_POST_add_cultivo = R.string.host+"add_cultivo.php";

    String tipo_arroz,tipo_sembrio,iduser;
    int a=1;

    View focusView = null;
    Spinner item_sembrio, item_arroz;
    CardView add;
    EditText name, dimension, inicial, semilla, inversion;


    @Override
    public void onBackPressed() {
        finish();
    }


    private boolean isnameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length()<10;
    }

    private boolean isdimeValid(String dime) {
        //TODO: Replace this with your own logic
        return dime.contains(".");
    }

    private boolean isinicValid(String inic) {
        //TODO: Replace this with your own logic
        return inic.contains(".");
    }

     private boolean issemillaValid(String semi) {
        //TODO: Replace this with your own logic
        return semi.contains(".");
    }

    private boolean isinverValid(String inver) {
        //TODO: Replace this with your own logic
        return inver.contains(".");
    }


public void validar(){

    if(name.getText().toString().isEmpty()){
        name.setError("Campo vacio");
        focusView = name;
        a = 0;}else{
        if(!isnameValid(name.getText().toString())){
            name.setError("max. 10 caracteres");
            focusView = name;
            a = 0;} else a=1;}

    if(dimension.getText().toString().isEmpty() ){
    dimension.setError("Campo vacio");
    focusView = dimension;
    a = 0;
    }else {
        if(!isdimeValid(dimension.getText().toString())){
            dimension.setError("Formato no valido: 00.00");
            focusView = dimension;
            a = 0;
        }else a=1;}





    if(inicial.getText().toString().isEmpty() ){
        inicial.setError("Campo vacio");
        focusView = inicial;
        a = 0;
    }else {
        if(!isinicValid(inicial.getText().toString())){
            inicial.setError("Formato no valido: 00.00");
            focusView = inicial;
            a = 0;
        }else a=1; }



    if(inversion.getText().toString().isEmpty() ){
        inversion.setError("Campo vacio");
        focusView = inversion;
        a = 0;
    }else {
        if(!isinverValid(inversion.getText().toString())){
            inversion.setError("Formato no valido: 00.00");
            focusView = inversion;
            a = 0;
        }else a=1; }




    if(semilla.getText().toString().isEmpty() ){
        semilla.setError("Campo vacio");
        focusView = semilla;
        a = 0;
    }else {
        if(!issemillaValid(semilla.getText().toString())){
            semilla.setError("Formato no valido: 00.00");
            focusView = semilla;
            a = 0;
        }else a=1;  } }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cultivo);

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        iduser = preferences.getString("id",null);

        ArrayList<String> item_tipo_arroz = new ArrayList<String>();
        item_tipo_arroz.add("FSL");

        ArrayList<String> item_tipo_sembrio = new ArrayList<String>();
        item_tipo_sembrio.add("Arboleo");
        item_tipo_sembrio.add("Transplante");


        item_sembrio = (Spinner) findViewById(R.id.add_spinner_tipoS_cult_nuev);
        item_arroz = (Spinner) findViewById(R.id.add_spinner_tipoA__cult_nuev);
        add = (CardView)findViewById(R.id.add_btnadd__cult_nuev);

        name = (EditText)findViewById(R.id.add_name_cult_nuev);
        dimension = (EditText)findViewById(R.id.add_dim_cult_nuev);
        inicial = (EditText)findViewById(R.id.add_cap_ini_cult_nuev);
        semilla = (EditText)findViewById(R.id.add_cant_sem_cult_nuev);
        inversion = (EditText)findViewById(R.id.add_inversion__cult_nuev);


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,item_tipo_arroz);
        item_arroz.setAdapter(adapter);

        ArrayAdapter<CharSequence> Itemsembrio = new ArrayAdapter(this, android.R.layout.simple_spinner_item,item_tipo_sembrio);
        item_sembrio.setAdapter(Itemsembrio);

        item_arroz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tipo_arroz = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getBaseContext(),tipo_arroz, Toast.LENGTH_SHORT).show(); }

            @Override  public void onNothingSelected(AdapterView<?> adapterView) {   }  });

        item_sembrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipo_sembrio = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getBaseContext(),tipo_sembrio,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { } });



       add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
                if(a==1){
                    insertar_cultivo();
                    finish();}
            }
        });
    }



    public  void insertar_cultivo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_add_cultivo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_cultivo.this , error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            //name_cult, dimension_cult, tipo_arroz, tipo_siembra,
            // cantidad_semilla, valor_inversion, capital_inicial,estado_cultivo, users_id
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String nam_cult= name.getText().toString();
                String dim_cult = dimension.getText().toString();
                String tipo_arr= tipo_arroz;
                String tipo_siembra = tipo_sembrio ;
                String cant_sem = semilla.getText().toString() ;
                String val_inver = inversion.getText().toString();
                String cap_inic = inicial.getText().toString();
                String estado_cult = "true" ;
                String userid = iduser;
                params.put("name",nam_cult);
                params.put("dime",dim_cult);
                params.put("tipr",tipo_arr );
                params.put("tips", tipo_siembra);
                params.put("cants", cant_sem);
                params.put("vali", val_inver);
                params.put("capi", cap_inic);
                params.put("estac", estado_cult);
                params.put("usid", userid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
