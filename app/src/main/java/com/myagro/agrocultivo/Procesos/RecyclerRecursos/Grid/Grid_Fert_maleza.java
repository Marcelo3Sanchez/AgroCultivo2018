package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Entidades.Pro_ctrl_maleza;
import com.myagro.agrocultivo.Home;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add.Add_fer_maleza;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Card_recursos;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.RecyclerView.RecyclerViewAdap_fer_maleza;
import com.myagro.agrocultivo.R;
import com.myagro.agrocultivo.RecyclreMisCultivos.MisCultivosGrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Grid_Fert_maleza extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context contextos;
    List<Card_recursos> lstRecurso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView txt_n;
    String idCultivo, tipo;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MisCultivosGrid.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home_recurs:
                    startActivity(new Intent(getBaseContext(), Home.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                    return true;
                case R.id.navigation_micultivo_recurs:
                    startActivity(new Intent(getBaseContext(), MisCultivosGrid.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                    return true;
                case R.id.navigation_add_recurs:
                    Intent add = new Intent(Grid_Fert_maleza.this, Add_fer_maleza.class);
                    add.putExtra("idCultivo",idCultivo);
                    startActivity(add);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recursos);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_recurs);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        txt_n = (TextView)findViewById(R.id.txt_name_recurs);
        Intent intent = getIntent();

        String nombre = intent.getExtras().getString("name");
        idCultivo = intent.getExtras().getString("idcult");


        SharedPreferences preferences = getSharedPreferences("put_recurso", Context.MODE_PRIVATE);
        idCultivo = preferences.getString("idCultivo",null);
        txt_n.setText(nombre);
        contextos = this;

        request= Volley.newRequestQueue(getBaseContext());
        cargarService();


    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(),"No se pudo Consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Pro_ctrl_maleza ctrlmaleza = new Pro_ctrl_maleza();
        JSONArray json = response.optJSONArray("fertilizacion_recursos");
        JSONObject jsonObject = null;
        lstRecurso = new ArrayList<>();

        try {

            for (int i = 0; i <json.length() ; i++) {
                jsonObject = json.getJSONObject(i);
                ctrlmaleza.setId(jsonObject.getString("id"));
                ctrlmaleza.setTipo_componente(jsonObject.optString("tipo_componente"));
                ctrlmaleza.setCantidad(jsonObject.optString("cantidad"));
                ctrlmaleza.setVal_inversion(jsonObject.optString("val_inversion"));
                ctrlmaleza.setNotas(jsonObject.optString("notas"));
                ctrlmaleza.setCreated_at(jsonObject.optString("created_at"));

                String id = ctrlmaleza.getId().toString();
                int tipo_recur = Integer.parseInt(ctrlmaleza.getTipo_componente());
                tipo_recur = tipo_recur+1;

                switch (tipo_recur) {
                    case 1:  tipo = "Incepticida";
                        break;
                    case 2:  tipo = "Herbicidas";
                        break;
                    case 3:  tipo = "Pesticidas";
                        break;
                    case 4:  tipo = "Otros Abonos";
                        break;
                    case 5:  tipo = "Fertilizantes - Ureas";
                        break;
                    default: tipo = "Error!";
                        break;
                }

                String cantidad = ctrlmaleza.getCantidad().toString();
                String v_inversion = ctrlmaleza.getVal_inversion().toString();
                String fecha = ctrlmaleza.getCreated_at().toString();
                String notas = ctrlmaleza.getNotas().toString();

                lstRecurso.add(new Card_recursos(id,tipo,cantidad,v_inversion,notas,fecha));
            }

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id_recurs);
            RecyclerViewAdap_fer_maleza myAdapter = new RecyclerViewAdap_fer_maleza(this,this,lstRecurso);
            myrv.setLayoutManager(new GridLayoutManager(this,1));
            myrv.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void cargarService(){
        String url =getString(R.string.host)+"fertilizacioninsumos/obtener/id_cultivo/"+idCultivo;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


}
