package com.example.assignment2mcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void food(View v){
        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        startActivity(intent);
    }
    public void clothes(View v){
        Intent intent = new Intent(MainActivity.this, ClothesActivity.class);
        startActivity(intent);
    }
    public void electronic(View v){
        Intent intent = new Intent(MainActivity.this, ElectronicActivity.class);
        startActivity(intent);
    }
}