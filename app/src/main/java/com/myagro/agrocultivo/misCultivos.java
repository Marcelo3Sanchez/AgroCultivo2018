package com.myagro.agrocultivo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Entidades.Mis_cultivos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class misCultivos extends AppCompatActivity  implements Response.Listener<JSONObject>, Response.ErrorListener{


    TextView txt1, txt2, txt3, txt4;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button button;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cultivos);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        button = (Button)findViewById(R.id.button);
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        txt4 = (TextView)findViewById(R.id.txt4);

        request= Volley.newRequestQueue(getBaseContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarService();
            }

        }); ////////////////


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(),"No se pudo Consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"Mensaje"+response, Toast.LENGTH_SHORT).show();

        Mis_cultivos misUsuarios = new Mis_cultivos();
        JSONArray json = response.optJSONArray("cultivos");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            misUsuarios.setId(jsonObject.getString("id"));
            misUsuarios.setName_cult(jsonObject.optString("name_cult"));
            misUsuarios.setTipo_arroz(jsonObject.optString("tipo_arroz"));
            misUsuarios.setTipo_siembra(jsonObject.optString("tipo_siembra"));
            misUsuarios.setCreated_at(jsonObject.optString("created_at"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        txt1.setText(misUsuarios.getId());
        txt2.setText(misUsuarios.getName_cult());
        txt3.setText(misUsuarios.getTipo_arroz());
        txt4.setText(misUsuarios.getCreated_at());

    }


    private void cargarService(){
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String iduser = preferences.getString("id",null);
        String url =getString(R.string.host)+"MisCultivos.php?id="+iduser;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
}
