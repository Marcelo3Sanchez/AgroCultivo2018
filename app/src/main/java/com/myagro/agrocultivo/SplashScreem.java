package com.myagro.agrocultivo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

public class SplashScreem extends Activity {

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screem);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


               sesion();
            }
        },2000);
    }

    private void sesion(){

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String id = preferences.getString("id",null);
        String user = preferences.getString("user",null);
        String pass = preferences.getString("pass",null);

        if(id != null && user != null && pass !=null){
            Intent home = new Intent(SplashScreem.this, Home.class);
            finish();
            startActivity(home);
        }else{ Intent login = new Intent(SplashScreem.this, Login.class);
            finish();
            startActivity(login);}
    }




}
