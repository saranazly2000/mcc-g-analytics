package com.example.assignment2mcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Model> productList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private myAdapter.RecyclerViewClickListener listener;
    private FirebaseAnalytics mFirebaseAnalytics;
    static Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        trackScreen("Food","FoodActivity");
        SimpleDateFormat formatter= new SimpleDateFormat("h:mm a");
        date = new Date(System.currentTimeMillis());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();

        db.collection("food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name =document.getData().get("name").toString();

                                Model product = new Model(name);
                             Log.e("sara",name);
                                productList.add(product);
                                adapter = new myAdapter(productList,listener);
                                recyclerView.setAdapter(adapter);
                                setOnClickListener();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                    
                });
    }

    private void setOnClickListener(){
        listener = new myAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                if(productList.get(position).getName().equals("pizza")){
                    selectContent("4",productList.get(position).getName(),"text view");
                    saveTime(date,"Food");
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",productList.get(position).getName());
                    startActivity(intent);
                }
                if(productList.get(position).getName().equals("Grilled chicken")){
                    selectContent("5",productList.get(position).getName(),"text view");
                    saveTime(date,"Food");
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",productList.get(position).getName());
                    startActivity(intent);
                }
                if(productList.get(position).getName().equals("Shrimp")){
                    selectContent("6",productList.get(position).getName(),"text view");
                    saveTime(date,"Food");
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",productList.get(position).getName());
                    startActivity(intent);
                }
                if(productList.get(position).getName().equals("Shawarma")){
                    selectContent("7",productList.get(position).getName(),"text view");
                    saveTime(date,"Food");
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",productList.get(position).getName());
                    startActivity(intent);
                }
                if(productList.get(position).getName().equals("Macaroni")){
                    selectContent("8",productList.get(position).getName(),"text view");
                    saveTime(date,"Food");
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",productList.get(position).getName());
                    startActivity(intent);
                }

            }
        };
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