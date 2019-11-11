package com.example.tolga.tolgaandroidodev;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BilgiGoster extends AppCompatActivity {

    ProgressDialog progressDialog;
    private final static String DATA_URL="https://api.myjson.com/bins/zkg56";
    ListView lvArabalar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilgi_goster);
        final Intent intent = getIntent();
        final String sMarka = intent.getStringExtra("veri");
        lvArabalar=(ListView) findViewById(R.id.lv_Arabalar);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, DATA_URL,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String[][] listData = new String[100][4];
                            progressDialog.dismiss();
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("arabalar");

                            int length=jsonArray.length();
                            int x = 0, y = 0;
                            for(int i=0;i<length;i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);
                                String sAdi=json_data.getString("adi");
                                if(!sAdi.equals(sMarka))
                                    continue;

                                final globalveri globalveri = (globalveri) getApplicationContext();
                                globalveri.setmarkaposition(i);

                                y = x;
                                String sModel=json_data.getString("model");
                                JSONArray jsonArray2 = new JSONArray(sModel);
                                for (int j = 0; j < jsonArray2.length(); j++) {
                                    listData[y][0] = sAdi;
                                    listData[y][1] = jsonArray2.getString(j);
                                    y++;
                                }
                                y = x;
                                String sYil=json_data.getString("yil");
                                JSONArray jsonArray3 = new JSONArray(sYil);
                                for (int j = 0; j < jsonArray3.length(); j++) {
                                    listData[y][2] = jsonArray3.getString(j);
                                    y++;
                                }
                                y = x;
                                String sFiyat=json_data.getString("fiyat");
                                JSONArray jsonArray4 = new JSONArray(sFiyat);
                                for (int j = 0; j < jsonArray4.length(); j++) {
                                    listData[y][3] = jsonArray4.getString(j);
                                    y++;
                                }
                                x=y;
                            }

                            String[] temp = new String[y];
                            for(int i=0;i<y;i++){
                                temp[i] = listData[i][0] + "\nModel:" + listData[i][1] + "\nYıl:" + listData[i][2] + "\nFiyat:" + listData[i][3];
                            }

                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_list,temp);
                            lvArabalar.setAdapter(adapter);
                            lvArabalar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final globalveri globalveri = (globalveri) getApplicationContext();
                                    globalveri.setArabaposition(position);
                                    Intent intent = new Intent(BilgiGoster.this,resimgoster.class);
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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