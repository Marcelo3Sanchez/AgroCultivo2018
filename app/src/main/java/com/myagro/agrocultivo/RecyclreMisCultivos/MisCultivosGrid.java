package com.myagro.agrocultivo.RecyclreMisCultivos;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Entidades.Mis_cultivos;
import com.myagro.agrocultivo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MisCultivosGrid extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    List<CardCultivos> lstCultivos;

    int dra = 0;
    int cont = 1;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(MisCultivosGrid.this, com.myagro.agrocultivo.home.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_cultivos);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
        Toast.makeText(getApplicationContext(),"Mis cultivos", Toast.LENGTH_SHORT).show();

        Mis_cultivos misUsuarios = new Mis_cultivos();
        JSONArray json = response.optJSONArray("cultivos");
        JSONObject jsonObject = null;
        lstCultivos = new ArrayList<>();

        try {

            for (int i = 0; i <json.length() ; i++) {
                jsonObject = json.getJSONObject(i);
                misUsuarios.setId(jsonObject.getString("id"));
                misUsuarios.setName_cult(jsonObject.optString("name_cult"));
                misUsuarios.setTipo_arroz(jsonObject.optString("tipo_arroz"));
                misUsuarios.setTipo_siembra(jsonObject.optString("tipo_siembra"));
                misUsuarios.setCreated_at(jsonObject.optString("created_at"));

                String id =  misUsuarios.getId().toString();
                String name_cultivo = misUsuarios.getName_cult().toString();
                String tipo_arroz = misUsuarios.getTipo_arroz().toString();
                String tipo_siembra = misUsuarios.getTipo_siembra().toString();
                String created = misUsuarios.getCreated_at().toString();

                switch (cont) {
                    case 1:  dra = R.drawable.c1; cont = cont+1;
                        break;
                    case 2:  dra = R.drawable.c2; cont = cont+1;
                        break;
                    case 3:  dra = R.drawable.c3; cont = cont+1;
                        break;
                    case 4:  dra = R.drawable.c4; cont = cont+1;
                        break;
                    case 5:  dra = R.drawable.c5; cont = cont+1;
                        break;
                    case 6:  dra = R.drawable.c6; cont = cont+1;
                        break;
                    case 7:  dra = R.drawable.c7; cont = cont+1;
                        break;
                    case 8:  dra = R.drawable.c8; cont = cont+1;
                        break;
                    case 9:  dra = R.drawable.c9; cont = cont+1;
                        break;
                    case 10: dra = R.drawable.c10; cont = cont+1;
                        break;
                    case 11: dra = R.drawable.c11; cont = cont+1;
                        break;
                    case 12: dra = R.drawable.c12; cont = cont+1;
                        break;
                    case 13:  dra = R.drawable.c13; cont = cont+1;
                        break;
                    case 14:  dra = R.drawable.c14; cont = cont+1;
                        break;
                    case 15:  dra = R.drawable.c15; cont = cont+1;
                        break;
                    case 16:  dra = R.drawable.c16; cont = cont+1;
                        break;
                    case 17:  dra = R.drawable.c17; cont = cont+1;
                        break;
                    case 18: dra = R.drawable.c18; cont = cont+1;
                        break;
                    case 19: dra = R.drawable.c19; cont = cont+1;
                        break;
                    case 20: dra = R.drawable.c20; cont = cont+1;
                        break;
                    default: dra = R.drawable.c1; cont = 2;
                        break;
                }

                lstCultivos.add(new CardCultivos(id,name_cultivo,tipo_arroz,tipo_siembra,created,dra));
            }

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerViewAdapter_cultivos myAdapter = new RecyclerViewAdapter_cultivos(this,lstCultivos);
            myrv.setLayoutManager(new GridLayoutManager(this,3));
            myrv.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void cargarService(){
//
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String iduser = preferences.getString("id",null);
        String url =getString(R.string.host)+"MisCultivos.php?id="+iduser;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

}
