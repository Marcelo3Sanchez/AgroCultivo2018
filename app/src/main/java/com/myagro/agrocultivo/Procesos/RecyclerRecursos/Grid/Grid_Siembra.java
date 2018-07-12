package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid;

import android.content.Intent;
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
import com.myagro.agrocultivo.Entidades.Pro_recursos;
import com.myagro.agrocultivo.Procesos.CultProcesos;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add.Add_siembra;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Card_recursos;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.RecyclerView.RecyclerViewAdap_recursos;
import com.myagro.agrocultivo.R;
import com.myagro.agrocultivo.RecyclreMisCultivos.MisCultivosGrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Grid_Siembra extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    List<Card_recursos> lstRecurso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView txt_n;
    String idCultivo, tipo;

    @Override
    public void onBackPressed() {
        finish();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home_recurs:

                    Intent inicio = new Intent(Grid_Siembra.this, com.myagro.agrocultivo.home.class);
                    startActivity(inicio);

                    return true;
                case R.id.navigation_micultivo_recurs:

                    Intent cult = new Intent(Grid_Siembra.this, MisCultivosGrid.class);
                    startActivity(cult);

                    return true;
                case R.id.navigation_add_recurs:
                    Intent add = new Intent(Grid_Siembra.this, Add_siembra.class);
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
        txt_n.setText(nombre);

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

        Pro_recursos Prorecursos = new Pro_recursos();
        JSONArray json = response.optJSONArray("siembra");
        JSONObject jsonObject = null;
        lstRecurso = new ArrayList<>();

        try {
               for (int i = 0; i <json.length() ; i++) {
                jsonObject = json.getJSONObject(i);
                Prorecursos.setId(jsonObject.getString("id"));
                Prorecursos.setTipo_recurso(jsonObject.optString("tipo_recurso"));
                Prorecursos.setN_horas(jsonObject.optString("n_horas"));
                Prorecursos.setVal_inversion(jsonObject.optString("val_inversion"));
                Prorecursos.setNotas(jsonObject.optString("notas"));
                Prorecursos.setCreated_at(jsonObject.optString("created_at"));

                   String id = Prorecursos.getId().toString();
                   int tipo_recur = Integer.parseInt(Prorecursos.getTipo_recurso());
                   tipo_recur = tipo_recur+1;


                   switch (tipo_recur) {
                       case 1:  tipo = "Maquinaria";
                           break;
                       case 2:  tipo = "Mano de obra";
                           break;
                       case 3:  tipo = "Riego";
                           break;
                       case 4:  tipo = "Transporte";
                           break;
                       default: tipo = "Error!";
                           break;
                   }

                   String n_horas = Prorecursos.getN_horas().toString();
                   String v_inversion = Prorecursos.getVal_inversion().toString();
                   String fecha = Prorecursos.getCreated_at().toString();
                   String notas = Prorecursos.getNotas().toString();

                   lstRecurso.add(new Card_recursos(id,tipo,n_horas,v_inversion,notas,fecha));

               }

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id_recurs);
            RecyclerViewAdap_recursos myAdapter = new RecyclerViewAdap_recursos(this,this,lstRecurso);
            myrv.setLayoutManager(new GridLayoutManager(this,1));
            myrv.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void cargarService(){
        String url =getString(R.string.host)+"siembra.php?id="+idCultivo;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


}
