package com.example.tolga.tolgaandroidodev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        TextView clickedTextView = (TextView) v;
        String text = clickedTextView.getText().toString();

        Intent intent = new Intent(MainActivity.this, BilgiGoster.class);
        intent.putExtra("veri", text);
        startActivity(intent);
    }
}