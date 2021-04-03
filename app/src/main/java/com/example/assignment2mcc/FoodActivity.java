package com.example.assignment2mcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Model> foodList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private myAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodList = new ArrayList<>();

        db.collection("food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name =document.getData().get("name").toString();

                                Model food = new Model(name);
                             Log.e("sara",name);
                                foodList.add(food);
                                adapter = new myAdapter(foodList,listener);
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
                if(foodList.get(position).getName().equals("pizza")){
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",foodList.get(position).getName());
                    startActivity(intent);
                }
                if(foodList.get(position).getName().equals("Grilled chicken")){
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",foodList.get(position).getName());
                    startActivity(intent);
                }
                if(foodList.get(position).getName().equals("Shrimp")){
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",foodList.get(position).getName());
                    startActivity(intent);
                }
                if(foodList.get(position).getName().equals("Shawarma")){
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",foodList.get(position).getName());
                    startActivity(intent);
                }
                if(foodList.get(position).getName().equals("Macaroni")){
                    Intent  intent = new Intent(getApplicationContext(),FoodDetails.class);
                    intent.putExtra("foodName",foodList.get(position).getName());
                    startActivity(intent);
                }

            }
        };
    }




}