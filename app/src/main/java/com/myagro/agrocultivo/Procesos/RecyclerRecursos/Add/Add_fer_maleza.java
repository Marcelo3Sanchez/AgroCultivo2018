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
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Fert_Recurs;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Fert_maleza;
import com.myagro.agrocultivo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_fer_maleza extends AppCompatActivity {

    String URL_POST_add_maleza = R.string.host+"add_cultivo.php";
    String idCultivo;
    View focusView = null;
    Spinner item_fer_malez;
    TextView titulo;
    String recurso;
    int recurs=0;
    CardView add;
    Button salir;
    EditText cantidad, inversion, nota;
    int a=1;

    @Override
    public void onBackPressed() {
        Intent addcultiv = new Intent(getApplication(), Grid_Fert_maleza.class);
        addcultiv.putExtra("name","Insumos de fertilización");
        startActivity(addcultiv);
        finish();
    }

    private boolean iscantValid_a(String cant) {
        //TODO: Replace this with your own logic
        return cant.contains(".");
    }

    private boolean iscantValid_b(String cant) {
        //TODO: Replace this with your own logic
        return cant.length() >= 5;
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
        if(cantidad.getText().toString().isEmpty()){
            cantidad.setError("Ingrese una cantidad");
            focusView = cantidad;
            a = 0;
        }else a=1;

        if(inversion.getText().toString().isEmpty() ){
            inversion.setError("Ingrese una cantidad");
            focusView = inversion;
            a = 0;
        }else a=1;

        if(!iscantValid_a(cantidad.getText().toString())){
            cantidad.setError("Formato no valido: 00.00");
            focusView = cantidad;
            a = 0;
        }else a=1;

        if(!isinversionValid_a(inversion.getText().toString())){
            inversion.setError("Formato de valido: 00.00");
            focusView = inversion;
            a = 0;
        }else a=1;

        if(!iscantValid_b(cantidad.getText().toString())){
            cantidad.setError("Formato no valido: 00.00");
            focusView = cantidad;
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
        setContentView(R.layout.activity_add_fer_maleza);

        Intent intent = getIntent();
        idCultivo = intent.getExtras().getString("idCultivo");

        //Toast.makeText(getApplication(), idCultivo,Toast.LENGTH_SHORT).show();

        item_fer_malez = (Spinner) findViewById(R.id.add_spinner_malez);
        titulo = (TextView)findViewById(R.id.add_title_malez);
        add = (CardView)findViewById(R.id.add_btnadd_malez);
        cantidad = (EditText)findViewById(R.id.add_tiempo_malez);
        inversion = (EditText)findViewById(R.id.add_inversion_malez);
        nota = (EditText)findViewById(R.id.add_not_malez);

        ArrayList<String> item_recurso = new ArrayList<String>();
        item_recurso.add("Incepticida");
        item_recurso.add("Herbicida");
        item_recurso.add("Pesticida");
        item_recurso.add("Abonos");
        item_recurso.add("Ureas y otros");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item,item_recurso);
        item_fer_malez.setAdapter(adapter);

        titulo.setText("Insumos de fertilización");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
                if(a==1){
                    insertar_fer_maleza();
                    Intent addcultiv = new Intent(getApplication(), Grid_Fert_maleza.class);
                    addcultiv.putExtra("name","Insumos de fertilización");
                    startActivity(addcultiv);
                    finish();
                }
            }
        });

        item_fer_malez.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            recurs = adapterView.getSelectedItemPosition();
            //Toast.makeText(getBaseContext(),Integer.toString(recurs),Toast.LENGTH_SHORT).show();
            recurso = adapterView.getItemAtPosition(i).toString();
            }
            @Override  public void onNothingSelected(AdapterView<?> adapterView) {   }  });

        salir = (Button) findViewById(R.id.add_salir_maleza);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

    }




    //Volley Inserta maleza

    public  void insertar_fer_maleza(){

        String URL_POST_add_recurs ="http://206.189.165.63/cultivo/fertilizacioninsumos/anadir";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_add_recurs, new Response.Listener<String>() {
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
                String hor = cantidad.getText().toString();
                String val= inversion.getText().toString();
                String not = nota.getText().toString() ;
                String cultid = idCultivo.toString();
                params.put("tipo_componente",tipo);
                params.put("cantidad",hor);
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
