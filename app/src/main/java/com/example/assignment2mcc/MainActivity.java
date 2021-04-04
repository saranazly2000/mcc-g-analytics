package com.example.assignment2mcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    static Date date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        trackScreen("categories","MainActivity");
        SimpleDateFormat formatter= new SimpleDateFormat("h:mm a");
         date = new Date(System.currentTimeMillis());
    }


    public void food(View v){
        saveTime(date,"Categories");
        selectContent("1","food","button");
        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        startActivity(intent);
    }
    public void clothes(View v){
        saveTime(date,"Categories");
        selectContent("2","clothes","button");
        Intent intent = new Intent(MainActivity.this, ClothesActivity.class);
        startActivity(intent);
    }
    public void electronic(View v){
        saveTime(date,"Categories");
        selectContent("3","electronic","button");
        Intent intent = new Intent(MainActivity.this, ElectronicActivity.class);
        startActivity(intent);
    }


    public void saveTime(Date date,String screenName){
        SimpleDateFormat formatter= new SimpleDateFormat("h:mm a");
        Date date1 = new Date(System.currentTimeMillis());
        int x = (int) ((int)date1.getTime()-date.getTime());
        date = new Date(System.currentTimeMillis());
        String date2 =(x / 3600000) + " hour/s " + (x % 3600000) / 60000 + " minutes "+x / 1000 % 60+" sec";
        Map<String, Object> time = new HashMap<>();
        time.put("Screen Name", screenName);
        time.put("Time", date2);
        db.collection("time")
                .add(time)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document");
                    }
                });
        Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();
    }

    void trackScreen(String screenName ,String screenClass){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    void selectContent(String id,String name,String contentType){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }


}