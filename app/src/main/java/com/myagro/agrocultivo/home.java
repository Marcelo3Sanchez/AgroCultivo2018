package com.myagro.agrocultivo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.myagro.agrocultivo.RecyclreMisCultivos.MisCultivosGrid;

public class home extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    Intent home = new Intent(home.this, MisCultivosGrid.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_notifications:

                    SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id",null);
                    editor.putString("user",null);
                    editor.putString("pass",null);
                    editor.putString("idCultivo", null);
                    editor.commit();
                    finish();
                    Intent home2 = new Intent(getApplicationContext(), Login.class);
                    startActivity(home2);

                    return true;
            }
            return false;
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
