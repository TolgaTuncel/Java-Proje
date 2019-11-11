package com.example.tolga.tolgaandroidodev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class resimgoster extends AppCompatActivity {
    private final static String DATA_URL="https://api.myjson.com/bins/zkg56";
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resim_goster);


        final globalveri globalVariable = (globalveri) getApplicationContext();
        final int araba = globalVariable.getArabaposition();

        final globalveri globalVariableMarka = (globalveri) getApplicationContext();
        final int marka = globalVariableMarka.getmarkaposition();

        image = findViewById(R.id.resim);


        StringRequest stringRequest=new StringRequest(Request.Method.GET, DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("arabalar");
                            JSONObject json_data = jsonArray.getJSONObject(marka);
                            JSONArray json_resim = json_data.getJSONArray("resim");
                            String sadii = json_resim.getString(araba);

                            LoadImageFromUrl(sadii);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    private void LoadImageFromUrl(String sadii) {
                        Picasso.with(resimgoster.this).load(sadii).placeholder(R.mipmap.ic_launcher).
                                error(R.mipmap.ic_launcher).into(image, new com.squareup.picasso.Callback(){
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(), "Ürün Resmi", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onError() {
                                Toast.makeText(getApplicationContext(), "Resim Yok", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(resimgoster.this, BilgiGoster.class);
                                startActivity(intent);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.getMessage());
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

//        if(getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

    }
    }
