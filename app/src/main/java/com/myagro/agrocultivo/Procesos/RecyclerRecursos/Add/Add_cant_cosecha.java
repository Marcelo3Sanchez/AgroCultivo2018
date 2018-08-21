package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_cant_cosechada;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_recursos_cosecha;
import com.myagro.agrocultivo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_cant_cosecha extends AppCompatActivity {

  
    String idCultivo;
    View focusView = null;
    Spinner item_cosecha;
    TextView titulo;
    int recurs=0;
    CardView add;
    Button salir;
    EditText horas, inversion, nota;
    int a=1;

    @Override
    public void onBackPressed() {
        Intent addcultiv = new Intent(getApplication(), Grid_cant_cosechada.class);
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
        return inver.length() >= 5 ;
    }



public void validar(){



    if(inversion.getText().toString().isEmpty() ){
        inversion.setError("Ingrese una cantidad");
        focusView = inversion;
        a = 0;
    }else a=1;



    if(!isinversionValid_a(inversion.getText().toString())){
        inversion.setError("Formato no valido: 00.00");
        focusView = inversion;
        a = 0;
    }else a=1;



    if(!isinversionValid_b(inversion.getText().toString())){
        inversion.setError("Formato no valido: 00.00");
        focusView = inversion;
        a = 0;
    }else a=1;

}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cant_cosecha);

        Intent intent = getIntent();
        idCultivo = intent.getExtras().getString("idCultivo");
        //Toast.makeText(getApplication(), idCultivo,Toast.LENGTH_SHORT).show();

        ArrayList<String> item_recurso = new ArrayList<String>();
        item_recurso.add("Cosecha");


        item_cosecha = (Spinner) findViewById(R.id.add_spinner_cosecha);
        titulo = (TextView)findViewById(R.id.add_title_cosecha);
        add = (CardView)findViewById(R.id.add_btnadd_cosecha);
        //horas = (EditText)findViewById(R.id.add_tiempo_recurs);
        inversion = (EditText)findViewById(R.id.add_edi_cantidad_cosecha);
        nota = (EditText)findViewById(R.id.add_not_cosecha);

        titulo.setText("Nueva Cosecha");
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item,item_recurso);
        item_cosecha.setAdapter(adapter);


        item_cosecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            recurs = adapterView.getSelectedItemPosition();
            //Toast.makeText(getBaseContext(),Integer.toString(recurs),Toast.LENGTH_SHORT).show();
            }
            @Override  public void onNothingSelected(AdapterView<?> adapterView) {   }  });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
                if(a==1){
                    insertar_recurs_cosecha();
                    Intent addcultiv = new Intent(getApplication(), Grid_cant_cosechada.class);
                    addcultiv.putExtra("name","Cantidad Cosechada");
                    startActivity(addcultiv);
                    finish();
                }
            }
        });


        salir = (Button) findViewById(R.id.add_salir_cosecha);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish(); }
        });

    }




    //Volley Inserta Cosecha

    public  void insertar_recurs_cosecha(){
        String URL_POST_add_recurs_cosecha ="http://206.189.165.63/cultivo/cosechacantidades/anadir";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_add_recurs_cosecha, new Response.Listener<String>() {
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
                //String hor = horas.getText().toString();
                String val= inversion.getText().toString();
                String not = nota.getText().toString() ;
                String cultid = idCultivo.toString();
                params.put("tipo_recurso",tipo);
                params.put("cantidad",val );
                params.put("notas", not);
                params.put("cultivos_id", cultid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
