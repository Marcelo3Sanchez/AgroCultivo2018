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
import com.myagro.agrocultivo.Entidades.Pro_ctrl_almacenamiento;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add.Add_almacenamiento;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Card_recursos;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.RecyclerView.RecyclerViewAdap_almacen;
import com.myagro.agrocultivo.R;
import com.myagro.agrocultivo.RecyclreMisCultivos.MisCultivosGrid;
import com.myagro.agrocultivo.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Grid_Almacenamiento extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    List<Card_recursos> lstRecurso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView txt_n;
    String idCultivo, tipo;

    @Override
    public void onBackPressed() {
      super.onBackPressed();
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
                    Intent add = new Intent(Grid_Almacenamiento.this, Add_almacenamiento.class);
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
        Pro_ctrl_almacenamiento ctrlventa = new Pro_ctrl_almacenamiento();
        JSONArray json = response.optJSONArray("almac");
        JSONObject jsonObject = null;
        lstRecurso = new ArrayList<>();

        try {

            for (int i = 0; i <json.length() ; i++) {
                jsonObject = json.getJSONObject(i);
                ctrlventa.setId(jsonObject.getString("id"));
                ctrlventa.setTipo_recurso(jsonObject.optString("tipo_recurso"));
                ctrlventa.setCantidad(jsonObject.optString("cantidad"));
                ctrlventa.setVal_inversion(jsonObject.optString("val_inversion"));
                ctrlventa.setNotas(jsonObject.optString("notas"));
                ctrlventa.setCreated_at(jsonObject.optString("created_at"));

                String id = ctrlventa.getId().toString();
                int tipo_recur = Integer.parseInt(ctrlventa.getTipo_recurso());
                tipo_recur = tipo_recur+1;

                switch (tipo_recur) {
                    case 1:  tipo = "Almacenamiento";
                        break;

                    default: tipo = "Error!";
                        break;
                }

                String cantidad = ctrlventa.getCantidad().toString();
                String v_inversion = ctrlventa.getVal_inversion().toString();
                String fecha = ctrlventa.getCreated_at().toString();
                String notas = ctrlventa.getNotas().toString();

                lstRecurso.add(new Card_recursos(id,tipo,cantidad,v_inversion,notas,fecha));
            }

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id_recurs);
            RecyclerViewAdap_almacen myAdapter = new RecyclerViewAdap_almacen(this,this, lstRecurso);
            myrv.setLayoutManager(new GridLayoutManager(this,1));
            myrv.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void cargarService(){
        String url =getString(R.string.host)+"almacenamiento.php?id="+idCultivo;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


}
